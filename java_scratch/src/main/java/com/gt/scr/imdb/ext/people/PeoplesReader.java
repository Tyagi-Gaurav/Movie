package com.gt.scr.imdb.ext.people;

import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public interface PeoplesReader {
    Mono<Aka> getTitleById(String titleId);

    long getTotalNumberOfTitles();
}
