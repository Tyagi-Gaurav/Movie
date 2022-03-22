package com.gt.scr.movie.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.movie.audit.UserEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import com.gt.scr.exception.DatabaseException;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Repository
public class EventMySQLRepository implements EventRepository {
    private static final Logger LOG = LoggerFactory.getLogger(EventMySQLRepository.class);

    @Autowired
    private DataSource dataSource;

    private static final String SCHEMA = "MOVIE_SCHEMA";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String INSERT_SQL = String.format(
            "INSERT INTO %s.EVENTS (ID, OWNER_USER, ORIGINATOR_USER, TYPE, PAYLOAD, CREATION_TIMESTAMP) VALUES (?, ?, ?, ?, ? ,?)", SCHEMA);

    @Override
    public Mono<Void> save(UserEventMessage eventMessage) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setString(1, eventMessage.eventId().toString());
            preparedStatement.setString(2, eventMessage.ownerUser().toString());
            preparedStatement.setString(3, eventMessage.originatorUser().toString());
            preparedStatement.setString(4, eventMessage.eventType().toString());
            preparedStatement.setBinaryStream(5,
                    new ByteArrayInputStream(objectMapper.writeValueAsString(eventMessage).getBytes(StandardCharsets.UTF_8)));
            preparedStatement.setLong(6, eventMessage.creationTimestamp());

            preparedStatement.execute();
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
            return Mono.error(new DatabaseException(e.getMessage(), e));
        }

        return Mono.empty();
    }
}
