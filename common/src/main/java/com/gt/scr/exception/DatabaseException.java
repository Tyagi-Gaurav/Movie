package com.gt.scr.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Exception cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
