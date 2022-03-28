package com.gt.scr.movie.dao;

import com.gt.scr.domain.User;
import com.gt.scr.exception.DatabaseException;
import com.gt.scr.movie.service.domain.AgeRating;
import com.gt.scr.movie.service.domain.ContentType;
import com.gt.scr.movie.service.domain.Genre;
import com.gt.scr.movie.service.domain.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static com.gt.scr.movie.util.MovieBuilder.aMovie;
import static com.gt.scr.movie.util.MovieBuilder.copyOf;
import static com.gt.scr.movie.util.TestUtils.*;
import static com.gt.scr.movie.util.UserBuilder.aUser;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = MovieMySQLRepository.class)
@ExtendWith(MockitoExtension.class)
class MovieMySQLRepositoryTest extends DatabaseTest {
    @Autowired
    private MovieRepository movieRepository;

    private final MovieRepository buggyMovieRepository = new MovieMySQLRepository();

    @Autowired
    private DataSource dataSource;

    private static final String ADD_MOVIE =
            "INSERT INTO MOVIE_SCHEMA.MOVIE (ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP, AGE_RATING, CONTENT_TYPE, IS_SHAREABLE, GENRE, USER_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_MOVIE_BY_ID = "SELECT ID, NAME, YEAR_PRODUCED, RATING, AGE_RATING, CONTENT_TYPE, IS_SHAREABLE, GENRE, CREATION_TIMESTAMP FROM "
            + "MOVIE_SCHEMA.MOVIE WHERE ID = ?";

    @BeforeEach
    void setUp() throws SQLException {
        clearDatabase(dataSource);
    }

