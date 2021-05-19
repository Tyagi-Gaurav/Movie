package com.toptal.scr.tz.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTestTimezoneUpdateRequestDTO.Builder.class)
public interface TestTimezoneUpdateRequestDTO {
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
