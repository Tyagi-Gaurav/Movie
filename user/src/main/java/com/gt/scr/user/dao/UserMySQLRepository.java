package com.gt.scr.user.dao;

import com.gt.scr.exception.DatabaseException;
import com.gt.scr.user.service.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository("mysql")
public class UserMySQLRepository implements UserRepository {
    @Autowired
    private DataSource dataSource;

    private static final String USER_NAME = "USER_NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ROLES = "ROLES";

    @Override
    public Mono<User> findUserBy(UUID userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES FROM USER where ID = ?")) {
            preparedStatement.setString(1, userId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Mono.just(getUserFrom(resultSet));
                } else {
                    return Mono.empty();
                }
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Mono<User> findUserBy(String userName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES FROM USER where USER_NAME = ?")) {
            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Mono.just(getUserFrom(resultSet));
                } else {
                    return Mono.empty();
                }
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Flux<User> getAllUsers() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES FROM USER")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(getUserFrom(resultSet));
                }

                if (users.isEmpty()) {
                    return Flux.empty();
                }

                return Flux.fromIterable(users);
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Mono<Void> delete(UUID userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM USER where ID = ?")) {
            preparedStatement.setString(1, userId.toString());
            preparedStatement.execute();
            return Mono.empty();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Mono<Void> update(User user) {
        return Mono.fromRunnable(() -> {
            try {
                try (Connection connection = dataSource.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(
                             "UPDATE USER SET FIRST_NAME = ?, " +
                                     "LAST_NAME = ?, " +
                                     "PASSWORD = ?," +
                                     "ROLES = ? where ID = ?")) {

                    preparedStatement.setString(1, user.firstName());
                    preparedStatement.setString(2, user.lastName());
                    preparedStatement.setString(3, user.getPassword());
                    preparedStatement.setString(4, user.getRole());
                    preparedStatement.setString(5, user.id().toString());

                    preparedStatement.execute();
                } catch (SQLException throwables) {
                    throw new DatabaseException(throwables);
                }
            } catch (Exception e) {
                throw new DatabaseException(e);
            }
        }).then();
    }

    @Override
    public void create(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO USER (ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES) " +
                             "VALUES (?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, user.id().toString());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.firstName());
            preparedStatement.setString(4, user.lastName());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6,
                    StringUtils.join(user.getAuthorities(), ","));

            preparedStatement.execute();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    public User getUserFrom(ResultSet resultSet) throws SQLException {
        return new User(UUID.fromString(resultSet.getString("ID")),
                resultSet.getString(FIRST_NAME),
                resultSet.getString(LAST_NAME),
                resultSet.getString(USER_NAME),
                resultSet.getString(PASSWORD),
                Arrays.stream(resultSet.getString(ROLES).split(","))
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
