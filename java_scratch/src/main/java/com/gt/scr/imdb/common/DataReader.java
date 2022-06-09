package com.gt.scr.imdb.common;

import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.fail;

public abstract class DataReader {
    protected int totalBlockCount;

    public DataReader(int totalBlockCount) {
        this.totalBlockCount = totalBlockCount;
    }

    protected void preProcessedRow(byte[] rowAsBytes, String indexKey, int indexFileOffset) {

    }

    protected void postProcessedRow(byte[] rowAsBytes, String indexKey, int indexFileOffset) {

    }

    public abstract Flux<String> fetchRowUsingIndexKey(String key);

    protected void doIt(File originalFile,
                        boolean blockCountProvided,
                        Consumer<Map.Entry<String, List<Integer>>> indexWriter) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(originalFile, "r");
            //Defines the size of block to be used for reading at once from file.
            final int BLOCK_SIZE = 4 * 1024 * 1024;

            /*
                Position is used to keep track of the field within the row which will be picked up
                while the row is being scanned. The field becomes the index into the map used for
                grouping. At present, it always assumes the first column to be that index.
             */
            AtomicInteger position = new AtomicInteger();
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BLOCK_SIZE);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final BoundedPriorityMap<String, List<Integer>> objects = new BoundedPriorityMap<>(20);
            /*
                When totalBlockCount is 0, then read full content of all files. Use totalBlockCount = 1 to read only BLOCK_SIZE
                worth of data and then quit.
             */
            int tempBlockCount = 0;
            int currentFp = 0;
            while ((!blockCountProvided || tempBlockCount < totalBlockCount) && channel.read(byteBuffer) > 0) {
                int i = 0;
                while (i < byteBuffer.limit()) {
                    int end = getRow(i, byteBuffer, byteArrayOutputStream, position);

                    /*
                        Once the row is retrieved, here we extract the first column using the position and then
                        update the map.
                     */
                    if (end + 1 < byteBuffer.limit() && byteBuffer.get(end) == 10) {
                        final byte[] rowAsBytes = byteArrayOutputStream.toByteArray();
                        final String indexKey = new String(rowAsBytes, 0, position.get(), StandardCharsets.UTF_8);

                        boolean elementEvicted;

                        preProcessedRow(rowAsBytes, indexKey, i+currentFp);
                        if (objects.containsKey(indexKey)) {
                            final List<Integer> offsets = objects.get(indexKey);
                            elementEvicted = objects.putWithEviction(indexKey, Stream.concat(offsets.stream(), Stream.of(i+currentFp)).toList());
                        } else {
                            elementEvicted = objects.putWithEviction(indexKey, List.of(i+currentFp));
                        }

                        if (elementEvicted) {
                            final Map.Entry<String, List<Integer>> lastEvictedElement = objects.getLastEvictedElement();
                            if (lastEvictedElement.getValue() != null && !lastEvictedElement.getValue().isEmpty()) {
                                indexWriter.accept(lastEvictedElement);
                            } else {
                                fail("This condition should not occur. Last evicted element is null");
                            }
                        }

                        postProcessedRow(rowAsBytes, indexKey, i+currentFp);

                        /*
                        Only reset byte array stream when a full row has been loaded. It is used to keep hold of partial
                        rows read during block reads.
                         */
                        byteArrayOutputStream.reset();
                    } else {
                        break;
                    }
                    i = end + 1;
                }
                //Clear buffer for next round of reading the block.
                currentFp += byteBuffer.limit();
                byteBuffer.clear();
                ++tempBlockCount;
            }

            byteArrayOutputStream.close();
            channel.close();
            objects.entrySet().forEach(indexWriter);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private int getRow(int start, ByteBuffer bytes,
                       ByteArrayOutputStream byteArrayOutputStream,
                       AtomicInteger position) {
        int i = start;
        boolean found = false;
        int oldSize = byteArrayOutputStream.size();
        if (oldSize > 0) {
            //In this case, it means that there is some residual bytes from previous iteration.
            //go through the stream and find if
            final byte[] previousArray = byteArrayOutputStream.toByteArray();
            for (int j = 0; j < previousArray.length; j++) {
                if (previousArray[j] == 9) {
                    found = true;
                    position.set(j);
                    break;
                }
            }
        }

        while (i < bytes.limit() && bytes.get(i) != 10) { //10 is carriage return '/r'
            byteArrayOutputStream.write(bytes.get(i));
            if (!found && bytes.get(i) == 9) { //9 is tab character
                position.set(oldSize + i - start);
                found = true;
            }
            ++i;
        }

        return i;
    }
}
