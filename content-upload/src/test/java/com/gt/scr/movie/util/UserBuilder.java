package com.gt.scr.movie.util;

import com.gt.scr.domain.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class UserBuilder {

    private UUID id = UUID.randomUUID();
    private String password = randomAlphabetic(10);
    private String username = randomAlphabetic(10);
    private String firstName = randomAlphabetic(10);
    private String lastName = randomAlphabetic(10);
    private List<String> authorities =
            Collections.singletonList("USER");

    private UserBuilder() {
    }

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public static UserBuilder copyOf(User currentUser) {
        UserBuilder userBuilder = new UserBuilder();

        userBuilder.id = currentUser.id();
        userBuilder.username = currentUser.username();
        userBuilder.firstName = currentUser.firstName();
        userBuilder.lastName = currentUser.lastName();
        userBuilder.password = currentUser.password();
        userBuilder.authorities = currentUser.authorities().stream().toList();

        return userBuilder;
    }

    public UserBuilder withUserName(String userName) {
        this.username = userName;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withUserPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(this.id,
                this.firstName,
                this.lastName,
                this.username,
                this.password,
                this.authorities);
    }
}
