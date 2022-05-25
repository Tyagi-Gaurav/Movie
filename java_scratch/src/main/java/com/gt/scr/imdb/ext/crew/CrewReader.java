package com.gt.scr.imdb.ext.crew;

import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public interface CrewReader {
    Mono<Aka> getTitleById(String titleId);

    long getTotalNumberOfTitles();
}
