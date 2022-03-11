package com.gt.scr.movie.test.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ResponseHolder {
    private List<ResponseEntity> previousResponses = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    private String token;
    private String userId;
    private UUID movieId;

    public void setResponse(ResponseEntity entity) {
        previousResponses.add(0, entity);
    }

    public int getResponseCode() {
        return getLastResponse().getStatusCode().value();
    }

    public <T> T readResponse(Class<T> clazz) {
        return read(getLastResponse(), clazz);
    }

    private <T> T read(ResponseEntity responseEntity, Class<T> clazz) {
        Object body = responseEntity.getBody();

        if (body != null && clazz.isAssignableFrom(body.getClass())) {
            return (T) body;
        }

        try {
            return objectMapper.readValue(body.toString(), clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpHeaders getHeaders() {
        return getLastResponse().getHeaders();
    }

    public <T> T getPreviousResponse(int index, Class<T> clazz) {
        Optional<ResponseEntity> previousResponseEntity = Optional.ofNullable(previousResponses.get(index));
        ResponseEntity responseEntity = previousResponseEntity.orElseThrow(IllegalStateException::new);
        return read(responseEntity, clazz);
    }

    private ResponseEntity getLastResponse() {
        return previousResponses.get(0);
    }

    @PreDestroy
    public void clearResponses() {
        previousResponses.clear();
    }

    public void storeUserToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void storeUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
    }

    public UUID getMovieId() {
        return movieId;
    }
}
