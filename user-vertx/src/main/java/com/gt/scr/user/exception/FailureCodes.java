package com.gt.scr.user.exception;

public enum FailureCodes {
    DATABASE_FAILURE(1),
    NO_RECORD_FOUND(2);

    private final int failureCode;

    FailureCodes(int failureCode) {
        this.failureCode = failureCode;
    }

    public int getFailureCode() {
        return failureCode;
    }
}
