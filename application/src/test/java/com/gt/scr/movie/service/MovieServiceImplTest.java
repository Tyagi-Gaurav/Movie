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
        movieService.addMovie(userId, movie);

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
        movieService.addMovie(userId, movie);

        //when
        Throwable throwable = catchThrowable(() -> movieService.addMovie(userId, movie));

        //then
        assertThat(throwable).isNotNull()
                .isInstanceOf(DuplicateRecordException.class);
    }

    @Test
    void shouldRetrieveMovie() {
        //given
        UUID userId = UUID.randomUUID();
        User user = TestBuilders.aUserWithMovies();

        when(userService.findUserBy(userId)).thenReturn(user);

        //when
        Map<UUID, Movie> movies = movieService.getMovie(userId);

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
        movieService.deleteMovie(userId, movie.id());

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
        movieService.updateMovie(userId, movie);

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
        Movie updatedMovie = ImmutableMovie.copyOf(movieToUpdate)
                .withName(newMovieName);

        //when
        movieService.updateMovie(userId, updatedMovie);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

//        User actualParameter = userArgumentCaptor.getValue();
        //TODO fix this.
        // assertThat(actualParameter.movies()).contains(Map.entry(movieId, updatedMovie));
    }
}