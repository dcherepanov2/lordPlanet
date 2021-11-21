package com.cd.ntiteam_test.exception;

public class PlanetException extends Exception {

    private final String message;

    public PlanetException(String s) {
        message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
