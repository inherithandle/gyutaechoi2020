package com.gyutaechoi.kakaopay.exception;

public class NotFoundException extends RuntimeException {

    private static final String MSG = "404 Not Found";

    public NotFoundException() {
        super(MSG);
    }

    public NotFoundException(String message) {
        super(message);
    }

}
