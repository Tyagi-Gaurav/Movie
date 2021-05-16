package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTimezoneCreateRequestDTO.Builder.class)
public interface TimezoneDTO {
    UUID id();

    String name();

    String city();

    int gmtOffset();
}
