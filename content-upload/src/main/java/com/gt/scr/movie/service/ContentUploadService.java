package com.gt.scr.movie.service;

import com.gt.scr.movie.service.domain.MovieStream;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import reactor.core.publisher.Mono;

public interface ContentUploadService {
    Mono<MovieStreamMetaData> saveStream(MovieStream movieStream);
}
