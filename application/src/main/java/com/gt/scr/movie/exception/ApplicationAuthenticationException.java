package com.gt.scr.movie.exception;

public class ApplicationAuthenticationException extends RuntimeException {
    public ApplicationAuthenticationException(String message, Exception exception) {
        super(message, exception);
    }
}
