package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.MovieRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.Movie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void addMovie(UUID userId, Movie movie) {
        Optional<Movie> movieExists = movieRepository.findMovieBy(userId, movie.name()).blockOptional();

        if (movieExists.filter(m -> m.yearProduced() == movie.yearProduced()).isEmpty()) {
            movieRepository.create(userId, movie);
        } else {
            throw new DuplicateRecordException("Movie already exists");
        }
    }

    @Override
    public List<Movie> getMoviesFor(UUID userId) {
        return movieRepository.getAllMoviesForUser(userId).collectList().block();
    }

    @Override
    public void updateMovie(Movie movie) {
        Optional<Movie> oldMovie = movieRepository.findMovieBy(movie.id()).blockOptional();

        oldMovie.ifPresent(om -> {
            Movie newMovieToUpdate =
                    new Movie(om.id(),
                            StringUtils.isBlank(movie.name()) ? om.name() : movie.name(),
                            movie.yearProduced() == 0 ? om.yearProduced() : movie.yearProduced(),
                            movie.rating() == null || movie.rating().equals(BigDecimal.ZERO) ? om.rating() : movie.rating(),
                            om.creationTimeStamp());

            movieRepository.update(newMovieToUpdate);
        });
    }

    @Override
    public void deleteMovie(UUID movieId) {
        movieRepository.delete(movieId);
    }
}
