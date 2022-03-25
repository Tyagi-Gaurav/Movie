package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.ByteStreamUploadResponseDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieCreateResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
@AutoConfigureWebFlux
@ActiveProfiles("ContentUploadTest")
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "user.host=localhost",
        "user.port=${wiremock.server.port}"
})
public class ContentUploadTest {
    private ScenarioExecutor scenarioExecutor;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + serverPort + "/api";
        WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl(baseUrl).build();
        scenarioExecutor = new ScenarioExecutor(webTestClient, dataSource);
    }

    @Test
    void shouldBeAbleToUploadByteStreamToAnExistingMovie() {
        MovieCreateRequestDTO movieCreateRequestDTO = MovieCreateRequestDTOBuilder.aMovieCreateRequest().build();

        MovieCreateResponseDTO movieCreateResponseDTO = scenarioExecutor.
                noEventsExistInTheSystem().then()
                .givenUserIsLoggedIn().when()
                .userCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(200)
                .returnMovieCreateResponse();

        byte[] byteStream = {0, 1, 1, 1};
        String streamName = "Test Stream";

        scenarioExecutor.userUploadsAByteStreamForTheMovie(
                        movieCreateResponseDTO.movieId(),
                        streamName,
                        byteStream)
                .expectReturnCode(200)
                .thenAssertThat(byteStreamResponseDTO -> {
                    assertThat(byteStreamResponseDTO).isNotNull();
                    assertThat(byteStreamResponseDTO.size()).isEqualTo(byteStream.length);
                    assertThat(byteStreamResponseDTO.streamId()).isNotNull();
                    assertThat(byteStreamResponseDTO.streamName()).isEqualTo(streamName);
                    assertThat(byteStreamResponseDTO.sequence()).isEqualTo(1);
                }, ByteStreamUploadResponseDTO.class);
    }

    @Test
    void shouldHandleErrorWhenUploadingByteStreamForANullMovie() {
        byte[] byteStream = {0, 1, 1, 1};
        String streamName = "Test Stream";

        scenarioExecutor
                .noEventsExistInTheSystem()
                .givenUserIsLoggedIn().when()
                .userUploadsAByteStreamForTheMovie(
                        null,
                        streamName,
                        byteStream)
                .expectReturnCode(400);
    }

    @ParameterizedTest
    @EmptySource
    void shouldHandleErrorWhenUploadingANullOrEmptyByteStreamForAMovie(byte[] byteStream) {
        MovieCreateRequestDTO movieCreateRequestDTO = MovieCreateRequestDTOBuilder.aMovieCreateRequest().build();
        MovieCreateResponseDTO movieCreateResponseDTO = scenarioExecutor.
                noEventsExistInTheSystem().then()
                .givenUserIsLoggedIn().when()
                .userCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(200)
                .returnMovieCreateResponse();

        String streamName = "Test Stream";

        scenarioExecutor
                .userUploadsAByteStreamForTheMovie(
                        movieCreateResponseDTO.movieId(),
                        streamName,
                        byteStream)
                .expectReturnCode(400);
    }

    @Test
    void shouldHandleErrorWhenUploadingByteStreamForANonExistentMovie() {
        byte[] byteStream = {0, 1, 1, 1};
        String streamName = "Test Stream";

        scenarioExecutor
                .noEventsExistInTheSystem()
                .givenUserIsLoggedIn().when()
                .userUploadsAByteStreamForTheMovie(
                        UUID.randomUUID(),
                        streamName,
                        byteStream)
                .expectReturnCode(500);
    }


//    @Test
//    void uploadingMultipleByteStreamsToTheSameMovieShouldBeCreatedInSequence() {
//
//    }


//    @Test
//    void deletingAMovieShouldAlsoDeleteItsMetaData() {
//
//    }


//    @Test
//    void contentUploadEventShouldBeSentWhenAMovieContentDataIsUploaded() {
//
//    }
}
