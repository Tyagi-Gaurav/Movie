package com.toptal.scr.tz.test.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ResponseHolder {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseHolder.class);

    private List<ResponseEntity> previousResponses = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();

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
}
