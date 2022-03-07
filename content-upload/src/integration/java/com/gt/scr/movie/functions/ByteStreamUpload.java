package com.gt.scr.movie.functions;

import com.gt.scr.movie.resource.domain.ByteStreamUploadDTO;
import com.gt.scr.movie.util.TestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;
import java.util.function.BiFunction;

public class ByteStreamUpload implements BiFunction<String, WebTestClient, WebTestClient.ResponseSpec> {
    private final ByteStreamUploadDTO byteStreamUploadDTO;

    public ByteStreamUpload(UUID movieId, String streamName, byte[] byteStream) {
        byteStreamUploadDTO = new ByteStreamUploadDTO(movieId, streamName, byteStream);
    }

    @Override
    public WebTestClient.ResponseSpec apply(String token, WebTestClient webTestClient) {
        try {
            return webTestClient.post()
                    .uri("/user/movie/stream")
                    .bodyValue(TestUtils.asJsonString(byteStreamUploadDTO))
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.stream.add.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd.movie.stream.add.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
