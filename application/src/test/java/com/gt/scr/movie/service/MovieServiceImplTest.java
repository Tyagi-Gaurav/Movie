package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.MovieRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.ImmutableMovie;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.util.TestBuilders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = MovieServiceImpl.class)
class MovieServiceImplTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;

    @Test
    void shouldAddMovie() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movie = TestBuilders.aMovie();
        when(movieRepository.findMovieBy(userId, movie.name())).thenReturn(Optional.empty());

        //when
        movieService.addMovie(userId, movie);

        //then
        verify(movieRepository).create(userId, movie);
    }

    @Test
    void shouldNotAllowAddingDuplicateMovieWithAllValuesSame() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movie = TestBuilders.aMovie();

        when(movieRepository.findMovieBy(userId, movie.name())).thenReturn(of(movie));

        //when
        DuplicateRecordException duplicateRecordException =
                catchThrowableOfType(() -> movieService.addMovie(userId, movie), DuplicateRecordException.class);

        //then
        assertThat(duplicateRecordException).isNotNull();
    }

    @Test
    void shouldAllowAddingDuplicateMovieWithOnlyNameSameButDiferentYear() {
        //given
        UUID userId = UUID.randomUUID();
        Movie movieA = TestBuilders.aMovie();
        Movie remakeOfMovieA = ImmutableMovie.copyOf(movieA).withYearProduced(2021);

        when(movieRepository.findMovieBy(userId, movieA.name())).thenReturn(of(movieA));

        //when
        Throwable throwable = catchThrowable(() -> movieService.addMovie(userId, remakeOfMovieA));

        //then
        assertThat(throwable).isNull();
        verify(movieRepository).create(userId, remakeOfMovieA);
    }

    @Test
    void shouldRetrieveMovie() {
        //given
        UUID userId = UUID.randomUUID();
        Movie aMovie = TestBuilders.aMovie();
        Movie bMovie = TestBuilders.aMovie();
        when(movieRepository.getAllMoviesForUser(userId)).thenReturn(List.of(aMovie, bMovie));

        //when
        List<Movie> movies = movieService.getMoviesFor(userId);

        //then
        assertThat(movies).containsExactlyInAnyOrderElementsOf(List.of(aMovie, bMovie));
    }

    @Test
    void shouldDeleteMovieForAUser() {
        //given
        Movie movie = TestBuilders.aMovie();

        //when
        movieService.deleteMovie(movie.id());

        //then
        verify(movieRepository).delete(movie.id());
    }

    @Test
    void shouldNotAllowUpdateCreationTimeStampForMovie() {
        //given
        Movie movieOld = TestBuilders.aMovie();
        Movie movieNew = ImmutableMovie.copyOf(movieOld).withCreationTimeStamp(System.nanoTime());
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(of(movieOld));

        //when
        movieService.updateMovie(movieNew);

        //then
        verify(movieRepository).update(movieOld);
    }

    @Test
    void shouldNotAllowUpdateZeroRatingForMovie() {
        //given
        Movie movieOld = TestBuilders.aMovie();
        Movie movieNew = ImmutableMovie.copyOf(movieOld).withRating(BigDecimal.ZERO);
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(of(movieOld));

        //when
        movieService.updateMovie(movieNew);

        //then
        verify(movieRepository).update(movieOld);
    }

    @Test
    void shouldAllowUpdateNameForMovie() {
        //given
        Movie movieOld = TestBuilders.aMovie();
        Movie movieNew = ImmutableMovie.copyOf(movieOld).withName("NewName");
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(of(movieOld));

        //when
        movieService.updateMovie(movieNew);

        //then
        verify(movieRepository).update(movieNew);
    }

    @Test
    void shouldAllowUpdateRatingForMovie() {
        //given
        Movie movieOld = TestBuilders.aMovie();
        Movie movieNew = ImmutableMovie.copyOf(movieOld).withRating(BigDecimal.ONE);
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(of(movieOld));

        //when
        movieService.updateMovie(movieNew);

        //then
        verify(movieRepository).update(movieNew);
    }

    @Test
    void shouldAllowUpdateYearProducedForMovie() {
        //given
        Movie movieOld = TestBuilders.aMovie();
        Movie movieNew = ImmutableMovie.copyOf(movieOld).withYearProduced(1900);
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(of(movieOld));

        //when
        movieService.updateMovie(movieNew);

        //then
        verify(movieRepository).update(movieNew);
    }

    @Test
    void shouldNotUpdateMovieWhenItDoesNotExist() {
        //given
        Movie movieOld = TestBuilders.aMovie();
        Movie movieNew = ImmutableMovie.copyOf(movieOld).withYearProduced(1900);
        when(movieRepository.findMovieBy(movieOld.id())).thenReturn(empty());

        //when
        movieService.updateMovie(movieNew);

        //then
        verify(movieRepository, times(0)).update(any());
    }
}