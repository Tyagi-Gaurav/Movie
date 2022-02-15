package com.gt.scr.user.config;

public enum EventBusAddress {
    REPO_ACCOUNT_CREATE("account.create.repo"),
    REPO_USER_FETCH_BY_USER_NAME("user.fetch.by.userName"),
    REPO_USER_FETCH_BY_USER_ID("user.fetch.by.userId"),
    REPO_USER_DELETE_BY_USER_ID("user.delete.by.userId"),
    REPO_USER_FETCH_ALL("user.fetch.all");

    private final String address;

    EventBusAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
