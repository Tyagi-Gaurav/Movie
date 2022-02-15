package com.gt.scr.user.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gt.scr.user.service.domain.Role;
import com.gt.scr.validator.AlphaNumeric;
import com.gt.scr.validator.MaxLength;
import com.gt.scr.validator.MinLength;
import com.gt.scr.validator.ValidEnum;

import javax.validation.Valid;

@JsonSerialize
@JsonDeserialize
@Valid
public record AccountCreateRequestDTO(
        @MinLength(4) @MaxLength(20) String userName,
        @AlphaNumeric @MinLength(6) @MaxLength(15) String password,
        @MinLength(3) @MaxLength(30) String firstName,
        @MinLength(3) @MaxLength(30) String lastName,
        @ValidEnum(Role.class) String role
) {
}
