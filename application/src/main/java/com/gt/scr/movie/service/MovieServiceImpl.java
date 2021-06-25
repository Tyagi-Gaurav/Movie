package com.gt.scr.movie.service;

import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.ImmutableUser;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private UserService userService;

    @Override
    public void addMovieRating(UUID userId, Movie movie) {
        User userFromDatabase = userService.findUserBy(userId);
        HashMap<UUID, Movie> uuidUserMoviesHashMap =
                userFromDatabase.movies();

        boolean alreadyExists = uuidUserMoviesHashMap.values()
                .stream().anyMatch(tz ->
                        tz.name().equalsIgnoreCase(movie.name()) &&
                        tz.yearProduced() == movie.yearProduced());

        if (alreadyExists) {
            throw new DuplicateRecordException("Movie already exists");
        }

        uuidUserMoviesHashMap.put(movie.id(), movie);

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .movies(uuidUserMoviesHashMap).build());
    }

    @Override
    public Map<UUID, Movie> getMovieRating(UUID userId) {
        User userFromDatabase = userService.findUserBy(userId);
        return userFromDatabase.movies();
    }

    @Override
    public void deleteMovieRating(UUID userId, UUID movieId) {
        User userFromDatabase = userService.findUserBy(userId);

        HashMap<UUID, Movie> uuidUserMovieMap = userFromDatabase.movies();
        uuidUserMovieMap.remove(movieId);

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .movies(uuidUserMovieMap).build());
    }

    @Override
    public void updateMovieRating(UUID userId, Movie movie) {
        User userFromDatabase = userService.findUserBy(userId);

        HashMap<UUID, Movie> uuidUserMovieMap = userFromDatabase.movies();

        uuidUserMovieMap.computeIfPresent(movie.id(), (uuid, mv) -> ImmutableMovie.builder()
                .name(isNotBlank(movie.name()) ? movie.name() : mv.name())
                .rating(movie.rating() != null ? movie.rating() : mv.rating())
                .yearProduced(movie.yearProduced() != 0 ? movie.yearProduced() : mv.yearProduced())
                .id(movie.id())
                .build());

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .movies(uuidUserMovieMap).build());
    }
}
