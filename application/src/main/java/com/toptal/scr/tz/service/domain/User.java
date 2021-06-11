package com.toptal.scr.tz.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.serial.Serial;
import org.immutables.value.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.UUID;

@Value.Immutable
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = ImmutableUser.Builder.class)
@Serial.Structural
public interface User extends UserDetails {
    int serialVersionUID = 1;

    UUID id();

    String firstName();

    String lastName();

    @Value.Default
    default boolean isAccountNonExpired() { return true;}

    @Value.Default
    default boolean isAccountNonLocked() { return true;}

    @Value.Default
    default boolean isCredentialsNonExpired() {return true;}

    @Value.Default
    default boolean isEnabled() {return true;}

    @Value.Default
    default HashMap<UUID, UserTimezone> userTimeZones() {
        return new HashMap<>();
    }

    default String getRole() {
        return getAuthorities().stream().map(auth -> auth.getAuthority()).findFirst().orElseGet(() -> "");
    }
}
