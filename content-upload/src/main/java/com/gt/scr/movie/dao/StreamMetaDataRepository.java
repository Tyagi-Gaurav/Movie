package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import reactor.core.publisher.Mono;

public interface StreamMetaDataRepository {
    Mono<Void> store(MovieStreamMetaData movieStreamMetaData);
}
