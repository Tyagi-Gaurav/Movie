package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableAccountCreateRequestDTO.Builder.class)
public interface AccountCreateRequestDTO {
    String userName();

    String password();

    String firstName();

    String lastName();

    List<String> roles();
}
