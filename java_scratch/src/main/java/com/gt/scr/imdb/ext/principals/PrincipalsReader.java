package com.gt.scr.imdb.ext.principals;

import com.gt.scr.imdb.ext.principals.domain.Principal;
import reactor.core.publisher.Mono;

public interface PrincipalsReader {
    Mono<Principal> getTitleById(String titleId);
}
