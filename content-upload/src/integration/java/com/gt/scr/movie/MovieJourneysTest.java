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
import org.springframework.context.ConfigurableApplicationContext;
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
@ActiveProfiles("MovieJourneysTest")
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "user.host=localhost",
        "user.port=${wiremock.server.port}"
})
public class MovieJourneysTest {
    private ScenarioExecutor scenarioExecutor;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + serverPort + "/api";
        WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl(baseUrl).build();
        scenarioExecutor = new ScenarioExecutor(webTestClient, dataSource);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "abc", "abcde", "efuusidhfauihsdfuhiusdhfaiuhsfiuhiufhs"})
    public void createMovieWithInvalidData(String movieName) {
        MovieCreateRequestDTO movieCreateRequestDTO = TestObjectBuilder.invalidMovieCreateRequestDTO(movieName);
        scenarioExecutor
                .givenUserIsLoggedIn().when()
                .userCreatesAMovieWith(movieCreateRequestDTO)
                .then().expectReturnCode(400);
    }

    @Test
    void creatingMoviesShouldSendMovieCreateEvent() {
        MovieCreateRequestDTO movieCreateRequestDTO = TestObjectBuilder.movieCreateRequestDTO();

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
        MovieCreateRequestDTO movieCreateRequestDTO = TestObjectBuilder.movieCreateRequestDTO();

        scenarioExecutor
                .noEventsExistInTheSystem().then()
                .givenAdminUserIsLoggedIn().when()
                .adminUserCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(200)
                .thenAssertThat(movieCreateResponseDTO -> {
                    assertThat(movieCreateResponseDTO).isNotNull();
                    assertThat(movieCreateResponseDTO.movieId()).isNotNull();
                }, MovieCreateResponseDTO.class)
                .movieCreateEventShouldBePublishedForAdminUser(movieCreateRequestDTO);
    }
}
