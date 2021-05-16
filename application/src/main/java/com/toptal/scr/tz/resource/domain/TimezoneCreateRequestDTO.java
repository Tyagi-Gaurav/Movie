package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTimezoneCreateRequestDTO.Builder.class)
public interface TimezoneCreateRequestDTO {
    String name();

    String city();

    int gmtOffset();
}
