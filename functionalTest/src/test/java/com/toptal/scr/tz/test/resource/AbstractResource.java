package com.toptal.scr.tz.test.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
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

    protected <T> ResponseEntity get(String fullUrl, Class<T> responseType) {
        try {
            return restTemplate.getForEntity(fullUrl, responseType);
        } catch (Exception e) {
            LOG.error("Error Occurred while invoking: " + fullUrl, e);
            RestClientResponseException exception = (RestClientResponseException) e;
            return ResponseEntity.status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }
    }

    protected <T> ResponseEntity post(String fullUrl, Object request, Class<T> responseType) {
        try {
            return restTemplate.postForEntity(fullUrl, request, responseType);
        } catch (Exception e) {
            LOG.error("Error Occurred while invoking: " + fullUrl, e);
            if (e instanceof RestClientException) {
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
}
