package com.toptal.scr.tz.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.io.Serializable;
import java.util.UUID;

@Value.Immutable
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = ImmutableUserTimezone.Builder.class)
public interface UserTimezone extends Serializable {
    UUID id();

    String name();

    String city();

    int gmtOffset();
}
