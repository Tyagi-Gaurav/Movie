package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableUserDetailsResponse.Builder.class)
public interface UserDetailsResponse {
    String userName();

    String firstName();

    String lastName();

    String role();

    UUID id();
}
