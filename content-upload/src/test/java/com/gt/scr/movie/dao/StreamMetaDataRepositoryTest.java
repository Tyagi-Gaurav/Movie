package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static com.gt.scr.movie.util.MovieBuilder.aMovie;
import static com.gt.scr.movie.util.MovieStreamMetaDataBuilder.aMovieStreamMetaDataBuilder;
import static com.gt.scr.movie.util.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = StreamMetaDataRepositoryImpl.class)
@ExtendWith(MockitoExtension.class)
@Import(StreamMetaDataRepositoryTest.TestStreamMetaDataRepoContextConfiguration.class)
public class StreamMetaDataRepositoryTest {

    @Autowired
    private StreamMetaDataRepository streamMetaDataRepository;

    @Autowired
    private DataSource dataSource;

    private static final String ADD_MOVIE =
            "INSERT INTO MOVIE_SCHEMA.MOVIE (ID, NAME, YEAR_PRODUCED, RATING, CREATION_TIMESTAMP, USER_ID) values (?, ?, ?, ?, ?, ?)";

    private static final String DELETE_ALL_METADATA = "DELETE FROM MOVIE_SCHEMA.MOVIE_STREAM_METADATA";

    private static final String SELECT_MOVIE_STREAM = "SELECT * FROM MOVIE_SCHEMA.MOVIE_STREAM_METADATA WHERE ID = ? AND MOVIE_ID = ?";

    @BeforeEach
    void setUp() throws SQLException {
        executeQueryUpdate(dataSource, DELETE_ALL_METADATA);
    }

    @Test
    void shouldCreateMovieStreamMetaData() throws SQLException {
        //given
        Movie expectedMovieA = aMovie().build();
        addToDatabase(expectedMovieA, dataSource, UUID.randomUUID(), ADD_MOVIE);
        MovieStreamMetaData expectedMovieStreamMetaData = aMovieStreamMetaDataBuilder()
                .withMovieId(expectedMovieA.id())
                .build();

        //when
        streamMetaDataRepository.store(expectedMovieStreamMetaData);

        //then
        Optional<MovieStreamMetaData> movieStreamMetaData = getMovieStreamMetaData(expectedMovieStreamMetaData.streamId(),
                expectedMovieA.id(), dataSource, SELECT_MOVIE_STREAM);
        assertThat(movieStreamMetaData).isNotEmpty().hasValue(expectedMovieStreamMetaData);
    }

    @TestConfiguration
    static class TestStreamMetaDataRepoContextConfiguration {

        @Bean
        public DataSource inMemoryMetaDataDataSource() {
            ComboPooledDataSource cpds = new ComboPooledDataSource();

            try {
                String databaseName = RandomStringUtils.randomAlphabetic(6);
                URL resource = StreamMetaDataRepositoryTest.TestStreamMetaDataRepoContextConfiguration.class.getClassLoader()
                        .getResource("db.changelog/dbchangelog.sql");
                assertThat(resource).describedAs("Unable to find sql file to create database").isNotNull();
                String tempFile = resource.toURI().getRawPath();
                cpds.setDriverClass("org.h2.Driver");
                String jdbcUrl = String.format("jdbc:h2:mem:%s;MODE=MySQL;DB_CLOSE_DELAY=-1;" +
                        "DB_CLOSE_ON_EXIT=TRUE;INIT=RUNSCRIPT FROM '%s'", databaseName, tempFile);
                cpds.setJdbcUrl(jdbcUrl);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            return cpds;
        }
    }
}
