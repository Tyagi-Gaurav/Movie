package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.util.Collections;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
@AutoConfigureWebFlux
@ActiveProfiles("MovieJourneysTest")
public class MovieJourneysTest {
    private ScenarioExecutor scenarioExecutor;
    private WebTestClient webTestClient;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + serverPort + "/api";
        webTestClient = WebTestClient.bindToServer().baseUrl(baseUrl).build();
        scenarioExecutor = new ScenarioExecutor(webTestClient, dataSource);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "abc", "abcde", "efuusidhfauihsdfuhiusdhfaiuhsfiuhiufhs"})
    public void createMovieWithInvalidData(String movieName) {
        AccountCreateRequestDTO userAccountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO userLoginRequestDTO =
                TestObjectBuilder.loginRequestUsing(userAccountCreateRequestDTO);

        MovieCreateRequestDTO movieCreateRequestDTO = TestObjectBuilder.invalidMovieCreateRequestDTO(movieName);

        scenarioExecutor
                .when().userIsCreatedWith(userAccountCreateRequestDTO)
                .and().userLoginsWith(userLoginRequestDTO)
                .and().userCreatesAMovieWith(movieCreateRequestDTO)
                .then().statusIs(400);
    }

    @Test
    void deletingUserShouldDeleteUserMovies() {
        AccountCreateRequestDTO adminAccountCreateRequestDTO =
                TestObjectBuilder.adminAccountCreateRequest();
        LoginRequestDTO adminLoginRequestDTO =
                TestObjectBuilder.loginRequestUsing(adminAccountCreateRequestDTO);
        AccountCreateRequestDTO userAccountCreateRequestDTO =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO userLoginRequestDTO =
                TestObjectBuilder.loginRequestUsing(userAccountCreateRequestDTO);

        MovieCreateRequestDTO movieCreateRequestDTO =
                TestObjectBuilder.movieCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedWith(adminAccountCreateRequestDTO)
                .and().statusIs(204)
                .and().adminUserLoginsWith(adminLoginRequestDTO)
                .and().statusIs(200)
                .when().userIsCreatedWith(userAccountCreateRequestDTO)
                .and().statusIs(204)
                .and().userLoginsWith(userLoginRequestDTO)
                .and().statusIs(200)
                .and().userCreatesAMovieWith(movieCreateRequestDTO)
                .and().statusIs(204)
                .when().userRetrievesAllMovies()
                .and().statusIs(200)
                .then()
                .theResponseShouldHaveFollowingMoviesInAnyOrder(
                        Collections.singletonList(movieCreateRequestDTO.name()))
                .and().adminUserDeletesTheUser()
                .and().statusIs(200)
                .then().verifyNoMoviesExistForTheNormalUserInDatabase();
    }
}
