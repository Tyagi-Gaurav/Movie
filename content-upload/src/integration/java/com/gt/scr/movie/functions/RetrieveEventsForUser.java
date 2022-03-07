package com.gt.scr.movie.functions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.movie.audit.UserEventMessage;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class RetrieveEventsForUser implements BiFunction<DataSource, String, List<UserEventMessage>> {
    private static final String GET_EVENTS_FOR_USER = "SELECT PAYLOAD FROM MOVIE_SCHEMA.EVENTS WHERE OWNER_USER = ?";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<UserEventMessage> apply(DataSource dataSource, String userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_EVENTS_FOR_USER)) {

            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserEventMessage> events = new ArrayList<>();

            while (resultSet.next()) {
                InputStream payload = resultSet.getBinaryStream("PAYLOAD");
                events.add(objectMapper.readValue(payload, UserEventMessage.class));
                resultSet.next();
            }

            resultSet.close();
            return events;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
