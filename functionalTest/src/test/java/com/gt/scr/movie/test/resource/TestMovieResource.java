package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.ApiGatewayConfig;
import com.gt.scr.movie.test.domain.TestMovieCreateRequestDTO;
import com.gt.scr.movie.test.domain.TestMovieUpdateRequestDTO;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TestMovieResource extends AbstractResource {

    @Autowired
    private ApiGatewayConfig apiGatewayConfig;

    @Autowired
    private ResponseHolder responseHolder;

    @Autowired
    private ManagedChannel managedChannel;

    public void createMovieFor(TestMovieCreateRequestDTO testMovieCreateRequestDTO) {
        createUsingRest(testMovieCreateRequestDTO);
    }

    public void createUsingRest(TestMovieCreateRequestDTO movieCreateRequestDTO) {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/api/user/movie", apiGatewayConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.add.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(movieCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void createWithoutToken(TestMovieCreateRequestDTO testMovieCreateRequestDTO) {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/api/user/movie", apiGatewayConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.add.v1+json");
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(testMovieCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void readMoviesFor() {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/api/user/movie", apiGatewayConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.read.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(headers);
        responseHolder.setResponse(this.get(fullUrl, requestObject, String.class));
    }

    public void deleteMovie(UUID uuid) {
        String fullUrl = String.format("%s?id=%s", getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/api/user/movie", apiGatewayConfig.port()), uuid.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.delete.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(headers);

        responseHolder.setResponse(this.delete(fullUrl, requestObject, String.class));
    }

    public void updateMovie(TestMovieUpdateRequestDTO updateRequestDTO) {
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/api/user/movie", apiGatewayConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.update.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieUpdateRequestDTO> requestObject = new HttpEntity<>(updateRequestDTO, headers);
        responseHolder.setResponse(this.put(fullUrl, requestObject, String.class));
    }

    public void readMoviesFor(String userId) {
        String path = String.format("/api/user/%s/movie", userId);
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(), apiGatewayConfig.contentUploadContextPath(), path, apiGatewayConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.read.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(headers);
        responseHolder.setResponse(this.get(fullUrl, requestObject, String.class));
    }

    public void deleteMovie(UUID movieId, String regularUserId) {
        String fullUrl = String.format("%s?id=%s&userId=%s", getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/api/user/movie", apiGatewayConfig.port()), movieId.toString(), regularUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.delete.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(headers);

        responseHolder.setResponse(this.delete(fullUrl, requestObject, String.class));
    }

    public void createMovieFor(TestMovieCreateRequestDTO movieCreateRequestDTO, String regularUserId) {
        String path = String.format("/api/user/%s/movie", regularUserId);
        String fullUrl = getFullUrl(apiGatewayConfig.host().trim(), apiGatewayConfig.contentUploadContextPath(),
                path, apiGatewayConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.add.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(movieCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void updateMovie(TestMovieUpdateRequestDTO updateRequestDTO, String regularUserId) {
        String fullUrl = String.format("%s?userId=%s", getFullUrl(apiGatewayConfig.host().trim(),
                apiGatewayConfig.contentUploadContextPath(),
                "/api/user/movie", apiGatewayConfig.port()), regularUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.update.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieUpdateRequestDTO> requestObject = new HttpEntity<>(updateRequestDTO, headers);
        responseHolder.setResponse(this.put(fullUrl, requestObject, String.class));
    }
}