package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public record TestLoginResponseDTO(String token, String id) {}
