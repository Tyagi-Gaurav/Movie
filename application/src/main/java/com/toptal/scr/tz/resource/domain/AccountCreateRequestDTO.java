package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import com.toptal.scr.tz.service.domain.Role;
import org.apache.commons.lang3.EnumUtils;
import org.immutables.value.Value;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableAccountCreateRequestDTO.Builder.class)
public interface AccountCreateRequestDTO {
    String userName();

    String password();

    String firstName();

    String lastName();

    String role();

    @Value.Check
    default void check() {
        Preconditions.checkArgument(isAlphanumeric(password()), "Password should be alphanumeric.");
        Preconditions.checkArgument(password().length() >= 6 && password().length() <= 15, "Password length should be between 6 & 15.");
        Preconditions.checkArgument(userName().length() >= 4 && userName().length() <= 20, "UserName length should be between 4 and 20.");
        Preconditions.checkArgument(firstName().length() >= 3 && firstName().length() <= 30, "FirstName length should be between 3 and 30.");
        Preconditions.checkArgument(lastName().length() >= 3 && lastName().length() <= 30, "LastName length should be between 3 and 30.");
        Preconditions.checkArgument(EnumUtils.isValidEnum(Role.class, role()), "Role can either be one of ('USER', 'ADMIN').");
    }
}
