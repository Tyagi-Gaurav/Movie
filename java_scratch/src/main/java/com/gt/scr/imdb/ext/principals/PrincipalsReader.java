package com.gt.scr.imdb.ext.principals;

import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public interface PrincipalsReader {
    Mono<Aka> getTitleById(String titleId);
}
