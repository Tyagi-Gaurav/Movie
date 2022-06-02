package com.gt.scr.imdb.ext.people;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.ext.akas.AkaRow;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public class PeoplesClient implements PeoplesReader {

    private final DataReader dataReader;

    public PeoplesClient(DataReader dataReader) {
        this.dataReader = dataReader;
        final long startTime = System.currentTimeMillis();
        System.out.println("Started loading Peoples data");
        final long endTime = System.currentTimeMillis();
        System.out.println("Finished loading Peoples data in " + (endTime - startTime) / 1000 + " seconds");
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
