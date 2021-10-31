package com.gt.scr.movie.service;

import com.gt.scr.movie.service.domain.Movie;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MovieService {
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    Mono<Void> addMovie(UUID userId, Movie movie);

    Flux<Movie> getMoviesFor(UUID userId);

    Mono<Void> updateMovie(Movie movie);

    Mono<Void> deleteMovie(UUID id);
}

