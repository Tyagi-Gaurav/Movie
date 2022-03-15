package com.gt.scr.user;

import com.gt.scr.domain.Gender;
import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class AccountCreateRequestBuilder {
    private String userName = randomAlphabetic(4);
    private String password = randomAlphabetic(6);
    private String firstName = randomAlphabetic(7);
    private String lastName = randomAlphabetic(7);
    private String dateOfBirth = "10/10/2010";
    private Gender gender = Gender.FEMALE;
    private String homeCountry = "AUS";
    private String role = "USER";

    private AccountCreateRequestBuilder() {
    }

    public static AccountCreateRequestBuilder accountCreateRequest() {
        return new AccountCreateRequestBuilder();
    }

    public AccountCreateRequestDTO build() {
        return new AccountCreateRequestDTO(
                userName, password, firstName, lastName,
                dateOfBirth, gender, homeCountry, role);
    }

    public AccountCreateRequestBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public AccountCreateRequestBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    public AccountCreateRequestBuilder withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public AccountCreateRequestBuilder withHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
        return this;
    }
}
