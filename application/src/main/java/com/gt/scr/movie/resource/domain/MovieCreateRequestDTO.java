package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import org.immutables.value.Value;

import java.math.BigDecimal;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableMovieCreateRequestDTO.Builder.class)
public interface MovieCreateRequestDTO {
    String name();

    int yearProduced();

    BigDecimal rating();

    @Value.Check
    default void check() {
        Preconditions.checkArgument(name().length() >= 6 && name().length() <= 20, "Name length should be between 6 & 20.");
        //Preconditions.checkArgument(city().length() >= 4 && city().length() <= 20, "City length should be between 4 and 20.");
        //Preconditions.checkArgument(gmtOffset() <= 12 && gmtOffset() >= -12, "GMT Offset should be between -12 & +12.");
    }
}
