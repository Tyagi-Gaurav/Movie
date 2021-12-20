package com.gt.scr.movie.service;

import com.gt.scr.exception.DuplicateRecordException;
import com.gt.scr.movie.audit.MovieCreateEvent;
import com.gt.scr.movie.audit.MovieUpdateEvent;
import com.gt.scr.movie.audit.UserEventMessage;
import com.gt.scr.movie.dao.MovieRepository;
import com.gt.scr.movie.service.domain.Movie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final Sinks.Many<UserEventMessage> eventSink;

    public MovieServiceImpl(MovieRepository movieRepository,
                            @Qualifier("EventSink") Sinks.Many<UserEventMessage> eventSink) {
        this.movieRepository = movieRepository;
        this.eventSink = eventSink;
    }

    @Override
    public Mono<Void> addMovie(UUID ownerUserId, UUID originatorUserId, Movie movie) {
        return movieRepository.findMovieBy(ownerUserId, movie.name())
                .flatMap(m -> {
                    if (m.yearProduced() == movie.yearProduced()) {
                        return Mono.error(new DuplicateRecordException("duplicate"));
                    } else {
                        return Mono.empty();
                    }
                })
                .switchIfEmpty(Mono.defer(() -> {
                    movieRepository.create(ownerUserId, movie);
                    eventSink.emitNext(new MovieCreateEvent(ownerUserId, originatorUserId, movie.id(), movie.name(), movie.yearProduced(), movie.rating()),
                            Sinks.EmitFailureHandler.FAIL_FAST);
                    return Mono.empty();
                }))
                .then();
    }

    @Override
    public Flux<Movie> getMoviesFor(UUID userId) {
        return movieRepository.getAllMoviesForUser(userId);
    }

    @Override
    public Mono<Void> updateMovie(UUID ownerUserId, Movie movie) {
        return movieRepository.findMovieBy(movie.id())
                .doOnNext(om -> {
                    Movie newMovieToUpdate =
                            new Movie(om.id(),
                                    StringUtils.isBlank(movie.name()) ? om.name() : movie.name(),
                                    movie.yearProduced() == 0 ? om.yearProduced() : movie.yearProduced(),
                                    movie.rating() == null || movie.rating().equals(BigDecimal.ZERO) ? om.rating() : movie.rating(),
                                    om.creationTimeStamp());

                    movieRepository.update(newMovieToUpdate);
                })
                .doOnNext(om -> eventSink.emitNext(new MovieUpdateEvent(ownerUserId, ownerUserId, movie.name(), movie.yearProduced(), movie.rating()),
                        Sinks.EmitFailureHandler.FAIL_FAST))
                .then();
    }

    @Override
    public Mono<Void> deleteMovie(UUID movieId) {
        return movieRepository.delete(movieId);
    }
}
