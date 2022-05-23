package com.gt.scr.imdb.common;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DataReader {
    Flux<String> readAll();

    Mono<String> readNext();

    long size();
}
