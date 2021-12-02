package com.gt.scr.user.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
public record User(UUID id,
                   String firstName,
                   String lastName,
                   String username,
                   String password,
                   Collection<GrantedAuthority> authorities) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() { return true;}

    public boolean isAccountNonLocked() { return true;}

    public boolean isCredentialsNonExpired() {return true;}

    public boolean isEnabled() {return true;}

    public String getRole() {
        return getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElseGet(() -> "");
    }
}
