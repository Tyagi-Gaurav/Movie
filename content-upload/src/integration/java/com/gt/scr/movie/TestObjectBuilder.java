package com.gt.scr.movie;

import com.gt.scr.movie.ext.user.UserDetailsResponseDTO;

import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestObjectBuilder {
    public static UserDetailsResponseDTO validUserResponseDto(UUID userId, String userName, String grantedRole) {
        return new UserDetailsResponseDTO(userName,
                randomAlphabetic(7), randomAlphabetic(7),
                randomAlphabetic(7),
                grantedRole,
                userId);
    }
}
