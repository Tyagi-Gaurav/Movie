package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableMovieCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;

import java.math.BigDecimal;

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

    public static ImmutableMovieCreateRequestDTO.Builder movieCreateRequestDTO() {
        return ImmutableMovieCreateRequestDTO.builder()
                .name(randomAlphabetic(7))
                .yearProduced(2010)
                .rating(BigDecimal.valueOf(5));
    }

    public static AccountCreateRequestDTO adminAccountCreateRequest() {
        return new AccountCreateRequestDTO(
                randomAlphabetic(4), randomAlphabetic(6)
                , randomAlphabetic(7), randomAlphabetic(7),
                "ADMIN");
    }
}
