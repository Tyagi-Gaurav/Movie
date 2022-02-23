package com.gt.scr.user.functions;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;

public class DeleteEvents implements Consumer<DataSource> {
    private static final String DELETE_EVENTS = "DELETE FROM MOVIE_SCHEMA.EVENTS";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void accept(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENTS)) {

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
