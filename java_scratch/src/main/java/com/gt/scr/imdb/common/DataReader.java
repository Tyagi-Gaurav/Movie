package com.gt.scr.imdb.common;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

public abstract class DataReader {
    public abstract void load(Map<String, List<Integer>> keyMap);

    public abstract Flux<String> readRowsAtLocation(List<Integer> integers);
}
