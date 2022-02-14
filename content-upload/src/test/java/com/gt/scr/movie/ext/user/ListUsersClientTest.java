package com.gt.scr.movie.ext.user;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.google.common.net.HttpHeaders;
import com.gt.scr.spc.domain.User;
import com.gt.scr.movie.util.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.codec.DecodingException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

class ListUsersClientTest {
    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
            .build();

    private ListUsersClient listUsersClient;

    @BeforeEach
    void setUp() {
        WireMockRuntimeInfo wireMockRuntimeInfo = wireMockExtension.getRuntimeInfo();
        listUsersClient = new ListUsersClient(WebClient.builder()
                .baseUrl(wireMockRuntimeInfo.getHttpBaseUrl())
                .build());
    }

    @Test
    void shouldReturnListOfUsersOnSuccess() {
        User expectedUser = UserBuilder.aUser().build();
        wireMockExtension.stubFor(get("/api/user/manage")
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.read.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.read.v1+json"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer token"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.user.read.v1+json")
                        .withBody(jsonResponse(new UserListResponseDTO(
                                List.of(
                                        new UserDetailsResponseDTO(expectedUser.username(), expectedUser.password(),
                                                expectedUser.firstName(),
                                                expectedUser.lastName(), expectedUser.getRole(),
                                                expectedUser.id())
                                )
                        )).getBody())));

        Mono<UserListResponseDTO> account = listUsersClient.listAllUsers("token");

        StepVerifier.create(account)
                .expectNext(new UserListResponseDTO(List.of(
                        new UserDetailsResponseDTO(expectedUser.username(),
                                expectedUser.password(),
                                expectedUser.firstName(),
                                expectedUser.lastName(), expectedUser.getRole(),
                                expectedUser.id())
                )))
                .verifyComplete();
    }

    @ParameterizedTest
    @ValueSource(ints = {403, 500})
    void shouldReturnExceptionWhenAccountCreationFails(int statusCode) {
        wireMockExtension.stubFor(get("/api/user/manage")
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.read.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.read.v1+json"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer token"))
                .willReturn(aResponse().withStatus(statusCode).withBody("")));

        Mono<UserListResponseDTO> response = listUsersClient.listAllUsers("token");

        StepVerifier.create(response)
                .consumeErrorWith(throwable -> {
                    assertThat(throwable).isInstanceOf(WebClientResponseException.class);
                    WebClientResponseException webClientResponseException = (WebClientResponseException) throwable;
                    assertThat(webClientResponseException.getRawStatusCode()).isEqualTo(statusCode);
                })
                .verify();
    }

    @ParameterizedTest
    @ValueSource(strings = {"{", "{\"ddd"})
    void onJsonExceptionShouldThrowRuntimeException(String malformedResponse)  {
        wireMockExtension.stubFor(get("/api/user/manage")
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.read.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.read.v1+json"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer token"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.user.read.v1+json")
                        .withBody(malformedResponse)));

        Mono<UserListResponseDTO> response = listUsersClient.listAllUsers("token");

        StepVerifier.create(response)
                .consumeErrorWith(throwable -> {
                    assertThat(throwable).isInstanceOf(DecodingException.class);
                    assertThat(throwable.getCause()).isInstanceOf(JsonEOFException.class);
                })
                .verify();
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void onNullOrEmptyShouldReturnEmptyList(String nullOrEmpty)  {
        wireMockExtension.stubFor(get("/api/user/manage")
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.read.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.read.v1+json"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer token"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.user.read.v1+json")
                        .withBody(nullOrEmpty)));

        Mono<UserListResponseDTO> response = listUsersClient.listAllUsers("token");

        StepVerifier.create(response).verifyComplete();
    }
}