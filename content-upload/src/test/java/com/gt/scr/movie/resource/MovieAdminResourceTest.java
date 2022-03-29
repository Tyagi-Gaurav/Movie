package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieCreateResponseDTO;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MovieUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.AgeRating;
import com.gt.scr.movie.service.domain.ContentType;
import com.gt.scr.movie.service.domain.Genre;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.util.MovieBuilder;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieAdminResourceTest {
    @Mock
    private MovieService movieService;

    @Mock
    private SecurityContextHolder securityContextHolder;

    private MovieResource movieAdminResource;

    @BeforeEach
    void setUp() {
        movieAdminResource = new MovieResource(movieService, securityContextHolder);
    }

    @Test
    void shouldAllowAdminToCreateMovies() {
        UUID requestedUserId = UUID.randomUUID();
        MovieCreateRequestDTO movieCreateRequestDTO =
                MovieCreateRequestDTOBuilder.aMovieCreateRequest().build();

        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "ADMIN", "token");
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));
        when(movieService.addMovie(eq(requestedUserId), eq(userProfile.id()), any(Movie.class))).thenReturn(Mono.empty());

        //when
        Mono<MovieCreateResponseDTO> movie = movieAdminResource.createMovieFor(movieCreateRequestDTO, requestedUserId.toString());

        StepVerifier.create(movie)
                .consumeNextWith(movieCreateResponseDTO -> {
                    assertThat(movieCreateResponseDTO.movieId()).isNotNull();
                })
                .verifyComplete();

        verify(movieService).addMovie(eq(requestedUserId), eq(userProfile.id()), any(Movie.class));
        verifyNoMoreInteractions(movieService);
    }

    @Test
    void shouldHandleFailureWhenAdminMovieCreateFails() {
        UUID requestedUserId = UUID.randomUUID();
        MovieCreateRequestDTO movieCreateRequestDTO =
                MovieCreateRequestDTOBuilder.aMovieCreateRequest().build();

        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "ADMIN", "token");
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));
        when(movieService.addMovie(eq(requestedUserId), eq(userProfile.id()), any(Movie.class)))
                .thenReturn(Mono.error(new RuntimeException()));

        //when
        Mono<MovieCreateResponseDTO> movie = movieAdminResource.createMovieFor(movieCreateRequestDTO,
                requestedUserId.toString());

        StepVerifier.create(movie)
                .consumeErrorWith(throwable -> {
                    assertThat(throwable).isNotNull();
                })
                .verify();

        verify(movieService).addMovie(eq(requestedUserId), eq(userProfile.id()), any(Movie.class));
        verifyNoMoreInteractions(movieService);
    }

    @Test
    void shouldAllowAdminToReadMovies() {
        UUID requestedUserId = UUID.randomUUID();
        UUID usersOwnId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile(usersOwnId, "ADMIN", "token");

        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));
        Movie expectedReturnObject = MovieBuilder.aMovie().withMovieId(usersOwnId).build();

        when(movieService.getMoviesFor(requestedUserId)).thenReturn(Flux.just(expectedReturnObject));

        //when
        Mono<MoviesDTO> movie = movieAdminResource.getMovieForUser(requestedUserId.toString());

        StepVerifier.create(movie)
                .expectNext(new MoviesDTO(Collections.singletonList(
                        new MovieDTO(usersOwnId, expectedReturnObject.name(), expectedReturnObject.yearProduced(),
                                expectedReturnObject.rating(),
                                expectedReturnObject.genre(),
                                expectedReturnObject.contentType(),
                                expectedReturnObject.ageRating(),
                                expectedReturnObject.isShareable()))))
                .verifyComplete();

        verify(movieService).getMoviesFor(requestedUserId);
        verify(movieService, times(0)).getMoviesFor(usersOwnId);
    }

    @Test
    void shouldAllowUserToDeleteMovies() {
        UUID movieId = UUID.randomUUID();

        //when
        movieAdminResource.deleteMovie(movieId.toString());
        verify(movieService).deleteMovie(movieId);
    }

    @Test
    void shouldAllowUserToUpdateMovies() {
        UUID usersOwnId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile(usersOwnId, "ADMIN", "token");
        MovieUpdateRequestDTO movieUpdateRequestDTO = new MovieUpdateRequestDTO(UUID.randomUUID(), randomAlphabetic(5),
                BigDecimal.ZERO, 2010, Genre.SUSPENSE, ContentType.TV_SERIES,
                AgeRating.EIGHTEEN, true);
        when(movieService.updateMovie(eq(usersOwnId), any(Movie.class))).thenReturn(Mono.empty());
        when(securityContextHolder.getContext(UserProfile.class)).thenReturn(Mono.just(userProfile));

        //when
        Mono<Void> voidMono = movieAdminResource.updateMovie(movieUpdateRequestDTO);

        StepVerifier.create(voidMono).verifyComplete();
        verify(movieService).updateMovie(any(UUID.class), any(Movie.class));
    }
}