package com.gt.scr.imdb.ext.akas;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public class AkasClient implements AkasReader {

    private final DataReader dataReader;

    public AkasClient(DataReader dataReader) {
        this.dataReader = dataReader;
        final long startTime = System.currentTimeMillis();
        System.out.println("Started loading Akas data");
        final long endTime = System.currentTimeMillis();
        System.out.println("Finished loading Akas data in " + (endTime - startTime) / 1000 + " seconds");
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
        return 0;
    }
}
