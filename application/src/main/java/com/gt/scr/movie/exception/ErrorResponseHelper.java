package com.gt.scr.movie.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.movie.metrics.ExceptionCounter;
import com.gt.scr.movie.resource.domain.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorResponseHelper.class);

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ExceptionCounter exceptionCounter;

    public ResponseEntity<String> errorResponse(int statusCode, String message) {
        try {
            var responseBody = objectMapper.writeValueAsString(new ErrorResponse(message));

            LOG.error("Response Body with status code {}: {}", statusCode, responseBody);

            exceptionCounter.increment(statusCode);
            
            return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
