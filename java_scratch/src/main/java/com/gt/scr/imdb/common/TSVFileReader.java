package com.gt.scr.imdb.common;

import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TSVFileReader extends DataReader {
    private final RandomAccessFile randomAccessFile;
    private final int totalBlockCount;
    private final boolean blockCountProvided;

    public TSVFileReader(String fileName, int blockCount) {
        totalBlockCount = blockCount;
        blockCountProvided = totalBlockCount > 0;
        InputStream resourceAsStream = TSVFileReader.class.getResourceAsStream(fileName);
        final URL resource = TSVFileReader.class.getResource(fileName);
        try {
            final File file = new File(resource.toURI().getPath());
            //Create random access file so it can be used to retrieve rows based on seek and read.
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        assertThat(resourceAsStream).describedAs("Could not find file " + fileName).isNotNull();
    }

    @Override
    public void load(Map<String, List<Integer>> keyMap) {
        try {
            //Defines the size of block to be used for reading at once from file.
            final int BLOCK_SIZE = 16 * 1024 * 1024;

            /*
                Position is used to keep track of the field within the row which will be picked up
                while the row is being scanned. The field becomes the index into the map used for
                grouping. At present, it always assumes the first column to be that index.
             */
            final AtomicInteger position = new AtomicInteger();
            final FileChannel channel = randomAccessFile.getChannel();
            final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BLOCK_SIZE);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            /*
                When blockCount is 0, then read full content of all files. Use blockCount = 1 to read only BLOCK_SIZE
                worth of data and then quit.
             */
            int blockCount = 0;
            while ((!blockCountProvided || blockCount < totalBlockCount) && channel.read(byteBuffer) > 0) {
                int i = 0;
                while (i < byteBuffer.limit()) {
                    int end = getRow(i, byteBuffer, byteArrayOutputStream, position);

                    /*
                        Once the row is retrieved, here we extract the first column using the position and then
                        update the map.
                     */
                    if (end + 1 < byteBuffer.limit() && byteBuffer.get(end) == 10) {
                        final byte[] rowAsBytes = byteArrayOutputStream.toByteArray();
                        final String title = new String(rowAsBytes, 0, position.get(), StandardCharsets.UTF_8);
                        int finalI = i;
                        keyMap.compute(title, (key1, oldValue) -> {
                            if (oldValue == null) {
                                final List<Integer> objects1 = new ArrayList<>(5);
                                objects1.add(finalI);
                                return objects1;
                            } else {
                                oldValue.add(finalI);
                                return oldValue;
                            }
                        });
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
                byteBuffer.clear();
                ++blockCount;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Flux<String> readRowsAtLocation(List<Integer> rows) {
        return Flux.fromIterable(rows.stream()
                .map(rowPos -> {
                    try {
                        randomAccessFile.seek(rowPos);
                        String line = randomAccessFile.readLine();
                        return new String(line.getBytes("ISO-8859-1"), "UTF-8");
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                })
                .collect(Collectors.toList()));
    }

    private int getRow(int start, ByteBuffer bytes,
                       ByteArrayOutputStream byteArrayOutputStream,
                       AtomicInteger position) {
        int i = start;
        boolean found = false;
        while (i < bytes.limit() && bytes.get(i) != 10) { //10 is carriage return '/r'
            byteArrayOutputStream.write(bytes.get(i));
            if (!found && bytes.get(i) == 9) { //9 is tab character
                position.set(i - start);
                found = true;
            }
            ++i;
        }

        return i;
    }
}
