package com.toptal.scr.tz.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import com.toptal.scr.tz.service.domain.Role;
import org.apache.commons.lang3.EnumUtils;
import org.immutables.value.Value;

import static org.apache.commons.lang3.StringUtils.isAlpha;
import static org.apache.commons.lang3.StringUtils.isAlphanumeric;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableTimezoneCreateRequestDTO.Builder.class)
public interface TimezoneCreateRequestDTO {
    String name();

    String city();

    int gmtOffset();

    @Value.Check
    default void check() {
        Preconditions.checkArgument(name().length() >= 6 && name().length() <= 20, "Name length should be between 6 & 20.");
        Preconditions.checkArgument(city().length() >= 4 && city().length() <= 20, "City length should be between 4 and 20.");
        Preconditions.checkArgument(gmtOffset() <= 12 && gmtOffset() >= -12, "GMT Offset should be between -12 & +12.");
    }
}
