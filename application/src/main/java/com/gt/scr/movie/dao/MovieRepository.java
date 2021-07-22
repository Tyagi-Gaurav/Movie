package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.Movie;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository {
    Optional<Movie> findMovieBy(UUID id);

    Optional<Movie> findMovieBy(UUID userId, String name);

    List<Movie> getAllMoviesForUser(UUID id);

    void delete(UUID movieId);

    void update(Movie updatedMovie);

    void create(UUID userId, Movie movie);
}
