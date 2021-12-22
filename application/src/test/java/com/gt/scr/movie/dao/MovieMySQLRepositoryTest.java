package com.gt.scr.movie.dao;

import com.gt.scr.domain.User;
import com.gt.scr.exception.DatabaseException;
import com.gt.scr.movie.service.domain.Movie;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static com.gt.scr.movie.util.MovieBuilder.aMovie;
import static com.gt.scr.movie.util.MovieBuilder.copyOf;
import static com.gt.scr.movie.util.TestUtils.*;
import static com.gt.scr.movie.util.UserBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@SpringBootTest(classes = MovieMySQLRepository.class)
@ExtendWith(MockitoExtension.class)
@Import(MovieMySQLRepositoryTest.TestMovieRepoContextConfiguration.class)
class MovieMySQLRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    private final MovieRepository buggyMovieRepository = new MovieMySQLRepository();

    @Autowired
    private DataSource dataSource;

    private static final String ADD_USER =
            "INSERT INTO MOVIE_SCHEMA.USER (ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES) values (?, ?, ?, ?, ?, ?)";

    private static final String ADD_MOVIE =
            "INSERT INTO MOVIE_SCHEMA.MOVIE (ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP, USER_ID) values (?, ?, ?, ?, ?, ?)";

    private static final String DELETE_ALL_MOVIES = "DELETE FROM MOVIE_SCHEMA.MOVIE";

    private static final String SELECT_MOVIE_BY_ID = "SELECT ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP FROM "
            + "MOVIE_SCHEMA.MOVIE WHERE ID = ?";

    @BeforeEach
    void setUp() throws SQLException {
        executeQueryUpdate(dataSource, DELETE_ALL_MOVIES);
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
    void shouldReturnEmptyMovieWhenNoMovieFoundById() throws SQLException {
        //given
        Movie expectedMovie = aMovie().build();
        User user = aUser().build();

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
        addToDatabase(expectedMovieA, dataSource, user.id(), ADD_MOVIE);
        Movie expectedMovieB = aMovie().build();
        addToDatabase(expectedMovieB, dataSource, user.id(), ADD_MOVIE);

        //when
        DatabaseException databaseException =
                catchThrowableOfType(() -> buggyMovieRepository.getAllMoviesForUser(user.id()),
                        DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
    }

    @Test
    void shouldReturnEmptyListWhenNoMoviesFoundForUser() throws SQLException {
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
        Movie updatedMovie = copyOf(expectedMovieA).withName("test").build();
        Mono<Void> update = movieRepository.update(updatedMovie);

        //then
        StepVerifier.create(update).verifyComplete();
        Optional<Movie> movie = getMovie(updatedMovie.id(), dataSource, SELECT_MOVIE_BY_ID);
        assertThat(movie).isNotEmpty();
        assertThat(movie.get().name()).isEqualTo("test");
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
    void shouldHandleExceptionWhenCreateUserFails() throws SQLException {
        //given
        User currentUser = aUser().build();
        Movie expectedMovie = aMovie().build();

        //when
        DatabaseException databaseException =
                catchThrowableOfType(() -> buggyMovieRepository.update(expectedMovie), DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
    }

    @TestConfiguration
    static class TestMovieRepoContextConfiguration {

        @Bean
        public DataSource inMemoryMovieDataSource() {
            ComboPooledDataSource cpds = new ComboPooledDataSource();

            try {
                URL resource = TestMovieRepoContextConfiguration.class.getClassLoader().getResource("db.changelog/dbchangelog.sql");
                assertThat(resource).describedAs("Unable to find sql file to create database").isNotNull();
                String tempFile = resource.toURI().getRawPath();
                cpds.setDriverClass("org.h2.Driver");
                String jdbcUrl = String.format("jdbc:h2:mem:testdb_movie;MODE=MySQL;DB_CLOSE_DELAY=-1;" +
                        "DB_CLOSE_ON_EXIT=TRUE;INIT=RUNSCRIPT FROM '%s'", tempFile);
                cpds.setJdbcUrl(jdbcUrl);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            return cpds;
        }
    }
}