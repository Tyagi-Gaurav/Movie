package com.gt.scr.movie.dao;

import com.gt.scr.movie.config.DatabaseConfig;
import com.gt.scr.movie.config.ModifiableDatabaseConfig;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.User;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import static com.gt.scr.movie.util.MovieBuilder.aMovie;
import static com.gt.scr.movie.util.MovieBuilder.copyOf;
import static com.gt.scr.movie.util.TestUtils.addToDatabase;
import static com.gt.scr.movie.util.UserBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = MovieMySQLRepository.class)
@ExtendWith(MockitoExtension.class)
@Import(MovieMySQLRepositoryTest.TestMovieRepoContextConfiguration.class)
class MovieMySQLRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    private final MovieRepository buggyMovieRepository = new MovieMySQLRepository();

    @Autowired
    private DataSource dataSource;

    @MockBean
    private DatabaseConfig databaseConfig;

    private static final String ADD_USER =
            "INSERT INTO USER (ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES) values (?, ?, ?, ?, ?, ?)";

    private static final String ADD_MOVIE =
            "INSERT INTO MOVIE (ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP, USER_ID) values (?, ?, ?, ?, ?, ?)";

    private static final String DELETE_ALL_MOVIES = "DELETE FROM MOVIE";
    private static final String DELETE_ALL_USERS = "DELETE FROM USER CASCADE";

    private static final String SELECT_MOVIE_BY_ID = "SELECT ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP FROM "
            + "MOVIE WHERE ID = ?";

    @BeforeEach
    void setUp() throws SQLException {
        when(databaseConfig.duplicateInterval()).thenReturn(Duration.ofMillis(10));
        deleteAllUsers();
        deleteAllMovies();
    }

    @Test
    void shouldFindMovieById() throws SQLException {
        //given
        Movie expectedMovie = aMovie().build();
        User user = aUser().build();
        addToDatabase(user, dataSource, ADD_USER);
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
        addToDatabase(user, dataSource, ADD_USER);

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
        addToDatabase(user, dataSource, ADD_USER);
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
        addToDatabase(user, dataSource, ADD_USER);
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
        addToDatabase(user, dataSource, ADD_USER);
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
        addToDatabase(user, dataSource, ADD_USER);
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
        addToDatabase(user, dataSource, ADD_USER);
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
    void shouldReturnEmptyMovieWhenNoMovieFoundByName() throws SQLException {
        //given
        Movie expectedMovie = aMovie().build();
        User user = aUser().build();
        addToDatabase(user, dataSource, ADD_USER);

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
        addToDatabase(user, dataSource, ADD_USER);
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
        Movie expectedMovieB = aMovie().build();
        addToDatabase(user, dataSource, ADD_USER);
        UUID userId = user.id();
        addToDatabase(expectedMovieA, dataSource, userId, ADD_MOVIE);
        addToDatabase(expectedMovieB, dataSource, userId, ADD_MOVIE);

        //when
        DatabaseException databaseException =
                catchThrowableOfType(() -> buggyMovieRepository.getAllMoviesForUser(userId), DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
    }

    @Test
    void shouldReturnEmptyListWhenNoMoviesFoundForUser() throws SQLException {
        //given
        User user = aUser().build();
        addToDatabase(user, dataSource, ADD_USER);

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
        addToDatabase(user, dataSource, ADD_USER);
        addToDatabase(expectedMovieA, dataSource, user.id(), ADD_MOVIE);

        //when
        movieRepository.delete(expectedMovieA.id());

        //then
        assertThat(getMovie(expectedMovieA.id())).isEmpty();
    }

    @Test
    void shouldHandleExceptionWhenDeleteUserFails() throws SQLException {
        //given
        User user = aUser().build();
        Movie expectedMovieA = aMovie().build();
        UUID movieId = expectedMovieA.id();
        addToDatabase(user, dataSource, ADD_USER);
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
        addToDatabase(user, dataSource, ADD_USER);
        addToDatabase(expectedMovieA, dataSource, user.id(), ADD_MOVIE);

        //when
        Movie updatedMovie = copyOf(expectedMovieA).withName("test").build();
        movieRepository.update(updatedMovie);

        //then
        Optional<Movie> movie = getMovie(updatedMovie.id());
        assertThat(movie).isNotEmpty();
        assertThat(movie.get().name()).isEqualTo("test");
    }

    @Test
    void shouldHandleExceptionWhenUpdateMovieFails() throws SQLException {
        //given
        User user = aUser().build();
        Movie expectedMovieA = aMovie().build();
        addToDatabase(user, dataSource, ADD_USER);
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
        addToDatabase(currentUser, dataSource, ADD_USER);
        Movie expectedMovie = aMovie().build();

        //when
        movieRepository.create(currentUser.id(), expectedMovie);

        //then
        Optional<Movie> movie = getMovie(expectedMovie.id());
        assertThat(movie).isNotEmpty().hasValue(expectedMovie);
    }

    @Test
    void shouldHandleExceptionWhenCreateUserFails() throws SQLException {
        //given
        User currentUser = aUser().build();
        addToDatabase(currentUser, dataSource, ADD_USER);
        Movie expectedMovie = aMovie().build();

        //when
        DatabaseException databaseException =
                catchThrowableOfType(() -> buggyMovieRepository.update(expectedMovie), DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
    }

    private Optional<Movie> getMovie(UUID movieId) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MOVIE_BY_ID)) {
            preparedStatement.setString(1, movieId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Movie(UUID.fromString(resultSet.getString("ID")),
                        resultSet.getString("NAME"),
                        resultSet.getInt("YEAR_PRODUCED"),
                        resultSet.getBigDecimal("RATING").setScale(1, RoundingMode.UNNECESSARY),
                        resultSet.getLong("CREATION_TIMESTAMP")));
            }

            resultSet.close();

            return Optional.empty();
        }
    }

    private void deleteAllMovies() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_MOVIES)) {
            preparedStatement.execute();
        }
    }

    private void deleteAllUsers() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_USERS)) {
            preparedStatement.execute();
        }
    }

    @TestConfiguration
    static class TestMovieRepoContextConfiguration {

        @Bean
        public DatabaseConfig mySQLMovieTestConfig() {
            return ModifiableDatabaseConfig.create();
        }

        @Bean
        public DataSource inMemoryMovieDataSource() {
            ComboPooledDataSource cpds = new ComboPooledDataSource();

            try {

                URL resource = TestMovieRepoContextConfiguration.class.getClassLoader().getResource("db.changelog/dbchangelog.sql");
                assertThat(resource).describedAs("Unable to find sql file to create database").isNotNull();
                String tempFile = resource.toURI().getRawPath();
                cpds.setDriverClass("org.h2.Driver");
                String jdbcUrl =
                        String.format("jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;" +
                                "DB_CLOSE_ON_EXIT=TRUE;INIT=RUNSCRIPT FROM '%s'", tempFile);
                cpds.setJdbcUrl(jdbcUrl);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            return cpds;
        }
    }
}