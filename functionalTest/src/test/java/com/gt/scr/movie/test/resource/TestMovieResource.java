package com.gt.scr.movie.test.resource;

import com.gt.scr.movie.test.config.MovieAppConfig;
import com.gt.scr.movie.test.domain.TestMovieCreateRequestDTO;
import com.gt.scr.movie.test.domain.TestMovieUpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TestMovieResource extends AbstractResource {

    @Autowired
    private MovieAppConfig movieAppConfig;

    @Autowired
    private ResponseHolder responseHolder;

    public void create(TestMovieCreateRequestDTO movieCreateRequestDTO) {
        String fullUrl = getFullUrl(movieAppConfig.host().trim(),
                "/api/user/movie", movieAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.add.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(movieCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void createWithoutToken(TestMovieCreateRequestDTO testMovieCreateRequestDTO) {
        String fullUrl = getFullUrl(movieAppConfig.host().trim(),
                "/api/user/movie", movieAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.add.v1+json");
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(testMovieCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void readMovies() {
        String fullUrl = getFullUrl(movieAppConfig.host().trim(),
                "/api/user/movie", movieAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.read.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(headers);
        responseHolder.setResponse(this.get(fullUrl, requestObject, String.class));
    }

    public void deleteMovie(UUID uuid) {
        String fullUrl = String.format("%s?id=%s", getFullUrl(movieAppConfig.host().trim(),
                "/api/user/movie", movieAppConfig.port()), uuid.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.delete.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(headers);

        responseHolder.setResponse(this.delete(fullUrl, requestObject, String.class));
    }

    public void updateMovie(TestMovieUpdateRequestDTO updateRequestDTO) {
        String fullUrl = getFullUrl(movieAppConfig.host().trim(),
                "/api/user/movie", movieAppConfig.port());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.update.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieUpdateRequestDTO> requestObject = new HttpEntity<>(updateRequestDTO, headers);
        responseHolder.setResponse(this.put(fullUrl, requestObject, String.class));
    }

    public void readMovies(String userId) {
        String fullUrl = String.format("%s?userId=%s", getFullUrl(movieAppConfig.host().trim(),
                "/api/user/movie", movieAppConfig.port()), userId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.read.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(headers);
        responseHolder.setResponse(this.get(fullUrl, requestObject, String.class));
    }

    public void deleteMovie(UUID movieId, String regularUserId) {
        String fullUrl = String.format("%s?id=%s&userId=%s", getFullUrl(movieAppConfig.host().trim(),
                "/api/user/movie", movieAppConfig.port()), movieId.toString(), regularUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.delete.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(headers);

        responseHolder.setResponse(this.delete(fullUrl, requestObject, String.class));
    }

    public void create(TestMovieCreateRequestDTO movieCreateRequestDTO, String regularUserId) {
        String fullUrl = String.format("%s?userId=%s", getFullUrl(movieAppConfig.host().trim(),
                "/api/user/movie", movieAppConfig.port()), regularUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.add.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieCreateRequestDTO> requestObject = new HttpEntity<>(movieCreateRequestDTO, headers);
        responseHolder.setResponse(this.post(fullUrl, requestObject, String.class));
    }

    public void updateMovie(TestMovieUpdateRequestDTO updateRequestDTO, String regularUserId) {
        String fullUrl = String.format("%s?userId=%s", getFullUrl(movieAppConfig.host().trim(),
                "/api/user/movie", movieAppConfig.port()), regularUserId);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.update.v1+json");
        headers.setBearerAuth(responseHolder.getToken());
        HttpEntity<TestMovieUpdateRequestDTO> requestObject = new HttpEntity<>(updateRequestDTO, headers);
        responseHolder.setResponse(this.put(fullUrl, requestObject, String.class));
    }
}