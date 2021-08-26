package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.UUID;

@JsonSerialize
@JsonDeserialize
public record MovieUpdateRequestDTO(UUID id,
                                    String name,
                                    BigDecimal rating,
                                    int yearProduced) {}
