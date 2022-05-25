package com.gt.scr.imdb.ext.episode;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.ext.akas.AkaRow;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpisodesClient implements EpisodeReader {

    private final Map<String, List<Integer>> keyMap;
    private final DataReader dataReader;

    public EpisodesClient(DataReader dataReader) {
        this.dataReader = dataReader;
        keyMap = new HashMap<>();
        final long startTime = System.currentTimeMillis();
        System.out.println("Started loading Crew data");
        dataReader.load(keyMap);
        final long endTime = System.currentTimeMillis();
        System.out.println("Finished loading Crew data in " + (endTime - startTime) / 1000 + " seconds");
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
