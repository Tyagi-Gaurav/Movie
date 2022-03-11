package com.gt.scr.movie.dao;

import com.gt.scr.exception.DatabaseException;
import com.gt.scr.movie.service.domain.Movie;
import com.gt.scr.movie.service.domain.MovieStreamMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static com.gt.scr.movie.util.MovieBuilder.aMovie;
import static com.gt.scr.movie.util.MovieStreamMetaDataBuilder.aMovieStreamMetaDataBuilder;
import static com.gt.scr.movie.util.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = StreamMetaDataRepositoryImpl.class)
@ExtendWith(MockitoExtension.class)
class StreamMetaDataRepositoryTest extends DatabaseTest {

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
        Mono<Void> response = streamMetaDataRepository.store(expectedMovieStreamMetaData);

        //then
        StepVerifier.create(response)
                .verifyComplete();
        Optional<MovieStreamMetaData> movieStreamMetaData = getMovieStreamMetaData(expectedMovieStreamMetaData.streamId(),
                expectedMovieA.id(), dataSource, SELECT_MOVIE_STREAM);
        assertThat(movieStreamMetaData).isNotEmpty().hasValue(expectedMovieStreamMetaData);
    }

    @Test
    void shouldHandleException() {
        //given
        MovieStreamMetaData expectedMovieStreamMetaData = aMovieStreamMetaDataBuilder()
                .build();

        //when
        Mono<Void> response = streamMetaDataRepository.store(expectedMovieStreamMetaData);

        //then
        StepVerifier.create(response)
                .consumeErrorWith(throwable -> {
                    assertThat(throwable).isInstanceOf(DatabaseException.class);
                })
                .verify();
    }
}
