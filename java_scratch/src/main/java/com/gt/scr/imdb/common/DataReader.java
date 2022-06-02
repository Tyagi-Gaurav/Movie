package com.gt.scr.imdb.common;

import reactor.core.publisher.Flux;

public abstract class DataReader {
    public abstract Flux<String> fetchRowUsingIndexKey(String key);
}
