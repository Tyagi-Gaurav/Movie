package com.gt.scr.movie.functions;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.function.BiConsumer;

public class MovieDelete implements BiConsumer<DataSource, UUID> {
    private static final String DELETE_MOVIE = "DELETE FROM MOVIE_SCHEMA.MOVIE WHERE USER_ID = ? ";

    @Override
    public void accept(DataSource dataSource, UUID userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MOVIE)) {

            preparedStatement.setString(1, userId.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
