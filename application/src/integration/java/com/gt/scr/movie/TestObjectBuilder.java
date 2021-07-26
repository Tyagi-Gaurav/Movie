package com.gt.scr.movie;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableAccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableLoginRequestDTO;
import com.gt.scr.movie.resource.domain.ImmutableMovieCreateRequestDTO;

import java.math.BigDecimal;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestObjectBuilder {
    public static ImmutableAccountCreateRequestDTO.Builder accountCreateRequestDTO() {
        return ImmutableAccountCreateRequestDTO.builder()
                .firstName(randomAlphabetic(7))
                .lastName(randomAlphabetic(7))
                .userName(randomAlphabetic(4))
                .password(randomAlphabetic(6))
                .role("USER");
    }

    public static ImmutableLoginRequestDTO.Builder loginRequestUsing(AccountCreateRequestDTO accountCreateRequestDTO) {
        return ImmutableLoginRequestDTO.builder()
                .userName(accountCreateRequestDTO.userName())
                .password(accountCreateRequestDTO.password());
    }

    public static ImmutableMovieCreateRequestDTO.Builder movieCreateRequestDTO() {
        return ImmutableMovieCreateRequestDTO.builder()
                .name(randomAlphabetic(7))
                .yearProduced(2010)
                .rating(BigDecimal.valueOf(5));
    }
}
