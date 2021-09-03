package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository {
    Mono<Movie> findMovieBy(UUID id);

    Mono<Movie> findMovieBy(UUID userId, String name);

    Flux<Movie> getAllMoviesForUser(UUID id);

    void delete(UUID movieId);

    void update(Movie updatedMovie);

    void create(UUID userId, Movie movie);
}
