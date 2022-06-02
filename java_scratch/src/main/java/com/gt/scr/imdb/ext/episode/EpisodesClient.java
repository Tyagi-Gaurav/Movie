package com.gt.scr.imdb.ext.episode;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.ext.akas.AkaRow;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public class EpisodesClient implements EpisodeReader {

    private final DataReader dataReader;

    public EpisodesClient(DataReader dataReader) {
        this.dataReader = dataReader;
        final long startTime = System.currentTimeMillis();
        System.out.println("Started loading Crew data");
        final long endTime = System.currentTimeMillis();
        System.out.println("Finished loading Crew data in " + (endTime - startTime) / 1000 + " seconds");
    }

    @Override
    public Mono<Aka> getTitleById(String titleId) {
        return dataReader.fetchRowUsingIndexKey(titleId)
                .map(AkaRow::from)
                .map(Aka.SubAkas::from)
                .collectList()
                .map(list -> new Aka(titleId, list));
    }

    @Override
    public long getTotalNumberOfTitles() {
        return 0L;
    }
}
