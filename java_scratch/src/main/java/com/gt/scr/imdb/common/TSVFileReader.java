package com.gt.scr.imdb.common;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TSVFileReader implements DataReader {
    private final BufferedReader bufferedReader;
    private final RandomAccessFile randomAccessFile;

    public TSVFileReader(String fileName) {
        InputStream resourceAsStream = TSVFileReader.class.getResourceAsStream(fileName);
        final URL resource = TSVFileReader.class.getResource(fileName);
        try {
            final File file = new File(resource.toURI().getPath());
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        assertThat(resourceAsStream).describedAs("Could not find file " + fileName).isNotNull();
        bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
    }

    @Override
    public Supplier<Stream<String>> readAll() {
        return () -> {
            try {
                bufferedReader.readLine(); //Read header and skip it.
                return bufferedReader.lines();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        };
    }

    @Override
    public Mono<String> readNext() {
        return null;
    }

    @Override
    public long currentPos() {
        return 0L;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public RandomAccessFile getFileHandle() {
        return randomAccessFile;
    }
}
