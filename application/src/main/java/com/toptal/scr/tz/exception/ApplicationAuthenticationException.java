package com.toptal.scr.tz.exception;

public class ApplicationAuthenticationException extends RuntimeException {
    public ApplicationAuthenticationException(String message, Exception exception) {
        super(message, exception);
    }

    public ApplicationAuthenticationException(String message) {
        super(message);
    }
}
