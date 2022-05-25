package com.gt.scr.imdb.common;

import reactor.core.publisher.Mono;

import java.io.RandomAccessFile;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface DataReader {
    Supplier<Stream<String>> readAll();

    Mono<String> readNext();

    long currentPos();

    long size();

    RandomAccessFile getFileHandle();
}
