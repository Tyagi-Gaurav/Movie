package com.gt.scr.user.exception;

public enum FailureCodes {
    DatabaseFailure(1),
    NoRecordFound(2);

    private final int failureCode;

    FailureCodes(int failureCode) {
        this.failureCode = failureCode;
    }

    public int getFailureCode() {
        return failureCode;
    }
}
