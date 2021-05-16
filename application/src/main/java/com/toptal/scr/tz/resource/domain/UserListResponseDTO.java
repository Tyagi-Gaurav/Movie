package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableUserListResponseDTO.Builder.class)
public interface UserListResponseDTO {
    List<UserDetailsResponse> userDetails();
}
