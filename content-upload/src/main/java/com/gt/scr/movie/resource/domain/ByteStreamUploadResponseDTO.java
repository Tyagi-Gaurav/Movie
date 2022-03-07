package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

@JsonDeserialize
@JsonSerialize
public record ByteStreamUploadResponseDTO(UUID movieId,
                                          UUID streamId,
                                          long sequence,
                                          long size,
                                          String streamName) {
}
