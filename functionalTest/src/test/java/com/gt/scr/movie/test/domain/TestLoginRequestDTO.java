package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTestLoginRequestDTO.Builder.class)
public interface TestLoginRequestDTO {
    String userName();

    String password();
}
