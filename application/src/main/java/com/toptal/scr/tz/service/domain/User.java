package com.toptal.scr.tz.service.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(builder = ImmutableUser.Builder.class)
public interface User extends UserDetails {
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

    //List<UserTimeZones>
}
