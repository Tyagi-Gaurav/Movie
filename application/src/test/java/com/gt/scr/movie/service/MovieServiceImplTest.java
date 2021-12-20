package com.gt.scr.movie.service;

import com.gt.scr.exception.DuplicateRecordException;
import com.gt.scr.movie.audit.UserEventMessage;
import com.gt.scr.movie.dao.MovieRepository;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.util.MovieBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static com.gt.scr.movie.util.MovieBuilder.aMovie;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private Sinks.Many<UserEventMessage> eventSink;

    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieService = new MovieServiceImpl(movieRepository, eventSink);
    }

    @Test
    void shouldAddMovie() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movie = aMovie().build();
        when(movieRepository.findMovieBy(userId, movie.name())).thenReturn(Mono.empty());
        when(movieRepository.create(userId, movie)).thenReturn(Mono.empty());

        //when
        Mono<Void> voidMono = movieService.addMovie(userId, userId, movie);

        //then
        StepVerifier.create(voidMono).verifyComplete();
        verify(movieRepository).create(userId, movie);
    }

    @Test
    void shouldNotAllowAddingDuplicateMovieWithAllValuesSame() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movie = aMovie().build();

        when(movieRepository.findMovieBy(userId, movie.name())).thenReturn(Mono.just(movie));

        //when
        Mono<Void> voidMono = movieService.addMovie(userId, userId, movie);

        StepVerifier.create(voidMono)
                .expectError(DuplicateRecordException.class)
                .verify();
    }

    @Test
    void shouldAllowAddingDuplicateMovieWithOnlyNameSameButDiferentYear() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movieA = aMovie().build();
        Movie remakeOfMovieA = MovieBuilder.copyOf(movieA).withYearProduced(2021).build();

        when(movieRepository.findMovieBy(userId, movieA.name())).thenReturn(Mono.just(movieA));
        when(movieRepository.create(userId, remakeOfMovieA)).thenReturn(Mono.empty());

        //when
        Mono<Void> voidMono = movieService.addMovie(userId, userId, remakeOfMovieA);

        //then
        StepVerifier.create(voidMono).verifyComplete();
        verify(movieRepository).create(userId, remakeOfMovieA);
    }

    @Test
    void shouldRetrieveMovie() {
        //given
        UUID userId = UUID.randomUUID();
        Movie aMovie = aMovie().build();
        Movie bMovie = aMovie().build();
        when(movieRepository.getAllMoviesForUser(userId)).thenReturn(Flux.just(aMovie, bMovie));

        //when
        Flux<Movie> movies = movieService.getMoviesFor(userId);

        //then
        StepVerifier.create(movies)
                .expectNext(aMovie)
                .expectNext(bMovie)
                .verifyComplete();
    }

    @Test
    void shouldDeleteMovieForAUser() {
        //given
        Movie movie = aMovie().build();
        when(movieRepository.delete(movie.id())).thenReturn(Mono.empty());

        //when
        Mono<Void> voidMono = movieService.deleteMovie(movie.id());

        //then
        StepVerifier.create(voidMono)
                .verifyComplete();
        verify(movieRepository).delete(movie.id());
    }

    @Test
    void shouldNotAllowUpdateCreationTimeStampForMovie() {
        //given
        Movie movieOld = aMovie().build();
        Movie movieNew = MovieBuilder.copyOf(movieOld).withCreationTimeStamp(System.nanoTime()).build();
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(Mono.just(movieOld));

        //when
        Mono<Void> voidMono = movieService.updateMovie(UUID.randomUUID(), movieNew);

        //then
        StepVerifier.create(voidMono).verifyComplete();
        verify(movieRepository).update(movieOld);
    }

    @Test
    void shouldNotAllowUpdateZeroRatingForMovie() {
        //given
        Movie movieOld = aMovie().build();
        Movie movieNew = MovieBuilder.copyOf(movieOld).withRating(BigDecimal.ZERO).build();
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(Mono.just(movieOld));

        //when
        Mono<Void> voidMono = movieService.updateMovie(UUID.randomUUID(), movieNew);

        //then
        StepVerifier.create(voidMono).verifyComplete();
        verify(movieRepository).update(movieOld);
    }

    @Test
    void shouldAllowUpdateNameForMovie() {
        //given
        Movie movieOld = aMovie().build();
        Movie movieNew = MovieBuilder.copyOf(movieOld).withName("NewName").build();
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(Mono.just(movieOld));

        //when
        Mono<Void> voidMono = movieService.updateMovie(UUID.randomUUID(), movieNew);

        //then
        StepVerifier.create(voidMono).verifyComplete();
        verify(movieRepository).update(movieNew);
    }

    @Test
    void shouldAllowUpdateRatingForMovie() {
        //given
        Movie movieOld = aMovie().build();
        Movie movieNew = MovieBuilder.copyOf(movieOld).withRating(BigDecimal.ONE).build();
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(Mono.just(movieOld));

        //when
        Mono<Void> voidMono = movieService.updateMovie(UUID.randomUUID(), movieNew);

        //then
        StepVerifier.create(voidMono).verifyComplete();
        verify(movieRepository).update(movieNew);
    }

    @Test
    void shouldAllowUpdateYearProducedForMovie() {
        //given
        Movie movieOld = aMovie().build();
        Movie movieNew = MovieBuilder.copyOf(movieOld).withYearProduced(1900).build();
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(Mono.just(movieOld));

        //when
        Mono<Void> voidMono = movieService.updateMovie(UUID.randomUUID(), movieNew);

        //then
        StepVerifier.create(voidMono).verifyComplete();
        verify(movieRepository).update(movieNew);
    }

    @Test
    void shouldNotUpdateMovieWhenItDoesNotExist() {
        //given
        Movie movieOld = aMovie().build();
        Movie movieNew = MovieBuilder.copyOf(movieOld).withYearProduced(1900).build();
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(Mono.empty());

        //when
        movieService.updateMovie(UUID.randomUUID(), movieNew);

        //then
        verify(movieRepository, times(0)).update(any());
    }
}