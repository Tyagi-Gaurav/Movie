package com.gt.scr.movie.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.movie.audit.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Repository
public class EventMySQLRepository implements EventRepository {
    private static final Logger LOG = LoggerFactory.getLogger(EventMySQLRepository.class);

    @Autowired
    private DataSource dataSource;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> save(EventMessage eventMessage) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO EVENTS (ID, TYPE, PAYLOAD, CREATION_TIMESTAMP) VALUES (?, ?, ? ,?)")) {

            preparedStatement.setString(1, eventMessage.eventId().toString());
            preparedStatement.setString(2, eventMessage.eventType().toString());
            preparedStatement.setBinaryStream(3, new ByteArrayInputStream(objectMapper.writeValueAsString(eventMessage).getBytes()));
            preparedStatement.setLong(4, eventMessage.creationTimestamp());

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
