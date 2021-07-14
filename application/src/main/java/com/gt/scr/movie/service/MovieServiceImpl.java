package com.gt.scr.movie.service;

import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.ImmutableUser;
import com.gt.scr.movie.service.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private UserService userService;

    @Override
    public void addMovie(UUID userId, Movie movie) {
        var userFromDatabase = userService.findUserBy(userId);
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
    public Map<UUID, Movie> getMovie(UUID userId) {
        var userFromDatabase = userService.findUserBy(userId);
        return userFromDatabase.movies();
    }

    @Override
    public void deleteMovie(UUID userId, UUID movieId) {
        var userFromDatabase = userService.findUserBy(userId);

        HashMap<UUID, Movie> uuidUserMovieMap = userFromDatabase.movies();
        uuidUserMovieMap.remove(movieId);

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .movies(uuidUserMovieMap).build());
    }

    @Override
    public void updateMovie(UUID userId, Movie movie) {
        var userFromDatabase = userService.findUserBy(userId);

        HashMap<UUID, Movie> uuidUserMovieMap = userFromDatabase.movies();

        uuidUserMovieMap.computeIfPresent(movie.id(), (uuid, mv) -> ImmutableMovie.builder()
                .name(isNotBlank(movie.name()) ? movie.name() : mv.name())
                .rating(!BigDecimal.ZERO.equals(movie.rating()) ? movie.rating() : mv.rating())
                .yearProduced(movie.yearProduced() != 0 ? movie.yearProduced() : mv.yearProduced())
                .id(movie.id())
                .movieVideo(movie.movieVideo().isPresent() ? movie.movieVideo() : mv.movieVideo())
                .build());

        userService.update(ImmutableUser.builder().from(userFromDatabase)
                .movies(uuidUserMovieMap).build());
    }
}
