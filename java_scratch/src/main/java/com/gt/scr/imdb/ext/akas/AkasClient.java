package com.gt.scr.imdb.ext.akas;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public class AkasClient implements AkasReader {

    private final DataReader dataReader;

    public AkasClient(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    @Override
    public Mono<Aka> getTitleById(String titleId) {
        return dataReader.fetchRowUsingIndexKey(titleId)
                .map(AkaRow::from)
                .map(Aka.SubAkas::from)
                .collectList()
                .map(list -> new Aka(titleId, list));
    }
}
