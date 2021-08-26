package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer.class)
@AutoConfigureMockMvc
public class MovieJourneysTest {
    @Autowired
    private ScenarioExecutor scenarioExecutor;

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
                TestObjectBuilder.movieCreateRequestDTO().build();

        scenarioExecutor
                .when().userIsCreatedWith(adminAccountCreateRequestDTO)
                .and().statusIs(204)
                .and().adminUserLoginsWith(adminLoginRequestDTO)
                .when().userIsCreatedWith(userAccountCreateRequestDTO)
                .and().userLoginsWith(userLoginRequestDTO)
                .and().userCreatesAMovieWith(movieCreateRequestDTO)
                .when().userRetrievesAllMovies()
                .then().theResponseShouldHaveFollowingMoviesInAnyOrder(
                Collections.singletonList(movieCreateRequestDTO.name()))
                .and().adminUserDeletesTheUser()
                .then().verifyNoMoviesExistForTheNormalUserInDatabase();
    }
}
