package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

@JsonSerialize
@JsonDeserialize
public record TestByteStreamUploadDTO(UUID movieId,
                                      String streamName,
                                      byte[] byteStream) {
    @Override
    public String toString() {
        return "TestByteStreamUploadDTO{" +
                "movieId=" + movieId +
                ", streamName='" + streamName + '\'' +
                '}';
    }
}
