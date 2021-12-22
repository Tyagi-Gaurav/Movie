package com.gt.scr.exception;

public class ApplicationAuthenticationException extends RuntimeException {
    public ApplicationAuthenticationException(String message, Exception exception) {
        super(message, exception);
    }

    public ApplicationAuthenticationException(String message) {
        super(message);
    }

    public ApplicationAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
