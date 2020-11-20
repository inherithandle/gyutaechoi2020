package com.gyutaechoi.kakaopay.exception;

public class BadRequestException extends RuntimeException {

    private static final String MSG = "400 Bad Request";

    public BadRequestException() {
        super(MSG);
    }

    public BadRequestException(String message) {
        super(message);
    }
}
