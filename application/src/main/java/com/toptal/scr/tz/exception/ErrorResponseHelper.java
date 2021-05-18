package com.toptal.scr.tz.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.scr.tz.resource.domain.ImmutableErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseHelper {
    @Autowired
    private ObjectMapper objectMapper;

    public ResponseEntity<String> errorResponse(int statusCode, String message) {
        try {
            String responseBody = objectMapper.writeValueAsString(ImmutableErrorResponse.builder()
                    .message(message)
                    .build());

            return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON).body(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
