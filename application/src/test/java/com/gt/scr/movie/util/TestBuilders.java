package com.gt.scr.movie.util;

import com.gt.scr.movie.service.domain.ImmutableUser;
import com.gt.scr.movie.service.domain.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestBuilders {
    public static User aUser() {
        return ImmutableUser.builder()
                .id(UUID.randomUUID())
                .password(randomAlphabetic(10))
                .username(randomAlphabetic(10))
                .lastName(randomAlphabetic(10))
                .firstName(randomAlphabetic(10))
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("USER")))
                .build();
    }

    public static User aUserWithUserName(String userName) {
        return ImmutableUser.builder()
                .id(UUID.randomUUID())
                .password(randomAlphabetic(10))
                .username(userName)
                .lastName(randomAlphabetic(10))
                .firstName(randomAlphabetic(10))
                .authorities(Collections.singleton(new SimpleGrantedAuthority("USER")))
                .build();
    }
}
