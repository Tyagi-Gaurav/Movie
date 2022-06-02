package com.gt.scr.imdb.common;

import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TSVFileReader extends DataReader {
    private RandomAccessFile randomAccessFile;
    private RandomAccessFile indexFile;
    private final Map<String, Long> keyMap;

    public TSVFileReader(String fileName, int totalBlockCount) {
        boolean blockCountProvided = totalBlockCount > 0;
        keyMap = new HashMap<>();
        InputStream resourceAsStream = TSVFileReader.class.getResourceAsStream(fileName);
        final URL resource = TSVFileReader.class.getResource(fileName);
        File originalFile;
        File indexFileObject;
        try {
            indexFileObject = File.createTempFile(fileName, ".index");
            indexFile = new RandomAccessFile(indexFileObject, "rw");
            originalFile = new File(resource.toURI().getPath());
            System.out.println("Index file path: " + indexFileObject.getAbsolutePath());
            //Create random access file so it can be used to retrieve rows based on seek and read.
            randomAccessFile = new RandomAccessFile(originalFile, "r");
        } catch (URISyntaxException | IOException e) {
            throw new IllegalStateException(e);
        }
        System.out.println(Runtime.getRuntime().freeMemory() / 1000);
        assertThat(resourceAsStream).describedAs("Could not find file " + fileName).isNotNull();

        try {
            //Defines the size of block to be used for reading at once from file.
            final int BLOCK_SIZE = 4 * 1024;

            /*
                Position is used to keep track of the field within the row which will be picked up
                while the row is being scanned. The field becomes the index into the map used for
                grouping. At present, it always assumes the first column to be that index.
             */
            AtomicInteger position = new AtomicInteger();
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BLOCK_SIZE);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Map<String, List<Long>> indexMap = new HashMap<>(512);
            final BoundedPriorityMap<String, List<Integer>> objects = new BoundedPriorityMap<>(20);
            /*
                When totalBlockCount is 0, then read full content of all files. Use totalBlockCount = 1 to read only BLOCK_SIZE
                worth of data and then quit.
             */
            int tempBlockCount = 0;
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
                        final String title = new String(rowAsBytes, 0, position.get(), StandardCharsets.UTF_8);

                        boolean elementEvicted;
                        if (objects.containsKey(title)) {
                            final List<Integer> offsets = objects.get(title);
                            elementEvicted = objects.putWithEviction(title, Stream.concat(offsets.stream(), Stream.of(i)).toList());
                        } else {
                            elementEvicted = objects.putWithEviction(title, List.of(i));
                        }

                        if (elementEvicted) {
                            final Map.Entry<String, List<Integer>> lastEvictedElement = objects.getLastEvictedElement();
                            writeToIndexFile(lastEvictedElement);
                        }

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
                ++tempBlockCount;
            }

            objects.entrySet().forEach(this::writeToIndexFile);
            indexFile = new RandomAccessFile(indexFileObject, "rw"); //Repoen file
            randomAccessFile = new RandomAccessFile(originalFile, "rw"); //Repoen file
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void writeToIndexFile(Map.Entry<String, List<Integer>> entry) {
        try {
            String s = entry.getKey() + "\t" + entry.getValue().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(",")) + "\n";

            keyMap.put(entry.getKey(), indexFile.getFilePointer());
            indexFile.write(s.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Flux<String> fetchRowUsingIndexKey(String titleId) {
        try {
            indexFile.seek(keyMap.get(titleId));
            final String allFileLocations = indexFile.readLine();
            final String[] split = allFileLocations.split("\t");
            final String[] locations = split[1].split(",");

            for (String location : locations) {
                randomAccessFile.seek(Long.parseLong(location));
                String line = randomAccessFile.readLine();
                System.out.println(new String(line.getBytes("ISO-8859-1"), "UTF-8"));
            }
            return Flux.empty();
        } catch (IOException e) {
            return Flux.error(new IllegalStateException(e));
        }
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

    public static record DataSet<E>(String indexKey, List<E> data) {
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof DataSet that) {
                return this.indexKey.equals(that.indexKey);
            }

            return false;
        }
    }
}
