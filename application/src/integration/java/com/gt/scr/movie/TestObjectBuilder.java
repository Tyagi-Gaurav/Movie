package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;

import java.math.BigDecimal;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestObjectBuilder {
    public static AccountCreateRequestDTO userAccountCreateRequestDTO() {
        return new AccountCreateRequestDTO(
                randomAlphabetic(4), randomAlphabetic(6)
                , randomAlphabetic(7), randomAlphabetic(7),
                "USER");
    }

    public static AccountCreateRequestDTO invalidUserAccountCreateRequestDTO() {
        return new AccountCreateRequestDTO(
                randomAlphabetic(2), "abc$123"
                , randomAlphabetic(7), randomAlphabetic(7),
                "USER");
    }

    public static LoginRequestDTO loginRequestUsing(AccountCreateRequestDTO accountCreateRequestDTO) {
        return new LoginRequestDTO(accountCreateRequestDTO.userName(), accountCreateRequestDTO.password());
    }

    public static MovieCreateRequestDTO movieCreateRequestDTO() {
        return new MovieCreateRequestDTO(randomAlphabetic(7), 2010,
                BigDecimal.valueOf(5));
    }

    public static MovieCreateRequestDTO invalidMovieCreateRequestDTO(String name) {
        return new MovieCreateRequestDTO(name, 2010,
                BigDecimal.valueOf(5));
    }

    public static AccountCreateRequestDTO adminAccountCreateRequest() {
        return new AccountCreateRequestDTO(
                randomAlphabetic(4), randomAlphabetic(6)
                , randomAlphabetic(7), randomAlphabetic(7),
                "ADMIN");
    }

    public static LoginRequestDTO invalidPasswordLoginRequestUsing(AccountCreateRequestDTO accountCreateRequestDTO) {
        return new LoginRequestDTO(accountCreateRequestDTO.userName(), UUID.randomUUID().toString());
    }
}
