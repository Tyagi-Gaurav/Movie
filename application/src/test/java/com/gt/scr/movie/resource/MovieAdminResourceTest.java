package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieUpdateRequestDTO;
import com.gt.scr.movie.resource.domain.UserProfile;
import com.gt.scr.movie.service.MovieService;
import com.gt.scr.movie.service.domain.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

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
                new MovieCreateRequestDTO(randomAlphabetic(6), 2010, BigDecimal.valueOf(10));

        UserProfile userProfile = new UserProfile(UUID.randomUUID(), "ADMIN");

        //when
//        movieAdminResource.createMovie(movieCreateRequestDTO, userProfile, requestedUserId.toString());

//        verify(movieService).addMovie(eq(requestedUserId), any(Movie.class));
//        verify(movieService, times(0)).addMovie(eq(userProfile.id()),
//                any(Movie.class));
    }

    @Test
    void shouldAllowAdminToReadMovies() {
        UUID requestedUserId = UUID.randomUUID();
        UUID usersOwnId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile(usersOwnId, "ADMIN");

        Movie expectedReturnObject = new Movie(usersOwnId, randomAlphabetic(5),
                2010, BigDecimal.ONE, System.nanoTime());

//        when(movieService.getMoviesFor(requestedUserId)).thenReturn(Flux.just(expectedReturnObject));

        //when
//        MoviesDTO moviesDTO = movieAdminResource.getMovie(userProfile, requestedUserId.toString()).block();
//
//        verify(movieService).getMoviesFor(requestedUserId);
//        verify(movieService, times(0)).getMoviesFor(usersOwnId);
//
//        StepVerifier.create(moviesDTO.movies())
//                .expectNext(new MovieDTO(usersOwnId, expectedReturnObject.name(), expectedReturnObject.yearProduced(),
//                        expectedReturnObject.rating()))
//                .verifyComplete();
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
        MovieUpdateRequestDTO movieUpdateRequestDTO = new MovieUpdateRequestDTO(UUID.randomUUID(), randomAlphabetic(5),
                BigDecimal.ZERO, 2010);

        //when
        movieAdminResource.updateMovie(movieUpdateRequestDTO);

        verify(movieService).updateMovie(any(Movie.class));
    }
}