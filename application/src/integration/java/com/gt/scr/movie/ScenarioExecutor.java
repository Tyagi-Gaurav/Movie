package com.gt.scr.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.gt.scr.movie.util.TestUtils;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class ScenarioExecutor {
    private MvcResult mvcResult;
    private LoginResponseDTO userLoginResponseDTO;
    private LoginResponseDTO adminLoginResponseDTO;

    private final MockMvc mockMvc;
    private final DataSource dataSource;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ScenarioExecutor(MockMvc mockMvc,
                            DataSource dataSource) {
        this.mockMvc = mockMvc;
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
        this.mvcResult = new UserCreate().apply(mockMvc, accountCreateRequestDTO);
        return this;
    }

    public ScenarioExecutor statusIs(int expectedStatus) {
        assertThat(mvcResult.getResponse()).isNotNull();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(expectedStatus);
        return this;
    }

    public ScenarioExecutor adminUserLoginsWith(LoginRequestDTO loginRequestDTO) {
        this.mvcResult = new Login().apply(mockMvc, loginRequestDTO);
        MockHttpServletResponse response = mvcResult.getResponse();
        try {
            this.adminLoginResponseDTO =
                    objectMapper.readValue(response.getContentAsString(), LoginResponseDTO.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ScenarioExecutor userLoginsWith(LoginRequestDTO loginRequestDTO) {
        this.mvcResult = new Login().apply(mockMvc, loginRequestDTO);
        MockHttpServletResponse response = mvcResult.getResponse();
        try {
            this.userLoginResponseDTO =
                    objectMapper.readValue(response.getContentAsString(), LoginResponseDTO.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ScenarioExecutor userCreatesAMovieWith(MovieCreateRequestDTO movieCreateRequestDTO) {
        this.mvcResult = new MovieCreate().apply(mockMvc, userLoginResponseDTO, movieCreateRequestDTO);
        return this;
    }

    public ScenarioExecutor userRetrievesAllMovies() {
        this.mvcResult = new RetrieveAllUserMovies().apply(mockMvc, userLoginResponseDTO);
        return this;
    }

    public ScenarioExecutor theResponseShouldHaveFollowingMoviesInAnyOrder(
            List<String> expectedMovies) {
        try {
            MoviesDTO moviesDTO =
                    TestUtils.readFromString(mvcResult.getResponse().getContentAsString(),
                            MoviesDTO.class);
            assertThat(moviesDTO.movies().stream().map(MovieDTO::name))
                    .containsExactlyInAnyOrderElementsOf(expectedMovies);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public ScenarioExecutor adminUserDeletesTheUser() {
        this.mvcResult = new AdminUserDelete()
                .apply(mockMvc, adminLoginResponseDTO,
                        userLoginResponseDTO.id().toString());
        return this;
    }

    public ScenarioExecutor verifyNoMoviesExistForTheNormalUserInDatabase() {
        new VerifyNoMoviesAvailableForUser().accept(dataSource, userLoginResponseDTO);
        return this;
    }
}
