package com.gt.scr.imdb.common;

import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DummyTSVFileReader extends DataReader {
    private static final String INDEX_KEY_SPLITTER = "\t";
    private static final String INDEX_LINE_SPLITTER = "\n";
    private final Set<String> interestKeys;
    private final RandomAccessFile randomAccessFile;
    private final RandomAccessFile randomAccessFile2;
    private RandomAccessFile indexFile;
    private RandomAccessFile indexFile2;
    private final Map<String, Long> keyMap;

    public DummyTSVFileReader(String fileName, int totalBlockCount,
                              Set<String> interestKeys) {
        super(totalBlockCount);
        this.interestKeys = interestKeys;
        boolean blockCountProvided = totalBlockCount > 0;
        keyMap = new HashMap<>();
        InputStream resourceAsStream = DummyTSVFileReader.class.getResourceAsStream(fileName);
        final URL resource = DummyTSVFileReader.class.getResource(fileName);
        File originalFile;
        File indexFileObject;
        try {
            indexFileObject = File.createTempFile(fileName, ".index");
            indexFile = new RandomAccessFile(indexFileObject, "rw");
            indexFile2 = new RandomAccessFile(indexFileObject, "rw");
            originalFile = new File(resource.toURI().getPath());
            System.out.println("Started index creation at path: " + indexFileObject.getAbsolutePath());
            //Create random access file so it can be used to retrieve rows based on seek and read.
        } catch (URISyntaxException | IOException e) {
            throw new IllegalStateException(e);
        }
        assertThat(resourceAsStream).describedAs("Could not find file " + fileName).isNotNull();

        final long startTime = System.currentTimeMillis();
        try {
            //Defines the size of block to be used for reading at once from file.
            randomAccessFile2 = new RandomAccessFile(originalFile, "rw");
            doIt(originalFile, blockCountProvided, this::writeToIndexFile);
            indexFile = new RandomAccessFile(indexFileObject, "rw"); //Repoen file
            randomAccessFile = new RandomAccessFile(originalFile, "rw");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        final long endTime = System.currentTimeMillis();
        System.out.println("Finished creating index in " + (endTime - startTime) / 1000 + " seconds");
    }

    private void writeToIndexFile(Map.Entry<String, List<Integer>> entry) {
        try {
            if (interestKeys.contains(entry.getKey())) {
                String s = entry.getKey() + INDEX_KEY_SPLITTER + entry.getValue().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(",")) + INDEX_LINE_SPLITTER;

                keyMap.put(entry.getKey(), indexFile.getFilePointer());
                indexFile.write(s.getBytes(StandardCharsets.UTF_8));
                fetchRowUsingIndexKey(entry.getKey()).subscribe();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Flux<String> fetchRowUsingIndexKey(String indexKey) {
        try {
            if (keyMap.containsKey(indexKey)) {
                indexFile2.seek(keyMap.get(indexKey));
                final String allFileLocations = indexFile2.readLine();
                final String[] split = allFileLocations.split(INDEX_KEY_SPLITTER);
                final String[] locations = split[1].split(",");

                return Flux.fromStream(Arrays.stream(locations)
                        .map(this::retrieveRowFromOriginalFile)
                        .peek(s -> System.out.println("Read: " + s + " for key: " + indexKey))
                        .filter(Objects::nonNull));
            } else {
                System.out.println("Key not found: " + indexKey);
            }
            return Flux.empty();
        } catch (IOException e) {
            return Flux.error(new IllegalStateException(e));
        }
    }

    private String retrieveRowFromOriginalFile(String location) {
        try {
            randomAccessFile2.seek(Long.parseLong(location));
            final String s = randomAccessFile2.readLine();
            return s;
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Set<String> keys = Set.of("tt0000003", "tt0000001", "nm0721526", "nm1770680", "nm1335271", "nm5442200");
        new DummyTSVFileReader("/name.basics.tsv", 0, keys);
    }
}
