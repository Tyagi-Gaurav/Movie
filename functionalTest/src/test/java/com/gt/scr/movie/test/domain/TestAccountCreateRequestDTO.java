package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTestAccountCreateRequestDTO.Builder.class)
public interface TestAccountCreateRequestDTO {
    String userName();

    @Value.Default
    default String password() {
        return "";
    }

    String firstName();

    String lastName();

    String role();
}
