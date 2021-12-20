package com.gt.scr.movie;

import com.gt.scr.movie.audit.EventType;
import com.gt.scr.movie.audit.MovieCreateEvent;
import com.gt.scr.movie.audit.UserEventMessage;
import com.gt.scr.user.functions.RetrieveAllUserMoviesForAnotherUser;
import com.gt.scr.user.functions.RetrieveEventsForUser;
import com.gt.scr.user.functions.UserManagementCreateUser;
import com.gt.scr.user.functions.UserManagementDeleteUser;
import com.gt.scr.user.functions.Login;
import com.gt.scr.user.functions.MovieCreate;
import com.gt.scr.user.functions.RetrieveAllUserMovies;
import com.gt.scr.user.functions.UserCreate;
import com.gt.scr.user.functions.UserManagementReadUsers;
import com.gt.scr.user.functions.VerifyNoMoviesAvailableForUser;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieDTO;
import com.gt.scr.movie.resource.domain.MoviesDTO;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioExecutor {
    private WebTestClient.ResponseSpec responseSpec;
    private LoginResponseDTO userLoginResponseDTO;
    private LoginResponseDTO adminLoginResponseDTO;
    private UUID lastUserIdRecorded;

    private final WebTestClient webTestClient;
    private final DataSource dataSource;

    public ScenarioExecutor(WebTestClient webTestClient,
                            DataSource dataSource) {
        this.webTestClient = webTestClient;
        this.dataSource = dataSource;
    }

    public ScenarioExecutor when() {
        return this;
    }

    public ScenarioExecutor then() {
        return this;
    }

    public ScenarioExecutor userIsCreatedFor(AccountCreateRequestDTO accountCreateRequestDTO) {
        this.responseSpec = new UserCreate().apply(webTestClient, accountCreateRequestDTO);
        return this;
    }

    public ScenarioExecutor expectReturnCode(int expectedStatus) {
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
        this.responseSpec = new UserManagementDeleteUser().apply(webTestClient, adminLoginResponseDTO,
                userLoginResponseDTO.id().toString());
        return this;
    }

    public ScenarioExecutor verifyNoMoviesExistForTheNormalUserInDatabase() {
        new VerifyNoMoviesAvailableForUser().accept(dataSource, userLoginResponseDTO);
        return this;
    }

    public ScenarioExecutor userRetrievesListOfAllUsers() {
        this.responseSpec = new UserManagementReadUsers().apply(webTestClient, userLoginResponseDTO);
        return this;
    }

    public ScenarioExecutor globalAdminUserCreatesUserWith(AccountCreateRequestDTO adminAccountCreateRequestDTO) {
        this.responseSpec = new UserManagementCreateUser().apply(webTestClient, adminLoginResponseDTO, adminAccountCreateRequestDTO);
        return this;
    }

    public ScenarioExecutor globalAdminUserLogins() {
        return adminUserLoginsWith(new LoginRequestDTO("admin", "admin123"));
    }

    public ScenarioExecutor recordCurrentUsersId() {
        this.lastUserIdRecorded = userLoginResponseDTO.id();
        return this;
    }

    public ScenarioExecutor userRetrievesAllMoviesForLastRecordedUserId() {
        this.responseSpec = new RetrieveAllUserMoviesForAnotherUser().apply(webTestClient, userLoginResponseDTO, lastUserIdRecorded);
        return this;
    }

    public ScenarioExecutor adminUserRetrievesAllMoviesForLastRecordedUserId() {
        this.responseSpec = new RetrieveAllUserMoviesForAnotherUser().apply(webTestClient, adminLoginResponseDTO, lastUserIdRecorded);
        return this;
    }

    public ScenarioExecutor movieCreateEventShouldBePublished(MovieCreateRequestDTO movieCreateRequestDTO) {
        List<UserEventMessage> events = new RetrieveEventsForUser().apply(dataSource, userLoginResponseDTO);
        assertThat(events).isNotEmpty();
        assertThat(events.size()).isEqualTo(1);
        UserEventMessage userEventMessage = events.get(0);

        assertThat(userEventMessage.eventType()).isEqualTo(EventType.MOVIE_CREATE);
        assertThat(userEventMessage).isInstanceOf(MovieCreateEvent.class);
        MovieCreateEvent movieCreateEvent = (MovieCreateEvent) userEventMessage;
        assertThat(movieCreateEvent.originatorUser()).isEqualTo(userLoginResponseDTO.id());
        assertThat(movieCreateEvent.name()).isEqualTo(movieCreateRequestDTO.name());
        return this;
    }
}
