package com.gt.scr.movie.service;

import com.gt.scr.movie.service.domain.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MovieService {
    Mono<Void> addMovie(UUID ownerUserId, UUID originatorUserId, Movie movie);

    Flux<Movie> getMoviesFor(UUID userId);

    Mono<Void> updateMovie(Movie movie);

    Mono<Void> deleteMovie(UUID id);
}

