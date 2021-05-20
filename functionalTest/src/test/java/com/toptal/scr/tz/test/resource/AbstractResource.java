package com.toptal.scr.tz.test.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class AbstractResource {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractResource.class);

    @Autowired
    protected RestTemplate restTemplate;

    protected String getFullUrl(String hostName, String path, int port) {
        StringBuilder fullUrl = new StringBuilder("http://" + hostName);

        if (port != 0) {
            fullUrl.append(":").append(port);
        }

        fullUrl.append(path);

        return fullUrl.toString();
    }

    protected <T> ResponseEntity get(String fullUrl, HttpEntity requestObject, Class<T> responseType) {
        try {
            return restTemplate.exchange(fullUrl, HttpMethod.GET, requestObject, responseType);
        } catch (Exception e) {
            return handleError(fullUrl, e);
        }
    }

    protected <T> ResponseEntity delete(String fullUrl, HttpEntity requestObject, Class<T> responseType) {
        try {
            return restTemplate.exchange(fullUrl, HttpMethod.DELETE, requestObject, responseType);
        } catch (Exception e) {
            return handleError(fullUrl, e);
        }
    }

    protected <T> ResponseEntity post(String fullUrl, Object request, Class<T> responseType) {
        try {
            return restTemplate.postForEntity(fullUrl, request, responseType);
        } catch (Exception e) {
            return handleError(fullUrl, e);
        }
    }

    protected ResponseEntity put(String fullUrl, HttpEntity requestObject, Class<String> responseType) {
        try {
            return restTemplate.exchange(fullUrl, HttpMethod.PUT, requestObject, responseType);
        } catch (Exception e) {
            return handleError(fullUrl, e);
        }
    }

    private ResponseEntity handleError(String fullUrl, Exception e) {
        LOG.error("Error Occurred while invoking: " + fullUrl, e);
        if (e instanceof RestClientResponseException) {
            RestClientResponseException exception = (RestClientResponseException) e;
            return ResponseEntity.status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        } else if (e instanceof HttpMessageConversionException) {
            HttpMessageConversionException exception = (HttpMessageConversionException) e;
            exception.printStackTrace();
            throw new RuntimeException(exception);
        } else {
            throw new RuntimeException(e);
        }
    }
}
