package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public record TestAccountCreateRequestDTO(String userName,
                                          String password,
                                          String firstName,
                                          String lastName,
                                          String role) {}
