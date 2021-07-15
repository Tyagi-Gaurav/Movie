package com.gt.scr.movie.dao;

import com.gt.scr.movie.config.DatabaseConfig;
import com.gt.scr.movie.config.ModifiableDatabaseConfig;
import com.gt.scr.movie.service.domain.ImmutableUser;
import com.gt.scr.movie.service.domain.User;
import com.gt.scr.movie.util.TestBuilders;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.assertj.core.util.Strings;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserMySQLRepository.class)
@ExtendWith(MockitoExtension.class)
@Import(UserMySQLRepositoryTest.TestMovieRepoContextConfiguration.class)
class UserMySQLRepositoryTest {
    @Autowired
    private UserRepository userRepository;

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
        User expectedUser = TestBuilders.aUser();
        addToDatabase(expectedUser);

        //when
        User user = userRepository.findUserBy(expectedUser.id());

        //then
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(expectedUser);
    }

    @Test
    void shouldFindUserByUserName() throws SQLException {
        //given
        User expectedUser = TestBuilders.aUser();
        addToDatabase(expectedUser);

        //when
        User user = userRepository.findUserBy(expectedUser.getUsername());

        //then
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(expectedUser);
    }

    @Test
    void shouldGetAllUsers() throws SQLException {
        //given
        User expectedUserA = TestBuilders.aUser();
        addToDatabase(expectedUserA);
        User expectedUserB = TestBuilders.aUser();
        addToDatabase(expectedUserB);

        //when
        List<User> allUsers = userRepository.getAllUsers();

        //then
        assertThat(allUsers).containsExactlyInAnyOrderElementsOf(List.of(expectedUserA, expectedUserB));
    }

    @Test
    void shouldDeleteUser() throws SQLException {
        //given
        User currentUser = TestBuilders.aUser();
        addToDatabase(currentUser);

        //when
        userRepository.delete(currentUser.id());

        //then
        assertThat(getUser(currentUser.id())).isEmpty();
    }

    @Test
    void shouldUpdateUser() throws SQLException {
        //given
        User currentUser = TestBuilders.aUser();
        addToDatabase(currentUser);

        //when
        ImmutableUser updatedUser = ImmutableUser.copyOf(currentUser).withFirstName("test");
        userRepository.update(updatedUser);

        //then
        Optional<User> user = getUser(currentUser.id());
        assertThat(user).isNotEmpty();
        assertThat(user.get().firstName()).isEqualTo("test");
    }

    private Optional<User> getUser(UUID id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(ImmutableUser.builder()
                        .id(UUID.fromString(resultSet.getString("ID")))
                        .username(resultSet.getString("USER_NAME"))
                        .firstName(resultSet.getString("FIRST_NAME"))
                        .lastName(resultSet.getString("LAST_NAME"))
                        .password(resultSet.getString("PASSWORD"))
                        .authorities(Arrays.stream(resultSet.getString("ROLES").split(","))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList()))
                        .build());
            }

            resultSet.close();

            return Optional.empty();
        }
    }

    private void addToDatabase(User expectedUser) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER);) {
            preparedStatement.setString(1, expectedUser.id().toString());
            preparedStatement.setString(2, expectedUser.getUsername());
            preparedStatement.setString(3, expectedUser.firstName());
            preparedStatement.setString(4, expectedUser.lastName());
            preparedStatement.setString(5, expectedUser.getPassword());
            preparedStatement.setString(6,
                    Strings.join(expectedUser.getAuthorities()).with(","));
            preparedStatement.execute();
        }
    }

    private void deleteAllUsers() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_USERS)) {
            preparedStatement.execute();
        }
    }

    // Test for unique user insert

    @TestConfiguration
    static class TestMovieRepoContextConfiguration {

        @Bean
        public DatabaseConfig mySQLConfig() {
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