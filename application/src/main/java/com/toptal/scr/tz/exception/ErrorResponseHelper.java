package com.toptal.scr.tz.exception;

public class ErrorResponseHelper {
    public static String errorResponse(String message) {
        return String.format("{\"message\" : %s}", message);
    }
}
