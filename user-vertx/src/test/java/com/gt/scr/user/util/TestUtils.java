package com.gt.scr.user.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.domain.User;
import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.domain.Gender;
import com.gt.scr.user.resource.domain.LoginRequestDTO;

import java.util.Collections;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestUtils {
    final static ObjectMapper mapper = new ObjectMapper();

    public static String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static AccountCreateRequestDTO testAccountCreateRequestDTO() {
        return new AccountCreateRequestDTO(
                randomAlphabetic(4), randomAlphabetic(6)
                , randomAlphabetic(7), randomAlphabetic(7),
                "10/10/2010", Gender.FEMALE, "AUS",
                "USER");
    }

    public static LoginRequestDTO testLoginRequest() {
        return new LoginRequestDTO(randomAlphabetic(6), randomAlphabetic(8));
    }

    public static User testUser() {
        return new User(UUID.randomUUID(), randomAlphabetic(6),
                randomAlphabetic(6), randomAlphabetic(6), randomAlphabetic(6)
                , Collections.emptyList());
    }
}
