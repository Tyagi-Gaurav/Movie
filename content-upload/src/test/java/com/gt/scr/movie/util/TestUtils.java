package com.gt.scr.movie.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.movie.service.domain.AgeRating;
import com.gt.scr.movie.service.domain.ContentType;
import com.gt.scr.movie.service.domain.Genre;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
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

    private static final String DELETE_ALL_MOVIES = "DELETE FROM MOVIE_SCHEMA.MOVIE";
    private static final String DELETE_ALL_METADATA = "DELETE FROM MOVIE_SCHEMA.MOVIE_STREAM_METADATA";

    public static String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            preparedStatement.setString(6, movie.ageRating().name());
            preparedStatement.setString(7, movie.contentType().name());
            preparedStatement.setBoolean(8, movie.isShareable());
            preparedStatement.setString(9, movie.genre().name());
            preparedStatement.setString(10, userId.toString());
            assertThat(preparedStatement.executeUpdate()).isPositive();
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Exception adding movie to database {} with error {}", movie.id(), e.getMessage());
            }
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
                        Genre.valueOf(resultSet.getString("GENRE")),
                        ContentType.valueOf(resultSet.getString("CONTENT_TYPE")),
                        AgeRating.valueOf(resultSet.getString("AGE_RATING")),
                        resultSet.getBoolean("IS_SHAREABLE"),
                        resultSet.getLong("CREATION_TIMESTAMP")
                ));
            }

            resultSet.close();

            return Optional.empty();
        }
    }

    public static Optional<MovieStreamMetaData> getMovieStreamMetaData(UUID streamId, UUID movieId,
                                                                       DataSource dataSource, String query) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, streamId.toString());
            preparedStatement.setString(2, movieId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new MovieStreamMetaData(
                        UUID.fromString(resultSet.getString("MOVIE_ID")),
                        UUID.fromString(resultSet.getString("ID")),
                        resultSet.getString("NAME"),
                        resultSet.getLong("SEQUENCE"),
                        resultSet.getLong("SIZE_IN_BYTES"),
                        resultSet.getLong("CREATION_TIMESTAMP")));
            }

            resultSet.close();

            return Optional.empty();
        }
    }

    public static void clearDatabase(DataSource dataSource) throws SQLException {
        executeQueryUpdate(dataSource, DELETE_ALL_METADATA);
        executeQueryUpdate(dataSource, DELETE_ALL_MOVIES);
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
