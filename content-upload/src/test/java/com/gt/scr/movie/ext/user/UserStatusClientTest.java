package com.gt.scr.movie.ext.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

class UserStatusClientTest {
    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicHttpsPort())
            .build();

    private UserStatusClient userStatusClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        WireMockRuntimeInfo wireMockRuntimeInfo = wireMockExtension.getRuntimeInfo();
        userStatusClient = new UserStatusClient(WebClient.builder()
                .baseUrl(wireMockRuntimeInfo.getHttpBaseUrl())
                .build());
    }

    @ParameterizedTest
    @ValueSource(strings = {"UP", "DOWN"})
    void shouldReturnAppropriateStatusBasedOnAppStatus(String status) throws JsonProcessingException {
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO(status);
        String expectedBody = objectMapper.writeValueAsString(statusResponseDTO);

        wireMockExtension.stubFor(get("/api/status")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(expectedBody)));

        Mono<StatusResponseDTO> statusResponse = userStatusClient.status();

        StepVerifier.create(statusResponse)
                .expectNext(new StatusResponseDTO(status))
                .verifyComplete();
    }
}