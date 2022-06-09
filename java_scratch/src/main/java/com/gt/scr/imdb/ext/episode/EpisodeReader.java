package com.gt.scr.imdb.ext.episode;

import com.gt.scr.imdb.ext.akas.domain.Aka;
import reactor.core.publisher.Mono;

public interface EpisodeReader {
    Mono<Aka> getTitleById(String titleId);

    long getTotalNumberOfTitles();
}
