package com.gt.scr.movie.ext.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.google.common.net.HttpHeaders;
import com.gt.scr.exception.DuplicateRecordException;
import com.gt.scr.movie.exception.UnexpectedSystemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserClientTest {
    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
            .build();

    private final ObjectMapper objectMapper = Mockito.spy(ObjectMapper.class);
    private CreateUserClient accountClient;
    private final UserCreateRequestDTO userCreateRequestDTO =
            new UserCreateRequestDTO("testUserName", "testPassword", "testFirstName",
                    "testLastName", "ADMIN");

    @BeforeEach
    void setUp() throws JsonProcessingException {
        WireMockRuntimeInfo wireMockRuntimeInfo = wireMockExtension.getRuntimeInfo();
        accountClient = new CreateUserClient(WebClient.builder()
                .baseUrl(wireMockRuntimeInfo.getHttpBaseUrl())
                .build(),
                objectMapper);
        when(objectMapper.writeValueAsString(any())).thenCallRealMethod();
    }

    @Test
    void shouldReturnSuccessWhenAccountCreationSucceeds() throws JsonProcessingException {
        wireMockExtension.stubFor(post("/api/user/account/create")
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd+account.create.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd+account.create.v1+json"))
                .withRequestBody(equalTo(objectMapper.writeValueAsString(userCreateRequestDTO)))
                .willReturn(ok()));

        Mono<Void> account = accountClient.createUser(userCreateRequestDTO);

        StepVerifier.create(account)
                .verifyComplete();
    }

    @ParameterizedTest
    @MethodSource(value = "errorArguments")
    void shouldReturnExceptionWhenAccountCreationFails(ResponseDefinitionBuilder errorResponse, int statusCode) throws JsonProcessingException {
        wireMockExtension.stubFor(post("/api/user/account/create")
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd+account.create.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd+account.create.v1+json"))
                .withRequestBody(equalTo(objectMapper.writeValueAsString(userCreateRequestDTO)))
                .willReturn(errorResponse.withStatus(statusCode)));

        Mono<Void> account = accountClient.createUser(userCreateRequestDTO);

        StepVerifier.create(account)
                .consumeErrorWith(throwable -> {
                    assertThat(throwable).isInstanceOf(WebClientResponseException.class);
                    WebClientResponseException webClientResponseException = (WebClientResponseException) throwable;
                    assertThat(webClientResponseException.getRawStatusCode()).isEqualTo(statusCode);
                })
                .verify();
    }

    @Test
    void shouldReturnForbiddenExceptionWhenAccountCreationFailsForDuplicate() throws JsonProcessingException {
        wireMockExtension.stubFor(post("/api/user/account/create")
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd+account.create.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd+account.create.v1+json"))
                .withRequestBody(equalTo(objectMapper.writeValueAsString(userCreateRequestDTO)))
                .willReturn(forbidden()));

        Mono<Void> account = accountClient.createUser(userCreateRequestDTO);

        StepVerifier.create(account)
                .expectError(DuplicateRecordException.class)
                .verify();
    }

    @Test
    void onJsonExceptionShouldThrowRuntimeException() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(RuntimeException.class);

        Mono<Void> account = accountClient.createUser(userCreateRequestDTO);

        StepVerifier.create(account)
                .consumeErrorWith(throwable -> {
                    assertThat(throwable).isInstanceOf(UnexpectedSystemException.class);
                    assertThat(throwable.getCause()).isInstanceOf(RuntimeException.class);
                })
                .verify();
    }

    public static Stream<Arguments> errorArguments() {
        return Stream.of(
                Arguments.of(badRequest(), 400),
                Arguments.of(serverError(), 500),
                Arguments.of(serviceUnavailable(), 503)
        );
    }
}