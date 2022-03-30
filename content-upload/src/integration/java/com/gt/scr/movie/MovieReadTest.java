package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieCreateResponseDTO;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import com.gt.scr.movie.util.MovieCreateRequestDTOBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
@AutoConfigureWebFlux
@ActiveProfiles("MovieReadTest")
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "user.host=localhost",
        "user.port=${wiremock.server.port}"
})
public class MovieReadTest {
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
        scenarioExecutor.deleteUserState();
    }

    @Test
    void shouldBeAbleToRetrieveMovieCreatedByUser() {
        MovieCreateRequestDTO movieCreateRequestDTO = MovieCreateRequestDTOBuilder.aMovieCreateRequest().build();

        scenarioExecutor.
                noEventsExistInTheSystem().then()
                .givenUserIsLoggedIn().when()
                .userCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(200)
                .thenAssertThat(movieCreateResponseDTO -> {
                    assertThat(movieCreateResponseDTO).isNotNull();
                    assertThat(movieCreateResponseDTO.movieId()).isNotNull();
                }, MovieCreateResponseDTO.class)
                .thenRetrieveMovieUsingApi()
                .thenAssertThat(allMovies -> {
                    assertThat(allMovies.movies()).isNotNull().isNotEmpty();
                    assertThat(allMovies.movies().size()).isOne();
                    List<MovieDTO> movies = allMovies.movies();
                    MovieDTO movie = movies.get(0);
                    assertThat(movie.name()).isEqualTo(movieCreateRequestDTO.name());
                    assertThat(movie.rating()).isEqualTo(movieCreateRequestDTO.rating());
                    assertThat(movie.yearProduced()).isEqualTo(movieCreateRequestDTO.yearProduced());
                    assertThat(movie.ageRating()).isEqualTo(movieCreateRequestDTO.ageRating());
                    assertThat(movie.contentType()).isEqualTo(movieCreateRequestDTO.contentType());
                    assertThat(movie.isShareable()).isEqualTo(movieCreateRequestDTO.isShareable());
                    assertThat(movie.genre()).isEqualTo(movieCreateRequestDTO.genre());
                }, MoviesDTO.class);
    }
}
