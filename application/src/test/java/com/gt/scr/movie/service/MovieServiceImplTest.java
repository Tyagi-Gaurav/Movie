package com.gt.scr.movie.service;

import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.User;
import com.gt.scr.movie.util.TestBuilders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = MovieServiceImpl.class)
class MovieServiceImplTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private UserService userService;

    @Test
    void shouldAddMovie() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movie = TestBuilders.aMovie();
        User user = TestBuilders.aUser();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        movieService.addMovieRating(userId, movie);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User actualParameter = userArgumentCaptor.getValue();
        assertThat(actualParameter.movies()).contains(Map.entry(movie.id(), movie));
    }

    @Test
    void shouldNotAllowAddingDuplicateMovieWithAllValuesSame() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movie = TestBuilders.aMovie();
        User user = TestBuilders.aUser();

        when(userService.findUserBy(userId)).thenReturn(user);
        movieService.addMovieRating(userId, movie);

        //when
        Throwable throwable = catchThrowable(() -> movieService.addMovieRating(userId, movie));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(DuplicateRecordException.class);
    }

    @Test
    void shouldRetrieveMovie() {
        //given
        UUID userId = UUID.randomUUID();
        User user = TestBuilders.aUserWithMovies();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        Map<UUID, Movie> movies = movieService.getMovieRating(userId);

        //then
        assertThat(movies).isEqualTo(user.movies());
    }

    @Test
    void shouldDeleteMovieForAUser() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movie = TestBuilders.aMovie();
        User user = TestBuilders.aUser();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        movieService.deleteMovieRating(userId, movie.id());

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User actualParameter = userArgumentCaptor.getValue();
        assertThat(actualParameter.movies()).doesNotContain(Map.entry(movie.id(), movie));
    }

    @Test
    void shouldUpdateMovieForAUser() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movie = TestBuilders.aMovie();
        User user = TestBuilders.aUser();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        movieService.updateMovieRating(userId, movie);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User actualParameter = userArgumentCaptor.getValue();
        assertThat(actualParameter.movies()).isEmpty();
    }

    @Test
    void shouldUpdateOnlyMovieNameForAUser() {
        //given
        UUID userId = UUID.randomUUID();
        UUID movieId = UUID.randomUUID();
        Movie movieToUpdate = TestBuilders.aMovie();
        HashMap<UUID, Movie> movieMap = new HashMap<>();

        movieMap.put(movieId, movieToUpdate);
        User user = TestBuilders.aUserWithMovies(movieMap);

        when(userService.findUserBy(userId)).thenReturn(user);
        String newMovieName = "newName";
        Movie expectedMovie = ImmutableMovie.builder()
                .name(newMovieName)
                .rating(BigDecimal.valueOf(2.3))
                .yearProduced(2021)
                .id(movieId)
                .build();

        //when
        Movie updatedMovie = ImmutableMovie.builder()
                .name(newMovieName)
                .rating(BigDecimal.valueOf(2.3))
                .yearProduced(2021)
                .id(movieId)
                .build();
        movieService.updateMovieRating(userId, updatedMovie);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User actualParameter = userArgumentCaptor.getValue();
        assertThat(actualParameter.movies()).contains(Map.entry(movieId, expectedMovie));
    }
}