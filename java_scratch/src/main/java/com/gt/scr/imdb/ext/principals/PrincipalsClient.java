package com.gt.scr.imdb.ext.principals;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.ext.akas.AkaRow;
import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public class PrincipalsClient implements PrincipalsReader {

    private final DataReader dataReader;

    public PrincipalsClient(DataReader dataReader) {
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
