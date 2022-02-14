package com.gt.scr.movie.ext.user;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.google.common.net.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

class DeleteUsersClientTest {
    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicHttpsPort().notifier(new ConsoleNotifier(true)))
            .build();

    private DeleteUsersClient deleteUsersClient;

    @BeforeEach
    void setUp() {
        WireMockRuntimeInfo wireMockRuntimeInfo = wireMockExtension.getRuntimeInfo();
        deleteUsersClient = new DeleteUsersClient(WebClient.builder()
                .baseUrl(wireMockRuntimeInfo.getHttpBaseUrl())
                .build());
    }

    @Test
    void shouldDeleteUsersOnSuccess() {
        String userId = "testUserId";
        wireMockExtension.stubFor(delete("/api/user/manage?userId=" + userId)
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.delete.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.delete.v1+json"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer token"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.user.delete.v1+json")
                        .withStatus(200)));

        Mono<Void> account = deleteUsersClient.deleteUser(userId, "token");

        StepVerifier.create(account)
                .verifyComplete();

        wireMockExtension.verify(deleteRequestedFor(urlEqualTo("/api/user/manage?userId=" + userId))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.delete.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.delete.v1+json"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer token"))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {400, 500})
    void onErrorShouldThrowRuntimeException(int statusCode) {
        String userId = "testUserId";
        wireMockExtension.stubFor(delete("/api/user/manage?userId=" + userId)
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.delete.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.delete.v1+json"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer token"))
                .willReturn(aResponse()
                        .withStatus(statusCode)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.user.delete.v1+json")));

        Mono<Void> response = deleteUsersClient.deleteUser(userId, "token");

        StepVerifier.create(response)
                .consumeErrorWith(throwable -> {
                    assertThat(throwable).isInstanceOf(WebClientResponseException.class);
                })
                .verify();

        wireMockExtension.verify(deleteRequestedFor(urlEqualTo("/api/user/manage?userId=" + userId))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo("application/vnd.user.delete.v1+json"))
                .withHeader(HttpHeaders.ACCEPT, equalTo("application/vnd.user.delete.v1+json"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("Bearer token"))
        );
    }
}