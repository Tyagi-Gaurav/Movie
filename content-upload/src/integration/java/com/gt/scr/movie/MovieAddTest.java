package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieCreateResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
@AutoConfigureWebFlux
@ActiveProfiles("MovieAddTest")
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "user.host=localhost",
        "user.port=${wiremock.server.port}"
})
public class MovieAddTest {
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

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "abc", "abcde", "efuusidhfauihsdfuhiusdhfaiuhsfiuhiufhs"})
    void createMovieWithInvalidData(String movieName) {
        MovieCreateRequestDTO movieCreateRequestDTO = MovieCreateRequestDTOBuilder.aMovieCreateRequest()
                .withName(movieName)
                .build();

        scenarioExecutor
                .givenUserIsLoggedIn().when()
                .userCreatesAMovieWith(movieCreateRequestDTO)
                .then().expectReturnCode(400);
    }

    @Test
    void creatingMoviesShouldSaveAllFields() {
        MovieCreateRequestDTO movieCreateRequestDTO = MovieCreateRequestDTOBuilder.aMovieCreateRequest().build();

        scenarioExecutor.
                noEventsExistInTheSystem().then()
                .givenUserIsLoggedIn().when()
                .userCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(200)
                .thenAssertThat(movieCreateResponseDTO -> {
                    assertThat(movieCreateResponseDTO).isNotNull();
                    assertThat(movieCreateResponseDTO.movieId()).isNotNull();
                }, MovieCreateResponseDTO.class)
                .thenRetrieveMovieFromDatabaseAndAssert(movie -> {
                    assertThat(movie.name()).isEqualTo(movieCreateRequestDTO.name());
                    assertThat(movie.rating()).isEqualTo(movieCreateRequestDTO.rating());
                    assertThat(movie.yearProduced()).isEqualTo(movieCreateRequestDTO.yearProduced());
                    assertThat(movie.ageRating()).isEqualTo(movieCreateRequestDTO.ageRating());
                    assertThat(movie.contentType()).isEqualTo(movieCreateRequestDTO.contentType());
                    assertThat(movie.isShareable()).isEqualTo(movieCreateRequestDTO.isShareable());
                    assertThat(movie.genre()).isEqualTo(movieCreateRequestDTO.genre());
                });
    }

    @Test
    void creatingMoviesShouldSendMovieCreateEvent() {
        MovieCreateRequestDTO movieCreateRequestDTO = MovieCreateRequestDTOBuilder.aMovieCreateRequest().build();

        scenarioExecutor.
                noEventsExistInTheSystem().then()
                .givenUserIsLoggedIn().when()
                .userCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(200)
                .thenAssertThat(movieCreateResponseDTO -> {
                    assertThat(movieCreateResponseDTO).isNotNull();
                    assertThat(movieCreateResponseDTO.movieId()).isNotNull();
                }, MovieCreateResponseDTO.class)
                .movieCreateEventShouldBePublishedForNormalUser(movieCreateRequestDTO);
    }

    @Test
    void whenAdminIsCreatingMoviesForUserShouldSendMovieCreateEvent() {
        MovieCreateRequestDTO movieCreateRequestDTO = MovieCreateRequestDTOBuilder.aMovieCreateRequest().build();

        scenarioExecutor
                .noEventsExistInTheSystem().then()
                .givenAdminUserIsLoggedIn().when()
                .adminUserCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(200)
                .thenAssertThat(movieCreateResponseDTO -> {
                    assertThat(movieCreateResponseDTO).isNotNull();
                    assertThat(movieCreateResponseDTO.movieId()).isNotNull();
                }, MovieCreateResponseDTO.class)
                .thenRetrieveMovieFromDatabaseAndAssert(movie -> {
                    assertThat(movie.name()).isEqualTo(movieCreateRequestDTO.name());
                    assertThat(movie.rating()).isEqualTo(movieCreateRequestDTO.rating());
                    assertThat(movie.yearProduced()).isEqualTo(movieCreateRequestDTO.yearProduced());
                    assertThat(movie.ageRating()).isEqualTo(movieCreateRequestDTO.ageRating());
                    assertThat(movie.contentType()).isEqualTo(movieCreateRequestDTO.contentType());
                    assertThat(movie.isShareable()).isEqualTo(movieCreateRequestDTO.isShareable());
                    assertThat(movie.genre()).isEqualTo(movieCreateRequestDTO.genre());
                })
                .movieCreateEventShouldBePublishedForAdminUser(movieCreateRequestDTO);
    }
}
