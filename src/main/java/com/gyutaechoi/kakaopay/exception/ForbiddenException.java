package com.gyutaechoi.kakaopay.exception;

public class ForbiddenException extends RuntimeException {

    private static final String MSG = "403 Forbidden";

    public ForbiddenException() {
        super(MSG);
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
