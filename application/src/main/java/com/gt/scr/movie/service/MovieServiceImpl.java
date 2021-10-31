package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.MovieRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.Movie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Mono<Void> addMovie(UUID userId, Movie movie) {
        return movieRepository.findMovieBy(userId, movie.name())
                .flatMap(m -> {
                    if (m.yearProduced() == movie.yearProduced()) {
                        return Mono.error(new DuplicateRecordException("duplicate"));
                    } else {
                        return Mono.empty();
                    }
                })
                .switchIfEmpty(Mono.defer(() -> movieRepository.create(userId, movie)))
                .then();
    }

    @Override
    public Flux<Movie> getMoviesFor(UUID userId) {
        return movieRepository.getAllMoviesForUser(userId);
    }

    @Override
    public Mono<Void> updateMovie(Movie movie) {
        return movieRepository.findMovieBy(movie.id()).doOnNext(om -> {
            Movie newMovieToUpdate =
                    new Movie(om.id(),
                            StringUtils.isBlank(movie.name()) ? om.name() : movie.name(),
                            movie.yearProduced() == 0 ? om.yearProduced() : movie.yearProduced(),
                            movie.rating() == null || movie.rating().equals(BigDecimal.ZERO) ? om.rating() : movie.rating(),
                            om.creationTimeStamp());

            movieRepository.update(newMovieToUpdate);
        }).then();
    }

    @Override
    public Mono<Void> deleteMovie(UUID movieId) {
        return movieRepository.delete(movieId);
    }
}
