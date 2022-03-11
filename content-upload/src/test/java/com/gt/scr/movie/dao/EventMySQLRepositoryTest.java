package com.gt.scr.movie.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.exception.DatabaseException;
import com.gt.scr.movie.audit.MovieCreateEvent;
import com.gt.scr.movie.audit.UserEventMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = EventMySQLRepository.class)
@ExtendWith(MockitoExtension.class)
class EventMySQLRepositoryTest extends DatabaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(EventMySQLRepositoryTest.class);
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private DataSource dataSource;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SELECT_ALL_EVENTS = "SELECT PAYLOAD FROM MOVIE_SCHEMA.EVENTS";

    @Test
    void shouldSaveEvent() throws SQLException, IOException {
        //given
        MovieCreateEvent testMovieEvent = new MovieCreateEvent(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "testMovie", 2021
                , BigDecimal.valueOf(5));

        //when
        Mono<Void> returnMono = eventRepository.save(testMovieEvent);

        //then
        StepVerifier.create(returnMono).verifyComplete();
        Flux<UserEventMessage> eventMessageFlux = getAllEvents();
        StepVerifier.create(eventMessageFlux)
                .expectNext(testMovieEvent)
                .verifyComplete();
    }

    @Test
    void shouldCaptureErrorFromDatabase() {
        //given
        MovieCreateEvent movieWithNullMovieName = new MovieCreateEvent(null, UUID.randomUUID(),
                UUID.randomUUID(),
                "testMovie", 2021
                , BigDecimal.valueOf(5));

        //when
        Mono<Void> returnMono = eventRepository.save(movieWithNullMovieName);

        //then
        StepVerifier.create(returnMono)
                .expectError(DatabaseException.class)
                .verify();
    }

    private Flux<UserEventMessage> getAllEvents() throws SQLException, IOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENTS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserEventMessage> events = new ArrayList<>();

            while (resultSet.next()) {
                InputStream payload = resultSet.getBinaryStream("PAYLOAD");
                events.add(objectMapper.readValue(payload, UserEventMessage.class));
                resultSet.next();
            }

            resultSet.close();
            return Flux.fromIterable(events);
        } catch (JsonProcessingException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage(), e);
            }
            return Flux.error(e);
        }
    }
}