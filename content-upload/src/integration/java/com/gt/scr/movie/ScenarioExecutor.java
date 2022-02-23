package com.gt.scr.movie;

import com.google.common.net.HttpHeaders;
import com.gt.scr.movie.audit.EventType;
import com.gt.scr.movie.audit.MovieCreateEvent;
import com.gt.scr.movie.audit.UserEventMessage;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.MovieCreateResponseDTO;
import com.gt.scr.user.functions.AdminMovieCreate;
import com.gt.scr.user.functions.DeleteEvents;
import com.gt.scr.user.functions.MovieCreate;
import com.gt.scr.user.functions.RetrieveEventsForUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.gt.scr.movie.TestObjectBuilder.validUserResponseDto;
import static org.assertj.core.api.Assertions.assertThat;

public class ScenarioExecutor {
    private WebTestClient.ResponseSpec responseSpec;
    private static final String NORMAL_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJBdXRob3JpdGllcyI6WyJBRE1JTiJdLCJzdWIiOiJDcmNhaWciLCJqdGkiOiI2OWJkMTdiOC01Mzc5LTQ5MmUtYjQxMC0xNzUwY2IyZjE3ZTMiLCJpYXQiOjE2NDQ5NDI5MzYsImV4cCI6MTk2MDMwMjkzNn0.uVN9etpicGwftAvIdlxkq8c0SkG7_keHAqfjbPl6YtI";
    private static final String TEST_NORMAL_USER_ID = "69bd17b8-5379-492e-b410-1750cb2f17e3";
    private static final String TEST_NORMAL_USER_NAME = "Crcaig";
    private static final String TEST_ADMIN_USER_NAME = "jyojoc";
    private static final String TEST_ADMIN_USER_ID = "e9297e3d-be94-4452-a2a7-e5d2b448a785";
    private static final String ADMIN_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJBdXRob3JpdGllcyI6WyJBRE1JTiJdLCJzdWIiOiJqeW9qb2MiLCJqdGkiOiJlOTI5N2UzZC1iZTk0LTQ0NTItYTJhNy1lNWQyYjQ0OGE3ODUiLCJpYXQiOjE2NDU1NzIwNDIsImV4cCI6MTk2MDkzMjA0Mn0.xno8T08rBazF3X8NyBiwuzw57bunup4SHI5ZsvU98B0";

    private final Map<Class, Object> responses = new HashMap<>();
    private final WebTestClient webTestClient;
    private final DataSource dataSource;

    public ScenarioExecutor(WebTestClient webTestClient, DataSource dataSource) {
        this.webTestClient = webTestClient;
        this.dataSource = dataSource;
    }

    public ScenarioExecutor when() {
        return this;
    }

    public ScenarioExecutor then() {
        return this;
    }

    public ScenarioExecutor expectReturnCode(int expectedStatus) {
        responseSpec.expectStatus().isEqualTo(expectedStatus);
        return this;
    }

    public ScenarioExecutor userCreatesAMovieWith(MovieCreateRequestDTO movieCreateRequestDTO) {
        this.responseSpec = new MovieCreate().apply(webTestClient, NORMAL_USER_TOKEN, movieCreateRequestDTO);
        this.responses.put(MovieCreateResponseDTO.class, responseSpec.returnResult(MovieCreateResponseDTO.class)
                .getResponseBody().blockFirst());
        return this;
    }

    public <T> ScenarioExecutor movieCreateEventShouldBePublishedForNormalUser(MovieCreateRequestDTO movieCreateRequestDTO) {
        assertOnEventUsing(movieCreateRequestDTO, TEST_NORMAL_USER_ID, TEST_NORMAL_USER_ID);
        return this;
    }

    public <T> ScenarioExecutor movieCreateEventShouldBePublishedForAdminUser(MovieCreateRequestDTO movieCreateRequestDTO) {
        assertOnEventUsing(movieCreateRequestDTO, TEST_NORMAL_USER_ID, TEST_ADMIN_USER_ID);
        return this;
    }

    public <T> ScenarioExecutor assertOnEventUsing(MovieCreateRequestDTO movieCreateRequestDTO,
                                                   String eventOwnerId, String eventOriginatorId) {
        List<UserEventMessage> events = new RetrieveEventsForUser().apply(dataSource, TEST_NORMAL_USER_ID);
        assertThat(events).isNotEmpty();
        assertThat(events.size()).isEqualTo(1);
        UserEventMessage userEventMessage = events.get(0);

        MovieCreateResponseDTO movieCreateResponseDTO = getResponseOfType(MovieCreateResponseDTO.class);

        assertThat(userEventMessage.eventType()).isEqualTo(EventType.MOVIE_CREATE);
        assertThat(userEventMessage).isInstanceOf(MovieCreateEvent.class);
        MovieCreateEvent movieCreateEvent = (MovieCreateEvent) userEventMessage;
        assertThat(movieCreateEvent.ownerUser().toString()).isEqualTo(eventOwnerId);
        assertThat(movieCreateEvent.originatorUser().toString()).isEqualTo(eventOriginatorId);
        assertThat(movieCreateEvent.name()).isEqualTo(movieCreateRequestDTO.name());
        assertThat(movieCreateEvent.movieId()).isEqualTo(movieCreateResponseDTO.movieId());
        return this;
    }

    public ScenarioExecutor givenUserIsLoggedIn() {
        stubFor(get(String.format("/api/user?userId=%s", TEST_NORMAL_USER_ID))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.fetchByUserId.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.fetchByUserId.v1+json"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.user.fetchByUserId.v1+json")
                        .withBody(jsonResponse(validUserResponseDto(UUID.fromString(TEST_NORMAL_USER_ID), TEST_NORMAL_USER_NAME, "USER"))
                                .getBody())));
        return this;
    }

    public ScenarioExecutor givenAdminUserIsLoggedIn() {
        stubFor(get(String.format("/api/user?userId=%s", TEST_ADMIN_USER_ID))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.fetchByUserId.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.fetchByUserId.v1+json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.user.fetchByUserId.v1+json")
                        .withBody(jsonResponse(validUserResponseDto(UUID.fromString(TEST_ADMIN_USER_ID), TEST_ADMIN_USER_NAME, "ADMIN"))
                                .getBody())));
        return this;
    }

    public <T> ScenarioExecutor thenAssertThat(Consumer<T> responseSpecConsumer, Class<T> clazz) {
        T response = getResponseOfType(clazz);
        responseSpecConsumer.accept(response);
        return this;
    }

    private <T> T getResponseOfType(Class<T> clazz) {
        T response = (T) responses.get(clazz);
        return response;
    }

    public ScenarioExecutor adminUserCreatesAMovieWith(MovieCreateRequestDTO movieCreateRequestDTO) {
        this.responseSpec = new AdminMovieCreate(TEST_NORMAL_USER_ID)
                .apply(webTestClient, ADMIN_USER_TOKEN, movieCreateRequestDTO);
        this.responses.put(MovieCreateResponseDTO.class, responseSpec.returnResult(MovieCreateResponseDTO.class)
                .getResponseBody().blockFirst());
        return this;
    }

    public ScenarioExecutor noEventsExistInTheSystem() {
        new DeleteEvents().accept(dataSource);
        return this;
    }
}
