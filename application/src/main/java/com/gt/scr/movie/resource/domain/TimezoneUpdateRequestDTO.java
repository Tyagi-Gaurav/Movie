package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTimezoneUpdateRequestDTO.Builder.class)
public interface TimezoneUpdateRequestDTO {
    UUID id();

    @Value.Default
    default String name() {
        return "";
    }

    @Value.Default
    default String city() {
        return "";
    }

    @Value.Default
    default int gmtOffset() {
        return -100;
    }
}
