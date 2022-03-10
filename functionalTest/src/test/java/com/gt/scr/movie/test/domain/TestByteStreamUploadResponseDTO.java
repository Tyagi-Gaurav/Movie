package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

@JsonSerialize
@JsonDeserialize
public record TestByteStreamUploadResponseDTO(UUID movieId,
                                              UUID streamId,
                                              long sequence,
                                              long size,
                                              String streamName) {
}
