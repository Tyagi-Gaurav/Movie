package com.gt.scr.user;


import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.resource.domain.LoginRequestDTO;

import java.util.UUID;

public class LoginRequestBuilder {
    public static LoginRequestDTO loginRequestUsing(AccountCreateRequestDTO accountCreateRequestDTO) {
        return new LoginRequestDTO(accountCreateRequestDTO.userName(), accountCreateRequestDTO.password());
    }

    public static LoginRequestDTO invalidPasswordLoginRequestUsing(AccountCreateRequestDTO accountCreateRequestDTO) {
        return new LoginRequestDTO(accountCreateRequestDTO.userName(), UUID.randomUUID().toString());
    }

    public static LoginRequestDTO invalidUserLoginRequestUsing(AccountCreateRequestDTO accountCreateRequestDTO) {
        return new LoginRequestDTO(UUID.randomUUID().toString(), accountCreateRequestDTO.password());
    }
}
