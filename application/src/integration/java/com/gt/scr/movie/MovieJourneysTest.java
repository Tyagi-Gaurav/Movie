package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.util.Collections;

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

    @Disabled
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
                .when()
                .userIsCreatedFor(userAccountCreateRequestDTO)
                .userLoginsWith(userLoginRequestDTO)
                .userCreatesAMovieWith(movieCreateRequestDTO)
                .then().expectReturnCode(400);
    }

    @Disabled
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
                .when()
                .globalAdminUserLogins().expectReturnCode(200)
                .globalAdminUserCreatesUserWith(adminAccountCreateRequestDTO).expectReturnCode(204)
                .adminUserLoginsWith(adminLoginRequestDTO).expectReturnCode(200)
                .userIsCreatedFor(userAccountCreateRequestDTO).expectReturnCode(204)
                .userLoginsWith(userLoginRequestDTO).expectReturnCode(200)
                .userCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(204)
                .userRetrievesAllMovies().expectReturnCode(200)
                .theResponseShouldHaveFollowingMoviesInAnyOrder(
                        Collections.singletonList(movieCreateRequestDTO.name()))
                .then().adminUserDeletesTheUser().expectReturnCode(200)
                .verifyNoMoviesExistForTheNormalUserInDatabase();
    }

    @Disabled
    void userShouldOnlyBeAbleToSeeSelfCreatedMovies() {
        AccountCreateRequestDTO userA =
                TestObjectBuilder.userAccountCreateRequestDTO();
        AccountCreateRequestDTO userB =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.loginRequestUsing(userA);
        MovieCreateRequestDTO movieCreateRequestDTO = TestObjectBuilder.movieCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedFor(userA).expectReturnCode(204)
                .userLoginsWith(loginRequestDTO).expectReturnCode(200)
                .recordCurrentUsersId()
                .userCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(204)
                .userIsCreatedFor(userB).expectReturnCode(204)
                .userLoginsWith(loginRequestDTO).expectReturnCode(200)
                .userRetrievesAllMoviesForLastRecordedUserId().expectReturnCode(403);
    }

    @Disabled
    void adminUserShouldBeAbleToSeeMoviesCreatedByOtherUsers() {
        AccountCreateRequestDTO userA =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.loginRequestUsing(userA);
        MovieCreateRequestDTO movieCreateRequestDTO = TestObjectBuilder.movieCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedFor(userA).expectReturnCode(204)
                .userLoginsWith(loginRequestDTO).expectReturnCode(200)
                .recordCurrentUsersId()
                .userCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(204)
                .globalAdminUserLogins().expectReturnCode(200)
                .adminUserRetrievesAllMoviesForLastRecordedUserId().expectReturnCode(200);
    }

    @Disabled
    void creatingMoviesShouldSendMovieCreateEvent() {
        AccountCreateRequestDTO userA =
                TestObjectBuilder.userAccountCreateRequestDTO();
        LoginRequestDTO loginRequestDTO = TestObjectBuilder.loginRequestUsing(userA);
        MovieCreateRequestDTO movieCreateRequestDTO = TestObjectBuilder.movieCreateRequestDTO();

        scenarioExecutor
                .when().userIsCreatedFor(userA).expectReturnCode(204)
                .userLoginsWith(loginRequestDTO).expectReturnCode(200)
                .userCreatesAMovieWith(movieCreateRequestDTO).expectReturnCode(204)
                .movieCreateEventShouldBePublished(movieCreateRequestDTO);
    }
}
