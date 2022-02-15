package com.gt.scr.movie;

import com.gt.scr.movie.ext.user.UserDetailsResponseDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;

import java.math.BigDecimal;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestObjectBuilder {
    public static UserDetailsResponseDTO validUserResponseDto(UUID userId, String userName) {
        return new UserDetailsResponseDTO(userName,
                randomAlphabetic(7), randomAlphabetic(7),
                randomAlphabetic(7),
                "USER",
                userId);
    }

    public static MovieCreateRequestDTO movieCreateRequestDTO() {
        return new MovieCreateRequestDTO(randomAlphabetic(7), 2010,
                BigDecimal.valueOf(5));
    }

    public static MovieCreateRequestDTO invalidMovieCreateRequestDTO(String name) {
        return new MovieCreateRequestDTO(name, 2010,
                BigDecimal.valueOf(5));
    }
}
