package com.gt.scr.movie.functions;

import com.gt.scr.movie.service.domain.AgeRating;
import com.gt.scr.movie.service.domain.ContentType;
import com.gt.scr.movie.service.domain.Genre;
import com.gt.scr.movie.service.domain.Movie;

import javax.sql.DataSource;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.BiFunction;

public class RetrieveMovieFromDatabase implements BiFunction<UUID, DataSource, Movie> {
    private static final String RETRIEVE_MOVIE = "SELECT * FROM MOVIE_SCHEMA.MOVIE WHERE ID = ?";

    @Override
    public Movie apply(UUID movieId, DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(RETRIEVE_MOVIE)) {

            preparedStatement.setString(1, movieId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Movie(UUID.fromString(resultSet.getString("ID")),
                        resultSet.getString("NAME"),
                        resultSet.getInt("YEAR_PRODUCED"),
                        resultSet.getBigDecimal("RATING").setScale(1, RoundingMode.UNNECESSARY),
                        Genre.valueOf(resultSet.getString("GENRE")),
                        ContentType.valueOf(resultSet.getString("CONTENT_TYPE")),
                        AgeRating.valueOf(resultSet.getString("AGE_RATING")),
                        resultSet.getBoolean("IS_SHAREABLE"),
                        resultSet.getLong("CREATION_TIMESTAMP"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
