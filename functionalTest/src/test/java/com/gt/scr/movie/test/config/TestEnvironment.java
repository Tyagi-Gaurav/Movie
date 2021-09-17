package com.gt.scr.movie.test.config;

public class TestEnvironment {
    public static boolean isGrpc() {
        return "grpc".equalsIgnoreCase(System.getProperty("protocol"));
    }
}
