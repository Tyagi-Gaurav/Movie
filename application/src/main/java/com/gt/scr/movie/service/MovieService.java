package com.gt.scr.movie.service;

import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.UserTimezone;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;
import java.util.UUID;

public interface MovieService {
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    void addMovieRating(UUID userId, Movie movie);

    Map<UUID, UserTimezone> getMovieRating(UUID userId);

    void deleteMovieRating(UUID userId, UUID timezoneId);

    void updateMovieRating(UUID userId, UserTimezone timezone);
}

