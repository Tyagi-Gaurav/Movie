package com.gt.scr.movie.dao;

import com.gt.scr.movie.config.DatabaseConfig;
import com.gt.scr.movie.config.ModifiableDatabaseConfig;
import com.gt.scr.movie.service.domain.User;
import com.gt.scr.movie.util.UserBuilder;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gt.scr.movie.util.TestUtils.addToDatabase;
import static com.gt.scr.movie.util.UserBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserMySQLRepository.class)
@ExtendWith(MockitoExtension.class)
@Import(UserMySQLRepositoryTest.TestMovieRepoContextConfiguration.class)
class UserMySQLRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private final UserRepository buggyUserRepository = new UserMySQLRepository();

    @Autowired
    private DataSource dataSource;

    @MockBean
    private DatabaseConfig databaseConfig;

    private static final String ADD_USER =
            "INSERT INTO USER (ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES) values (?, ?, ?, ?, ?, ?)";

    private static final String DELETE_ALL_USERS = "DELETE FROM USER";

    private static final String SELECT_USER_BY_ID = "SELECT ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES FROM "
            + "USER WHERE ID = ?";

    @BeforeEach
    void setUp() throws SQLException {
        when(databaseConfig.duplicateInterval()).thenReturn(Duration.ofMillis(10));
        deleteAllUsers();
    }

    @Test
    void shouldFindUserByUserId() throws SQLException {
        //given
        User expectedUser = aUser().build();
        addToDatabase(expectedUser, dataSource, ADD_USER);

        //when
        Optional<User> user = userRepository.findUserBy(expectedUser.id());

        //then
        assertThat(user).isPresent().hasValue(expectedUser);
    }

    @Test
    void shouldReturnEmptyUserWhenNoUserFoundByUserId() {
        //given
        User expectedUser = aUser().build();

        //when
        Optional<User> user = userRepository.findUserBy(expectedUser.id());

        //then
        assertThat(user).isEmpty();
    }

    @Test
    void shouldHandleExceptionWhenFindUserByIdFails() throws SQLException {
        //given
        User expectedUser = aUser().build();
        addToDatabase(expectedUser, dataSource, ADD_USER);

        //when
        UUID userId = expectedUser.id();
        DatabaseException exception = catchThrowableOfType(()-> buggyUserRepository.findUserBy(userId),
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
        Optional<User> user = userRepository.findUserBy(expectedUser.getUsername());

        //then
        assertThat(user).isPresent().hasValue(expectedUser);
    }

    @Test
    void shouldHandleExceptionWhenFindUserByUserNameFails() throws SQLException {
        //given
        User expectedUser = aUser().build();
        addToDatabase(expectedUser, dataSource, ADD_USER);

        //when
        String username = expectedUser.getUsername();
        DatabaseException exception = catchThrowableOfType(()->
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
        Optional<User> user = userRepository.findUserBy(expectedUser.getUsername());

        //then
        assertThat(user).isEmpty();
    }

    @Test
    void shouldGetAllUsers() throws SQLException {
        //given
        User expectedUserA = aUser().build();
        addToDatabase(expectedUserA, dataSource, ADD_USER);
        User expectedUserB = aUser().build();
        addToDatabase(expectedUserB, dataSource, ADD_USER);

        //when
        List<User> allUsers = userRepository.getAllUsers();

        //then
        assertThat(allUsers).containsExactlyInAnyOrderElementsOf(List.of(expectedUserA, expectedUserB));
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
        List<User> allUsers = userRepository.getAllUsers();

        //then
        assertThat(allUsers).isEmpty();
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
        userRepository.update(updatedUser);

        //then
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
        DatabaseException databaseException =
                catchThrowableOfType(() -> buggyUserRepository.update(updatedUser), DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
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
        DatabaseException databaseException =
                catchThrowableOfType(() -> buggyUserRepository.update(currentUser), DatabaseException.class);

        //then
        assertThat(databaseException).isNotNull();
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
        public DatabaseConfig mySQLTestConfig() {
            return ModifiableDatabaseConfig.create();
        }

        @Bean
        public DataSource inMemoryDataSource() {
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