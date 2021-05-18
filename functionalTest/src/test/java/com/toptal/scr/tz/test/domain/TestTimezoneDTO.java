package com.toptal.scr.tz.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTestTimezoneDTO.Builder.class)
public interface TestTimezoneDTO {
    UUID id();

    String name();

    String city();

    int gmtOffset();
}
