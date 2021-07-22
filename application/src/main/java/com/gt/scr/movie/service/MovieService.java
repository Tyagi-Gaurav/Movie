package com.gt.scr.movie.service;

import com.gt.scr.movie.service.domain.Movie;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

public interface MovieService {
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    void addMovie(UUID userId, Movie movie);

    List<Movie> getMoviesFor(UUID userId);

    void updateMovie(Movie movie);

    void deleteMovie(UUID id);
}

