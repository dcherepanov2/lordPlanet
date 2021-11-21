package com.cd.ntiteam_test.exception;

public class LordException extends Exception {

    private final String message;

    public LordException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
