package com.toptal.scr.tz.util;

import com.toptal.scr.tz.service.domain.ImmutableUser;
import com.toptal.scr.tz.service.domain.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.UUID;

public class TestBuilders {
    public static User aUser() {
        return ImmutableUser.builder()
                .id(UUID.randomUUID())
                .password(RandomStringUtils.randomAlphabetic(10))
                .username(RandomStringUtils.randomAlphabetic(10))
                .lastName(RandomStringUtils.randomAlphabetic(10))
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .authorities(Collections.singleton(new SimpleGrantedAuthority("USER")))
                .build();
    }
}
