package com.gt.scr.imdb.ext.principals;

import com.gt.scr.imdb.common.DataReader;
import com.gt.scr.imdb.ext.principals.domain.Principal;
import reactor.core.publisher.Mono;

public class PrincipalsClient implements PrincipalsReader {

    private final DataReader dataReader;

    public PrincipalsClient(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    @Override
    public Mono<Principal> getTitleById(String titleId) {
        return dataReader.fetchRowUsingIndexKey(titleId)
                .map(PrincipalRow::from)
                .map(Principal.SubPrincipals::from)
                .collectList()
                .map(list -> new Principal(titleId, list));
    }
}
