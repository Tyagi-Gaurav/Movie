package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.math.BigDecimal;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTestMovieCreateRequestDTO.Builder.class)
public interface TestMovieCreateRequestDTO {
    String name();

    int yearProduced();

    BigDecimal rating();
}
