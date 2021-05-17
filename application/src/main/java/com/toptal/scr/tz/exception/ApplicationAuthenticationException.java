package com.toptal.scr.tz.exception;

public class ApplicationAuthenticationException extends RuntimeException {
    public ApplicationAuthenticationException(String message) {
        super(message);
    }

    public ApplicationAuthenticationException(Exception exception) {
        super(exception);
    }
}
