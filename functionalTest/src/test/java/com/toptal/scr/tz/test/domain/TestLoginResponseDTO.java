package com.toptal.scr.tz.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTestLoginResponseDTO.Builder.class)
public interface TestLoginResponseDTO {
    String token();

    String id();
}
