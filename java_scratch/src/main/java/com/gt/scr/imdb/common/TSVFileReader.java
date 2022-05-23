package com.gt.scr.imdb.common;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

import static org.assertj.core.api.Assertions.assertThat;

public class TSVFileReader implements DataReader {
    private final BufferedReader bufferedReader;
    private final Sinks.Many<String> lineSink;

    public TSVFileReader(String fileName) {
        InputStream resourceAsStream = TSVFileReader.class.getResourceAsStream(fileName);
        assertThat(resourceAsStream).describedAs("Could not find file " + fileName).isNotNull();
        bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        lineSink = Sinks.many().unicast().onBackpressureBuffer(new LinkedTransferQueue<>());
    }

    @Override
    public Flux<String> readAll() {
        Callable<Object> callable = Executors.callable(() -> {
            try {
                bufferedReader.readLine(); // Read header and skip it.
                String line;
                int lineCount = 1;
                while ((line = bufferedReader.readLine()) != null) {
                    lineSink.emitNext(line, Sinks.EmitFailureHandler.FAIL_FAST);
                    Thread.sleep(10);
                    ++lineCount;
                    if (lineCount % 1000 == 0) {
                        System.out.println("Emitted: " + lineCount);
                    }
                }
                System.out.println("Lines Emitted: " + lineCount);
            } catch (IOException | InterruptedException e) {
                System.err.println("Error occurred: " + e.getMessage());
                throw new IllegalStateException(e);
            }
        });

        Executors.newSingleThreadExecutor().submit(callable);
        return lineSink.asFlux();
    }

    @Override
    public Mono<String> readNext() {
        return null;
    }

    @Override
    public long size() {
        return bufferedReader.lines().count();
    }
}
