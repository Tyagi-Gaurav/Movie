package com.gt.scr.movie.service;

import com.gt.scr.movie.service.domain.Movie;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;
import java.util.UUID;

public interface MovieService {
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    void addMovie(UUID userId, Movie movie);

    Map<UUID, Movie> getMovie(UUID userId);

    void deleteMovie(UUID userId, UUID movieId);

    void updateMovie(UUID userId, Movie movie);
}

