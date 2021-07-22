package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.MovieRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        movieRepository.update(movie);
    }

    @Override
    public void deleteMovie(UUID movieId) {
        movieRepository.delete(movieId);
    }
}
