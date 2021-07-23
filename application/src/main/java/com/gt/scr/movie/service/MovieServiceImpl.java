package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.MovieRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.Movie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void addMovie(UUID userId, Movie movie) {
        Optional<Movie> movieExists = movieRepository.findMovieBy(userId, movie.name());

        if (movieExists.filter(m -> m.yearProduced() == movie.yearProduced()).isEmpty()) {
            movieRepository.create(userId, movie);
        } else {
            throw new DuplicateRecordException("Movie already exists");
        }
    }

    @Override
    public List<Movie> getMoviesFor(UUID userId) {
        return movieRepository.getAllMoviesForUser(userId);
    }

    @Override
    public void updateMovie(Movie movie) {
        Optional<Movie> oldMovie = movieRepository.findMovieBy(movie.id());

        oldMovie.ifPresent(om -> {
            Movie newMovieToUpdate = ImmutableMovie.copyOf(om)
                    .withName(StringUtils.isBlank(movie.name()) ? om.name() : movie.name())
                    .withYearProduced(movie.yearProduced() == 0 ? om.yearProduced() : movie.yearProduced())
                    .withRating(movie.rating() == null || movie.rating().equals(BigDecimal.ZERO) ? om.rating() : movie.rating());

            movieRepository.update(newMovieToUpdate);
        });
    }

    @Override
    public void deleteMovie(UUID movieId) {
        movieRepository.delete(movieId);
    }
}
