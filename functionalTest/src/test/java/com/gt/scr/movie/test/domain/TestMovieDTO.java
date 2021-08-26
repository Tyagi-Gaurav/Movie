package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.UUID;

@JsonSerialize
@JsonDeserialize
public record TestMovieDTO(UUID id,
                           String name,
                           BigDecimal rating,
                           int yearProduced) { }
