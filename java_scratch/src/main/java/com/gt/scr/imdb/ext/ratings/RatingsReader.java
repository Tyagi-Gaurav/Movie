package com.gt.scr.imdb.ext.ratings;

import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public interface RatingsReader {
    Mono<Aka> getTitleById(String titleId);

    long getTotalNumberOfTitles();
}
