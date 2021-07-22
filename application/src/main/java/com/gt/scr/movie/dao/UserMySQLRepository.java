package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.ImmutableUser;
import com.gt.scr.movie.service.domain.User;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;

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
    public Optional<User> findUserBy(UUID userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES FROM USER where ID = ?")) {
            preparedStatement.setString(1, userId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return of(ImmutableUser.builder()
                            .id(UUID.fromString(resultSet.getString("ID")))
                            .username(resultSet.getString(USER_NAME))
                            .firstName(resultSet.getString(FIRST_NAME))
                            .lastName(resultSet.getString(LAST_NAME))
                            .password(resultSet.getString(PASSWORD))
                            .authorities(Arrays.stream(resultSet.getString(ROLES).split(","))
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList()))
                            .build());
                } else {
                    return empty();
                }
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findUserBy(String userName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES FROM USER where USER_NAME = ?")) {
            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return of(ImmutableUser.builder()
                            .id(UUID.fromString(resultSet.getString("ID")))
                            .username(resultSet.getString(USER_NAME))
                            .firstName(resultSet.getString(FIRST_NAME))
                            .lastName(resultSet.getString(LAST_NAME))
                            .password(resultSet.getString(PASSWORD))
                            .authorities(Arrays.stream(resultSet.getString(ROLES).split(","))
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList()))
                            .build());
                } else {
                    return empty();
                }
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ID, USER_NAME, FIRST_NAME, LAST_NAME, PASSWORD, ROLES FROM USER")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(ImmutableUser.builder()
                            .id(UUID.fromString(resultSet.getString("ID")))
                            .username(resultSet.getString(USER_NAME))
                            .firstName(resultSet.getString(FIRST_NAME))
                            .lastName(resultSet.getString(LAST_NAME))
                            .password(resultSet.getString(PASSWORD))
                            .authorities(Arrays.stream(resultSet.getString(ROLES).split(","))
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList()))
                            .build());
                }

                return users;
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(UUID userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM USER where ID = ?")) {
            preparedStatement.setString(1, userId.toString());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) {
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
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
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
                    Strings.join(user.getAuthorities()).with(","));

            preparedStatement.execute();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }
}
