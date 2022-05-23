package com.gt.scr.imdb.service;

import com.gt.scr.imdb.domain.ContentData;
import reactor.core.publisher.Mono;

public interface MovieService {
    Mono<ContentData> getMovieBy(String contentId);

    long getNumberOfTitles();
}
