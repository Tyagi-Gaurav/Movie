package com.gt.scr.user.dao;

import com.gt.scr.exception.DatabaseException;
import com.gt.scr.user.service.domain.User;
import com.gt.scr.user.util.UserBuilder;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.sql.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gt.scr.user.util.TestUtils.addToDatabase;
import static com.gt.scr.user.util.UserBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@SpringBootTest(classes = UserMySQLRepository.class)
@ExtendWith(MockitoExtension.class)
@Import(UserMySQLRepositoryTest.TestMovieRepoContextConfiguration.class)
class UserMySQLRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private final UserRepository buggyUserRepository = new UserMySQLRepository();

    @Autowired
    private DataSource dataSource;

    private static final String ADD_USER =
            "INSERT INTO USER (ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES) values (?, ?, ?, ?, ?, ?)";

    private static final String DELETE_ALL_USERS = "DELETE FROM USER";

    private static final String SELECT_USER_BY_ID = "SELECT ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES FROM "
            + "USER WHERE ID = ?";

    @BeforeEach
    void setUp() throws SQLException {
        deleteAllUsers();
    }

    @Test
    void shouldFindUserByUserId() throws SQLException {
        //given
        User expectedUser = aUser().build();
        addToDatabase(expectedUser, dataSource, ADD_USER);

        //when
        Mono<User> user = userRepository.findUserBy(expectedUser.id());

        //then
        StepVerifier.create(user).expectNext(expectedUser)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyUserWhenNoUserFoundByUserId() {
        //given
        User expectedUser = aUser().build();

        //when
        Mono<User> user = userRepository.findUserBy(expectedUser.id());

        //then
        StepVerifier.create(user).verifyComplete();
    }

    @Test
    void shouldHandleExceptionWhenFindUserByIdFails() throws SQLException {
        //given
        User expectedUser = aUser().build();
        addToDatabase(expectedUser, dataSource, ADD_USER);

        //when
        UUID userId = expectedUser.id();
        DatabaseException exception = catchThrowableOfType(() -> buggyUserRepository.findUserBy(userId),
                DatabaseException.class);

        //then
        assertThat(exception).isNotNull();
    }

    @Test
    void shouldFindUserByUserName() throws SQLException {
        //given
        User expectedUser = aUser().build();
        addToDatabase(expectedUser, dataSource, ADD_USER);

        //when
        Mono<User> user = userRepository.findUserBy(expectedUser.getUsername());

        //then
        StepVerifier.create(user).expectNext(expectedUser).verifyComplete();
    }

    @Test
    void shouldHandleExceptionWhenFindUserByUserNameFails() throws SQLException {
        //given
        User expectedUser = aUser().build();
        addToDatabase(expectedUser, dataSource, ADD_USER);

        //when
        String username = expectedUser.getUsername();
        DatabaseException exception = catchThrowableOfType(() ->
                        buggyUserRepository.findUserBy(username),
                DatabaseException.class);

        //then
        assertThat(exception).isNotNull();
    }

    @Test
    void shouldReturnEmptyUserWhenNoUserFoundByUserName() {
        //given
        User expectedUser = aUser().build();

        //when
        Mono<User> user = userRepository.findUserBy(expectedUser.getUsername());

        //then
        StepVerifier.create(user).verifyComplete();
    }

    @Test
    void shouldGetAllUsers() throws SQLException {
        //given
        User expectedUserA = aUser().build();
        addToDatabase(expectedUserA, dataSource, ADD_USER);
        User expectedUserB = aUser().build();
        addToDatabase(expectedUserB, dataSource, ADD_USER);

        //when
        Flux<User> allUsers = userRepository.getAllUsers();

        //then
        StepVerifier.create(allUsers)
                .expectNext(expectedUserA, expectedUserB)
                .verifyComplete();
    }

    @Test
    void shouldHandleExceptionWhenGetAllUsersFails() throws SQLException {
        //given
        User expectedUserA = aUser().build();
        addToDatabase(expectedUserA, dataSource, ADD_USER);
        User expectedUserB = aUser().build();
        addToDatabase(expectedUserB, dataSource, ADD_USER);

        //when
        DatabaseException databaseException =
                catchThrowableOfType(buggyUserRepository::getAllUsers, DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersFound() {
        //when
        Flux<User> allUsers = userRepository.getAllUsers();

        //then
        StepVerifier.create(allUsers)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldDeleteUser() throws SQLException {
        //given
        User currentUser = aUser().build();
        addToDatabase(currentUser, dataSource, ADD_USER);

        //when
        userRepository.delete(currentUser.id());

        //then
        assertThat(getUser(currentUser.id())).isEmpty();
    }

    @Test
    void shouldHandleExceptionWhenDeleteUserFails() throws SQLException {
        //given
        User currentUser = aUser().build();
        addToDatabase(currentUser, dataSource, ADD_USER);

        //when
        UUID currentUserId = currentUser.id();
        DatabaseException databaseException =
                catchThrowableOfType(() -> buggyUserRepository.delete(currentUserId), DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
    }

    @Test
    void shouldUpdateUser() throws SQLException {
        //given
        User currentUser = aUser().build();
        addToDatabase(currentUser, dataSource, ADD_USER);

        //when
        User updatedUser = UserBuilder.copyOf(currentUser).withFirstName("test").build();
        Mono<Void> update = userRepository.update(updatedUser);

        //then
        StepVerifier.create(update).verifyComplete();
        Optional<User> user = getUser(currentUser.id());
        assertThat(user).isNotEmpty();
        assertThat(user.get().firstName()).isEqualTo("test");
    }

    @Test
    void shouldHandleExceptionWhenUpdateUserFails() throws SQLException {
        //given
        User currentUser = aUser().build();
        addToDatabase(currentUser, dataSource, ADD_USER);

        //when
        User updatedUser = UserBuilder.copyOf(currentUser).withFirstName("test").build();
        Mono<Void> updateWithException = buggyUserRepository.update(updatedUser);

        //then
        StepVerifier.create(updateWithException)
                .expectError(DatabaseException.class)
                .verify();
    }

    @Test
    void shouldCreateUser() throws SQLException {
        //given
        User currentUser = aUser().build();

        //when
        userRepository.create(currentUser);

        //then
        Optional<User> user = getUser(currentUser.id());
        assertThat(user).isNotEmpty().hasValue(currentUser);
    }

    @Test
    void shouldHandleExceptionWhenCreateUserFails() {
        //given
        User currentUser = aUser().build();

        //when
        Mono<Void> updateWithException = buggyUserRepository.update(currentUser);

        //then
        StepVerifier.create(updateWithException)
                .expectError(DatabaseException.class)
                .verify();
    }

    private Optional<User> getUser(UUID id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        new User(
                                UUID.fromString(resultSet.getString("ID")),
                                resultSet.getString("FIRST_NAME"),
                                resultSet.getString("LAST_NAME"),
                                resultSet.getString("USER_NAME"),
                                resultSet.getString("PASSWORD"),
                                Arrays.stream(resultSet.getString("ROLES").split(","))
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList())
                        ));
            }

            resultSet.close();

            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
        public DataSource inMemoryDataSource() {
            ComboPooledDataSource cpds = new ComboPooledDataSource();

            try {

                URL resource = TestMovieRepoContextConfiguration.class.getClassLoader().getResource("db.changelog/userchangelog.sql");
                assertThat(resource).describedAs("Unable to find sql file to create database").isNotNull();
                String tempFile = resource.toURI().getRawPath();
                cpds.setDriverClass("org.h2.Driver");
                String jdbcUrl =
                        String.format("jdbc:h2:mem:testdb_user;MODE=MySQL;DB_CLOSE_DELAY=-1;" +
                                "DB_CLOSE_ON_EXIT=TRUE;INIT=RUNSCRIPT FROM '%s'", tempFile);
                cpds.setJdbcUrl(jdbcUrl);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            return cpds;
        }
    }
}