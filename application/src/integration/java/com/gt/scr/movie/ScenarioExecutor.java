package com.gt.scr.movie;

import com.gt.scr.movie.functions.AdminUserDelete;
import com.gt.scr.movie.functions.Login;
import com.gt.scr.movie.functions.MovieCreate;
import com.gt.scr.movie.functions.RetrieveAllUserMovies;
import com.gt.scr.movie.functions.UserCreate;
import com.gt.scr.movie.functions.VerifyNoMoviesAvailableForUser;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioExecutor {
    private MvcResult mvcResult;
    private WebTestClient.ResponseSpec responseSpec;
    private LoginResponseDTO userLoginResponseDTO;
    private LoginResponseDTO adminLoginResponseDTO;

    private final WebTestClient webTestClient;
    private final MockMvc mockMvc = null;
    private final DataSource dataSource;

    public ScenarioExecutor(WebTestClient webTestClient,
                            DataSource dataSource) {
        this.webTestClient = webTestClient;
        this.dataSource = dataSource;
    }

    public ScenarioExecutor when() {
        return this;
    }

    public ScenarioExecutor and() {
        return this;
    }

    public ScenarioExecutor then() {
        return this;
    }

    public ScenarioExecutor userIsCreatedWith(AccountCreateRequestDTO accountCreateRequestDTO) {
        this.responseSpec = new UserCreate().apply(webTestClient, accountCreateRequestDTO);
        return this;
    }

    public ScenarioExecutor statusIs(int expectedStatus) {
        responseSpec.expectStatus().isEqualTo(expectedStatus);
        return this;
    }

    public ScenarioExecutor adminUserLoginsWith(LoginRequestDTO loginRequestDTO) {
        this.responseSpec = new Login().apply(webTestClient, loginRequestDTO);
        this.adminLoginResponseDTO = this.responseSpec.returnResult(LoginResponseDTO.class)
                .getResponseBody().blockFirst();
        return this;
    }

    public ScenarioExecutor userLoginsWith(LoginRequestDTO loginRequestDTO) {
        this.responseSpec = new Login().apply(webTestClient, loginRequestDTO);
        this.userLoginResponseDTO = this.responseSpec.returnResult(LoginResponseDTO.class)
                .getResponseBody().blockFirst();
        return this;
    }

    public ScenarioExecutor userCreatesAMovieWith(MovieCreateRequestDTO movieCreateRequestDTO) {
        this.responseSpec = new MovieCreate().apply(webTestClient, userLoginResponseDTO, movieCreateRequestDTO);
        return this;
    }

    public ScenarioExecutor userRetrievesAllMovies() {
        this.responseSpec = new RetrieveAllUserMovies().apply(webTestClient, userLoginResponseDTO);
        return this;
    }

    public ScenarioExecutor theResponseShouldHaveFollowingMoviesInAnyOrder(List<String> expectedMovies) {
        EntityExchangeResult<MoviesDTO> moviesDTOEntityExchangeResult = responseSpec.expectBody(MoviesDTO.class).returnResult();
        MoviesDTO responseBody = moviesDTOEntityExchangeResult.getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.movies().size()).isOne();
        assertThat(responseBody.movies().stream().map(MovieDTO::name).toList())
                .hasSameElementsAs(expectedMovies);
        return this;
    }

    public ScenarioExecutor adminUserDeletesTheUser() {
        this.responseSpec = new AdminUserDelete().apply(webTestClient, adminLoginResponseDTO,
                userLoginResponseDTO.id().toString());
        return this;
    }

    public ScenarioExecutor verifyNoMoviesExistForTheNormalUserInDatabase() {
        new VerifyNoMoviesAvailableForUser().accept(dataSource, userLoginResponseDTO);
        return this;
    }
}
