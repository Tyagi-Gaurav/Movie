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
class MovieResourceTest {
    @Mock
    private MovieService movieService;

    private MovieResource movieResource;

    @BeforeEach
    void setUp() {
        movieResource = new MovieResource(movieService);
    }

    @Test
    void shouldAllowUserToCreateMovie() throws Exception {
        MovieCreateRequestDTO movieCreateRequestDTO = new MovieCreateRequestDTO(randomAlphabetic(6),
                2010, BigDecimal.ONE);

        UUID userId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile(userId, "USER");

        //when
        movieResource.createMovie(movieCreateRequestDTO, null);

//        verify(movieService).addMovie(eq(userProfile.id()), any(Movie.class));
    }

    @Test
    void shouldAllowUserToReadMovies() throws Exception {
        UUID id = UUID.randomUUID();
        UserProfile userProfile = new UserProfile(id, "USER");

        var expectedReturnObject = new Movie(id, randomAlphabetic(5),
                2010, BigDecimal.ONE, System.nanoTime());

//        when(movieService.getMoviesFor(id)).thenReturn(Flux.just(expectedReturnObject));

        //when
//        Mono<MoviesDTO> movies = movieResource.getMovie(userProfile, null);
//
//        MoviesDTO moviesDTO = movies.block();
//
//        StepVerifier.create(moviesDTO.movies())
//                .expectNext(new MovieDTO(id, expectedReturnObject.name(), expectedReturnObject.yearProduced(),
//                        expectedReturnObject.rating()))
//                .verifyComplete();
    }

    @Test
    void shouldAllowUserToDeleteMovies() {
        UUID movieId = UUID.randomUUID();

        //when
        movieResource.deleteMovie(movieId.toString());

        verify(movieService).deleteMovie(movieId);
    }

    @Test
    void shouldAllowUserToUpdateMovies() throws Exception {
        MovieUpdateRequestDTO movieUpdateRequestDTO = new MovieUpdateRequestDTO(UUID.randomUUID(),
                randomAlphabetic(5), BigDecimal.ZERO, 2010);

        var userProfile = new UserProfile(UUID.randomUUID(), "USER");

        //when
        movieResource.updateMovie(movieUpdateRequestDTO);

        verify(movieService).updateMovie(any(Movie.class));
    }
}