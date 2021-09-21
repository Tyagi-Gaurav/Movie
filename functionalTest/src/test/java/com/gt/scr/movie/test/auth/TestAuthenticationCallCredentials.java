package com.gt.scr.movie.test.auth;

import io.grpc.CallCredentials;
import io.grpc.Metadata;

import java.util.concurrent.Executor;

public class TestAuthenticationCallCredentials extends CallCredentials {

    private final String token;

    public TestAuthenticationCallCredentials(String token) {
        this.token = token;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo,
                                     Executor appExecutor,
                                     MetadataApplier applier) {
        appExecutor.execute(() -> {
            Metadata metadata = new Metadata();
            metadata.put(
                    Metadata.Key.of("Authorization",
                            Metadata.ASCII_STRING_MARSHALLER), "Bearer " + token);
            applier.apply(metadata);
        });
    }

    @Override
    public void thisUsesUnstableApi() {

    }
}