    @Test
    void shouldFindMovieById() throws SQLException {
        //given
        Movie expectedMovie = aMovie().build();
        User user = aUser().build();
        addToDatabase(expectedMovie, dataSource, user.id(), ADD_MOVIE);

        //when
        Mono<Movie> movie = movieRepository.findMovieBy(expectedMovie.id());

        //then
        StepVerifier.create(movie)
                .expectNext(expectedMovie)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyMovieWhenNoMovieFoundById() {
        //given
        Movie expectedMovie = aMovie().build();

        //when
        Mono<Movie> movie = movieRepository.findMovieBy(expectedMovie.id());

        //then
        StepVerifier.create(movie).verifyComplete();
    }

    @Test
    void shouldHandleExceptionWhenFindMovieByIdFails() throws SQLException {
        //given
        Movie expectedMovie = aMovie().build();
        User user = aUser().build();
        addToDatabase(expectedMovie, dataSource, user.id(), ADD_MOVIE);

        //when
        UUID movieId = expectedMovie.id();
        DatabaseException exception = catchThrowableOfType(() -> buggyMovieRepository.findMovieBy(movieId),
                DatabaseException.class);

        //then
        assertThat(exception).isNotNull();
    }

    @Test
    void shouldFindMovieByName() throws SQLException {
        //given
        Movie expectedMovie = aMovie().build();
        User user = aUser().build();
        addToDatabase(expectedMovie, dataSource, user.id(), ADD_MOVIE);

        //when
        Mono<Movie> movie = movieRepository.findMovieBy(user.id(), expectedMovie.name());

        //then
        StepVerifier.create(movie).expectNext(expectedMovie).verifyComplete();
    }

    @Test
    void shouldRetrieveRatingCorrectlyWithAppropriateScale() throws SQLException {
        //given
        Movie expectedMovie = aMovie().withRating(BigDecimal.valueOf(7.8)).build();

        User user = aUser().build();
        addToDatabase(expectedMovie, dataSource, user.id(), ADD_MOVIE);

        //when
        Mono<Movie> movie = movieRepository.findMovieBy(user.id(), expectedMovie.name());

        //then
        StepVerifier.create(movie)
                .expectNext(expectedMovie)
                .verifyComplete();
    }

    @Test
    void shouldNotFindMovieByNameForADifferentUser() throws SQLException {
        //given
        Movie expectedMovie = aMovie().build();
        User user = aUser().build();
        addToDatabase(expectedMovie, dataSource, user.id(), ADD_MOVIE);

        //when
        Mono<Movie> movie = movieRepository.findMovieBy(UUID.randomUUID(), expectedMovie.name());

        //then
        StepVerifier.create(movie).verifyComplete();
    }

    @Test
    void shouldHandleExceptionWhenFindMovieByNameFails() throws SQLException {
        //given
        Movie expectedMovie = aMovie().build();
        User user = aUser().build();
        UUID userId = user.id();
        addToDatabase(expectedMovie, dataSource, userId, ADD_MOVIE);

        //when
        String name = expectedMovie.name();
        DatabaseException exception = catchThrowableOfType(() -> buggyMovieRepository.findMovieBy(userId, name),
                DatabaseException.class);

        //then
        assertThat(exception).isNotNull();
    }

    @Test
    void shouldReturnEmptyMovieWhenNoMovieFoundByName() {
        //given
        Movie expectedMovie = aMovie().build();
        User user = aUser().build();

        //when
        Mono<Movie> movie = movieRepository.findMovieBy(user.id(), expectedMovie.name());

        //then
        StepVerifier.create(movie)
                .verifyComplete();
    }

    @Test
    void shouldGetAllMoviesForAUser() throws SQLException {
        //given
        User user = aUser().build();
        Movie expectedMovieA = aMovie().build();
        Movie expectedMovieB = aMovie().build();
        addToDatabase(expectedMovieA, dataSource, user.id(), ADD_MOVIE);
        addToDatabase(expectedMovieB, dataSource, user.id(), ADD_MOVIE);

        //when
        Flux<Movie> allMovies = movieRepository.getAllMoviesForUser(user.id());

        //then
        StepVerifier.create(allMovies)
                .expectNext(expectedMovieA)
                .expectNext(expectedMovieB)
                .verifyComplete();
    }

    @Test
    void shouldHandleExceptionWhenGetAllMoviesFails() throws SQLException {
        //given
        User user = aUser().build();
        Movie expectedMovieA = aMovie().build();
        UUID userId = user.id();
        addToDatabase(expectedMovieA, dataSource, userId, ADD_MOVIE);
        Movie expectedMovieB = aMovie().build();
        addToDatabase(expectedMovieB, dataSource, userId, ADD_MOVIE);

        //when & then
        assertThatThrownBy(() -> buggyMovieRepository.getAllMoviesForUser(userId))
                .isInstanceOf(DatabaseException.class);
    }

    @Test
    void shouldReturnEmptyListWhenNoMoviesFoundForUser() {
        //given
        User user = aUser().build();

        //when
        Flux<Movie> allMovies = movieRepository.getAllMoviesForUser(user.id());

        //then
        StepVerifier.create(allMovies).verifyComplete();
    }

    @Test
    void shouldDeleteMovieForUser() throws SQLException {
        //given
        User user = aUser().build();
        Movie expectedMovieA = aMovie().build();
        addToDatabase(expectedMovieA, dataSource, user.id(), ADD_MOVIE);

        //when
        movieRepository.delete(expectedMovieA.id());

        //then
        assertThat(getMovie(expectedMovieA.id(), dataSource, SELECT_MOVIE_BY_ID)).isEmpty();
    }

    @Test
    void shouldHandleExceptionWhenDeleteMovieFails() throws SQLException {
        //given
        User user = aUser().build();
        Movie expectedMovieA = aMovie().build();
        UUID movieId = expectedMovieA.id();
        addToDatabase(expectedMovieA, dataSource, user.id(), ADD_MOVIE);

        //when
        DatabaseException databaseException =
                catchThrowableOfType(() -> buggyMovieRepository.delete(movieId), DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
    }

    @Test
    void shouldUpdateMovie() throws SQLException {
        //given
        User user = aUser().build();
        Movie expectedMovieA = aMovie().build();
        addToDatabase(expectedMovieA, dataSource, user.id(), ADD_MOVIE);

        //when
        Movie updatedMovie = copyOf(expectedMovieA).withName("test")
                .withGenre(Genre.Suspense)
                .withAgeRating(AgeRating.EIGHTEEN)
                .withContentType(ContentType.TV_SERIES)
                .withIsShareable(false)
                .build();
        Mono<Void> update = movieRepository.update(updatedMovie);

        //then
        StepVerifier.create(update).verifyComplete();
        Optional<Movie> movie = getMovie(updatedMovie.id(), dataSource, SELECT_MOVIE_BY_ID);
        assertThat(movie).isNotEmpty();
        assertThat(movie.get().name()).isEqualTo("test");
        assertThat(movie.get().genre()).isEqualTo(updatedMovie.genre());
        assertThat(movie.get().ageRating()).isEqualTo(updatedMovie.ageRating());
        assertThat(movie.get().isShareable()).isEqualTo(updatedMovie.isShareable());
        assertThat(movie.get().contentType()).isEqualTo(updatedMovie.contentType());
    }

    @Test
    void shouldHandleExceptionWhenUpdateMovieFails() throws SQLException {
        //given
        User user = aUser().build();
        Movie expectedMovieA = aMovie().build();
        addToDatabase(expectedMovieA, dataSource, user.id(), ADD_MOVIE);

        //when
        Movie updatedMovie = copyOf(expectedMovieA).withName("test").build();
        DatabaseException databaseException =
                catchThrowableOfType(() -> buggyMovieRepository.update(updatedMovie), DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
    }

    @Test
    void shouldCreateMovie() throws SQLException {
        //given
        User currentUser = aUser().build();
        Movie expectedMovie = aMovie().build();

        //when
        movieRepository.create(currentUser.id(), expectedMovie);

        //then
        Optional<Movie> movie = getMovie(expectedMovie.id(), dataSource, SELECT_MOVIE_BY_ID);
        assertThat(movie).isNotEmpty().hasValue(expectedMovie);
    }

    @Test
    void shouldHandleExceptionWhenCreateUserFails() {
        //given
        Movie expectedMovie = aMovie().build();

        //when
        DatabaseException databaseException =
                catchThrowableOfType(() -> buggyMovieRepository.update(expectedMovie), DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
    }
}