package com.gt.scr.movie.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.movie.audit.EventMessage;
import com.gt.scr.movie.audit.MovieCreateEvent;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = EventMySQLRepository.class)
@ExtendWith(MockitoExtension.class)
@Import(EventMySQLRepositoryTest.TestMovieRepoContextConfiguration.class)
class EventMySQLRepositoryTest {
    private static final Logger LOG = LoggerFactory.getLogger(EventMySQLRepositoryTest.class);
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private DataSource dataSource;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SELECT_ALL_EVENTS = "SELECT PAYLOAD FROM EVENTS";

    @Test
    void shouldSaveEvent() throws SQLException, IOException {
        //given
        MovieCreateEvent testMovieEvent = new MovieCreateEvent("testMovie", 2021
                        , BigDecimal.valueOf(5));

        //when
        Mono<Void> returnMono = eventRepository.save(testMovieEvent);

        //then
        StepVerifier.create(returnMono).verifyComplete();
        Flux<EventMessage> eventMessageFlux = getAllEvents();
        StepVerifier.create(eventMessageFlux)
                .expectNext(testMovieEvent)
                .verifyComplete();
    }

    private Flux<EventMessage> getAllEvents() throws SQLException, IOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EVENTS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            List<EventMessage> events = new ArrayList<>();

            while (resultSet.next()) {
                InputStream payload = resultSet.getBinaryStream("PAYLOAD");
                events.add(objectMapper.readValue(payload, EventMessage.class));
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

    @TestConfiguration
    static class TestMovieRepoContextConfiguration {

        @Bean
        public DataSource inMemoryEventDataSource() {
            ComboPooledDataSource cpds = new ComboPooledDataSource();

            try {

                URL resource = EventMySQLRepositoryTest.TestMovieRepoContextConfiguration
                        .class.getClassLoader().getResource("db.changelog/dbchangelog.sql");
                assertThat(resource).describedAs("Unable to find sql file to create database").isNotNull();
                String tempFile = resource.toURI().getRawPath();
                cpds.setDriverClass("org.h2.Driver");
                String jdbcUrl =
                        String.format("jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;" +
                                "DB_CLOSE_ON_EXIT=TRUE;INIT=RUNSCRIPT FROM '%s'", tempFile);
                cpds.setJdbcUrl(jdbcUrl);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            return cpds;
        }
    }
}