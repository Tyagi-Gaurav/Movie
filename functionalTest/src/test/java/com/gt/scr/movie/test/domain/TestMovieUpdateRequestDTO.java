package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTestMovieUpdateRequestDTO.Builder.class)
public interface TestMovieUpdateRequestDTO {
    UUID id();

    @Value.Default
    default String name() {
        return "";
    }

    @Value.Default
    default BigDecimal rating() {
        return BigDecimal.ZERO;
    }

    @Value.Default
    default int yearProduced() {
        return 0;
    }
}
