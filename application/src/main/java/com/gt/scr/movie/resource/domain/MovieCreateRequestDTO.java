package com.gt.scr.movie.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gt.scr.spc.validator.MaxLength;
import com.gt.scr.spc.validator.MinLength;

import javax.validation.Valid;
import java.math.BigDecimal;

@JsonSerialize
@JsonDeserialize
@Valid
public record MovieCreateRequestDTO(@MinLength(6) @MaxLength(20) String name, int yearProduced, BigDecimal rating) {}
