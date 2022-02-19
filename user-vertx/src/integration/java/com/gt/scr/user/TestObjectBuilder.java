package com.gt.scr.user;


import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.resource.domain.LoginRequestDTO;

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

    public static AccountCreateRequestDTO adminAccountCreateRequest() {
        return new AccountCreateRequestDTO(
                randomAlphabetic(4), randomAlphabetic(6)
                , randomAlphabetic(7), randomAlphabetic(7),
                "ADMIN");
    }

    public static LoginRequestDTO invalidPasswordLoginRequestUsing(AccountCreateRequestDTO accountCreateRequestDTO) {
        return new LoginRequestDTO(accountCreateRequestDTO.userName(), UUID.randomUUID().toString());
    }

    public static LoginRequestDTO invalidUserLoginRequestUsing(AccountCreateRequestDTO accountCreateRequestDTO) {
        return new LoginRequestDTO(UUID.randomUUID().toString(), accountCreateRequestDTO.password());
    }
}
