package com.gyutaechoi.kakaopay.exception;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class BadRequestExceptionTest {

    @Test
    public void tc1() {
        BadRequestException e = new BadRequestException("400 hello world");
        assertThat(e.getMessage(), is("400 hello world"));
    }

}