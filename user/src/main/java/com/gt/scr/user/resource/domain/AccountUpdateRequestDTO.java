package com.gt.scr.user.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public record AccountUpdateRequestDTO(String userName,
                                      String password,
                                      String firstName,
                                      String lastName,
                                      String role) { }
