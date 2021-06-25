package com.gt.scr.movie.util;

import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.ImmutableUser;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestBuilders {
    public static User aUser() {
        return ImmutableUser.builder()
                .id(UUID.randomUUID())
                .password(randomAlphabetic(10))
                .username(randomAlphabetic(10))
                .lastName(randomAlphabetic(10))
                .firstName(randomAlphabetic(10))
                .authorities(Collections.singleton(new SimpleGrantedAuthority("USER")))
                .build();
    }

    public static User aUserWithUserName(String userName) {
        return ImmutableUser.builder()
                .id(UUID.randomUUID())
                .password(randomAlphabetic(10))
                .username(userName)
                .lastName(randomAlphabetic(10))
                .firstName(randomAlphabetic(10))
                .authorities(Collections.singleton(new SimpleGrantedAuthority("USER")))
                .build();
    }

    public static User aUserWithMovies() {
        Movie movie = aMovie();
        HashMap<UUID, Movie> userMovieMap = new HashMap<>();
        userMovieMap.put(movie.id(), movie);

        return aUserWithMovies(userMovieMap);
    }

    public static Movie aMovie() {
        return ImmutableMovie.builder()
                .name(randomAlphabetic(10))
                .yearProduced(2010)
                .rating(BigDecimal.valueOf(10))
                .id(UUID.randomUUID())
                .build();
    }

    public static User aUserWithMovies(HashMap<UUID, Movie> movieMap) {
        return ImmutableUser.builder()
                .id(UUID.randomUUID())
                .password(randomAlphabetic(10))
                .username(randomAlphabetic(10))
                .lastName(randomAlphabetic(10))
                .firstName(randomAlphabetic(10))
                .authorities(Collections.singleton(new SimpleGrantedAuthority("USER")))
                .movies(movieMap)
                .build();
    }
}
