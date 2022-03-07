package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.MovieStream;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import reactor.core.publisher.Mono;

public interface ContentStore {
    Mono<MovieStreamMetaData> store(MovieStream movieStream);
}
