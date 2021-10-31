package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MovieRepository {
    Mono<Movie> findMovieBy(UUID id);

    Mono<Movie> findMovieBy(UUID userId, String name);

    Flux<Movie> getAllMoviesForUser(UUID id);

    Mono<Void> delete(UUID movieId);

    Mono<Void> update(Movie updatedMovie);

    Mono<Void> create(UUID userId, Movie movie);
}
