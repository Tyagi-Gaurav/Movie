package com.gt.scr.movie.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Value.Immutable
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = ImmutableMovie.Builder.class)
public interface Movie extends Serializable {
    UUID id();

    String name();

    int yearProduced();

    BigDecimal rating();

    @Value.Default
    default long creationTimeStamp() {
        return System.nanoTime();
    }
}
