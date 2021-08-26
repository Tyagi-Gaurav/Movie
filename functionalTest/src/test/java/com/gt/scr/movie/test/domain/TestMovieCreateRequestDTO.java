package com.gt.scr.movie.test.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize
@JsonDeserialize
public record TestMovieCreateRequestDTO(String name,
                                        int yearProduced,
                                        BigDecimal rating) { }
