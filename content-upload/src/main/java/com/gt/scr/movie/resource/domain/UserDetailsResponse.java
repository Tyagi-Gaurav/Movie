package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

@JsonSerialize
@JsonDeserialize
public record UserDetailsResponse(String userName,
                                  String firstName,
                                  String lastName,
                                  String role,
                                  UUID id) {
}
