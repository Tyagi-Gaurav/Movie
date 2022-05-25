package com.gt.scr.imdb.ext.people;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.ext.akas.AkaRow;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeoplesClient implements PeoplesReader {

    private final Map<String, List<Integer>> keyMap;
    private final DataReader dataReader;

    public PeoplesClient(DataReader dataReader) {
        this.dataReader = dataReader;
        keyMap = new HashMap<>();
        final long startTime = System.currentTimeMillis();
        System.out.println("Started loading Peoples data");
        dataReader.load(keyMap);
        final long endTime = System.currentTimeMillis();
        System.out.println("Finished loading Peoples data in " + (endTime - startTime) / 1000 + " seconds");
    }

    @Override
    public Mono<Aka> getTitleById(String titleId) {
        return dataReader.readRowsAtLocation(keyMap.get(titleId))
                .map(AkaRow::from)
                .map(Aka.SubAkas::from)
                .collectList()
                .map(list -> new Aka(titleId, list));
    }

    @Override
    public long getTotalNumberOfTitles() {
        return keyMap.size();
    }
}
