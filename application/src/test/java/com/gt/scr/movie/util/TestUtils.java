package com.gt.scr.movie.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.User;
import org.assertj.core.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TestUtils.class);
    final static ObjectMapper mapper = new ObjectMapper();

    public static String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addToDatabase(User expectedUser,
                                     DataSource dataSource,
                                     String query) throws SQLException {
        if (LOG.isInfoEnabled()) {
            LOG.info("Adding user with Id {}", expectedUser.id());
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, expectedUser.id().toString());
            preparedStatement.setString(2, expectedUser.getUsername());
            preparedStatement.setString(3, expectedUser.firstName());
            preparedStatement.setString(4, expectedUser.lastName());
            preparedStatement.setString(5, expectedUser.getPassword());
            preparedStatement.setString(6,
                    Strings.join(expectedUser.getAuthorities()).with(","));
            assertThat(preparedStatement.executeUpdate()).isPositive();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void addToDatabase(Movie movie, DataSource dataSource,
                               UUID userId, String query) throws SQLException {
        if (LOG.isInfoEnabled()) {
            LOG.info("Adding movie with Id {}", movie.id());
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, movie.id().toString());
            preparedStatement.setString(2, movie.name());
            preparedStatement.setInt(3, movie.yearProduced());
            preparedStatement.setBigDecimal(4, movie.rating());
            preparedStatement.setLong(5, movie.creationTimeStamp());
            preparedStatement.setString(6, userId.toString());
            assertThat(preparedStatement.executeUpdate()).isPositive();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Optional<Movie> getMovie(UUID movieId, DataSource dataSource, String query) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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

    public static void executeQueryUpdate(DataSource dataSource, String query) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            assertThat(preparedStatement.executeUpdate())
                    .describedAs("Could not run query. {}", query)
                    .isNotNegative();
        }
    }
}
