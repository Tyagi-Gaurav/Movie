package com.gt.scr.movie.ext.user;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.google.common.net.HttpHeaders;
import com.gt.scr.domain.User;
import com.gt.scr.ext.UpstreamClient;
import com.gt.scr.movie.util.UserBuilder;
import com.gt.scr.resilience.Resilience;
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

import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

class FetchUsersByNameClientTest {
    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
            .build();

    private UpstreamClient<String, UserDetailsResponseDTO> fetchUsersByNameClient;

    @BeforeEach
    void setUp() {
        WireMockRuntimeInfo wireMockRuntimeInfo = wireMockExtension.getRuntimeInfo();
        fetchUsersByNameClient = new FetchUsersByNameClient(WebClient.builder()
                .baseUrl(wireMockRuntimeInfo.getHttpBaseUrl())
                .build());
    }

    @Test
    void shouldHaveResilienceAnnotationForExternalCalls() throws NoSuchMethodException {
        Resilience annotation = FetchUsersByNameClient.class.getMethod("execute", String.class)
                .getAnnotation(Resilience.class);

        assertThat(annotation).isNotNull();
        assertThat(annotation.value()).isEqualTo("user");
    }

    @Test
    void shouldReturnUserDetailsOnSuccess() {
        User expectedUser = UserBuilder.aUser().build();
        wireMockExtension.stubFor(get(String.format("/api/user?userName=%s", expectedUser.username()))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.fetchByUserName.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.fetchByUserName.v1+json"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.user.fetchByUserName.v1+json")
                        .withBody(jsonResponse(new UserDetailsResponseDTO(expectedUser.username(),
                                expectedUser.password(),
                                expectedUser.firstName(),
                                expectedUser.lastName(), expectedUser.getRole(),
                                expectedUser.id()))
                                .getBody())));

        Mono<UserDetailsResponseDTO> account = fetchUsersByNameClient.execute(expectedUser.username());

        StepVerifier.create(account)
                .expectNext(
                        new UserDetailsResponseDTO(expectedUser.username(),
                                expectedUser.password(),
                                expectedUser.firstName(),
                                expectedUser.lastName(), expectedUser.getRole(),
                                expectedUser.id())
                )
                .verifyComplete();
    }

    @ParameterizedTest
    @ValueSource(ints = {403, 500})
    void shouldReturnExceptionWhenAccountCreationFails(int statusCode) {
        wireMockExtension.stubFor(get("/api/user?userName=abc")
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.fetchByUserName.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.fetchByUserName.v1+json"))
                .willReturn(aResponse().withStatus(statusCode).withBody("")));

        Mono<UserDetailsResponseDTO> response = fetchUsersByNameClient.execute("abc");

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
    void onJsonExceptionShouldThrowRuntimeException(String malformedResponse) {
        wireMockExtension.stubFor(get("/api/user?userName=abc")
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.fetchByUserName.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.fetchByUserName.v1+json"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.user.fetchByUserName.v1+json")
                        .withBody(malformedResponse)));

        Mono<UserDetailsResponseDTO> response = fetchUsersByNameClient.execute("abc");

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
    void onNullOrEmptyShouldReturnEmptyList(String nullOrEmpty) {
        wireMockExtension.stubFor(get("/api/user?userName=abc")
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.fetchByUserName.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.fetchByUserName.v1+json"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.user.fetchByUserName.v1+json")
                        .withBody(nullOrEmpty)));

        Mono<UserDetailsResponseDTO> response = fetchUsersByNameClient.execute("abc");

        StepVerifier.create(response).verifyComplete();
    }
}