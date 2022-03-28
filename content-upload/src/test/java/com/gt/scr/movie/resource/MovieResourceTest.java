package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieCreateResponseDTO;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MovieUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.util.MovieCreateRequestDTOBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieResourceTest {
    @Mock
    private MovieService movieService;

    @Mock
    private SecurityContextHolder securityContextHolder;

    private MovieResource movieResource;

    @BeforeEach
    void setUp() {
        movieResource = new MovieResource(movieService, securityContextHolder);
    }

    @Test
    void shouldAllowUserToCreateMovie() {
        MovieCreateRequestDTO movieCreateRequestDTO = MovieCreateRequestDTOBuilder.aMovieCreateRequest().build();

        UUID userId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile(userId, "USER", "token");
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));
        when(movieService.addMovie(eq(userId), eq(userId), any(Movie.class))).thenReturn(Mono.empty());

        //when
        Mono<MovieCreateResponseDTO> movie = movieResource.createMovie(movieCreateRequestDTO);

        StepVerifier.create(movie)
                .consumeNextWith(movieCreateResponseDTO -> {
                    assertThat(movieCreateResponseDTO.movieId()).isNotNull();
                })
                .verifyComplete();

        verify(movieService).addMovie(eq(userId), eq(userId), any(Movie.class));
    }

    @Test
    void shouldAllowUserToReadMovies() {
        UUID id = UUID.randomUUID();
        UserProfile userProfile = new UserProfile(id, "USER", "token");

        var expectedReturnObject = new Movie(id, randomAlphabetic(5),
                2010, BigDecimal.ONE, System.nanoTime());

        when(movieService.getMoviesFor(id)).thenReturn(Flux.just(expectedReturnObject));
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));

        //when
        Mono<MoviesDTO> movies = movieResource.getMovie();

        StepVerifier.create(movies)
                .expectNext(new MoviesDTO(Collections.singletonList(
                        new MovieDTO(id, expectedReturnObject.name(), expectedReturnObject.yearProduced(),
                                expectedReturnObject.rating()))))
                .verifyComplete();
    }

    @Test
    void shouldAllowUserToDeleteMovies() {
        UUID movieId = UUID.randomUUID();
        when(movieResource.deleteMovie(movieId.toString())).thenReturn(Mono.empty());

        //when
        Mono<Void> voidMono = movieResource.deleteMovie(movieId.toString());

        StepVerifier.create(voidMono).verifyComplete();
        verify(movieService).deleteMovie(movieId);
    }

    @Test
    void shouldAllowUserToUpdateMovies() {
        UUID id = UUID.randomUUID();
        UserProfile userProfile = new UserProfile(id, "USER", "token");
        MovieUpdateRequestDTO movieUpdateRequestDTO = new MovieUpdateRequestDTO(UUID.randomUUID(),
                randomAlphabetic(5), BigDecimal.ZERO, 2010);
        when(movieService.updateMovie(any(UUID.class), any(Movie.class))).thenReturn(Mono.empty());
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));

        //when
        Mono<Void> voidMono = movieResource.updateMovie(movieUpdateRequestDTO);

        StepVerifier.create(voidMono).verifyComplete();
        verify(movieService).updateMovie(any(UUID.class), any(Movie.class));
    }

    @Test
    void shouldAllowMoviesCreatedByOtherUsersToBeReadByAdmin() { //This test does not verify the security part of requirement.
        UUID id = UUID.randomUUID();
        UserProfile userProfile = new UserProfile(id, "ADMIN", "token");

        var expectedReturnObject = new Movie(id, randomAlphabetic(5),
                2010, BigDecimal.ONE, System.nanoTime());

        when(movieService.getMoviesFor(id)).thenReturn(Flux.just(expectedReturnObject));
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));

        //when
        Mono<MoviesDTO> movies = movieResource.getMovieForUser(userProfile.id().toString());

        StepVerifier.create(movies)
                .expectNext(new MoviesDTO(Collections.singletonList(
                        new MovieDTO(id, expectedReturnObject.name(), expectedReturnObject.yearProduced(),
                                expectedReturnObject.rating()))))
                .verifyComplete();
    }
}