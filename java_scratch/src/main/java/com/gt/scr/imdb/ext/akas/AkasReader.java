package com.gt.scr.imdb.ext.akas;

import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public interface AkasReader {
    Mono<Aka> getTitleById(String titleId);
}
