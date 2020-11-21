package com.gyutaechoi.kakaopay.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomStringTest {

    @Test
    void nextString() {
        String s;
        for (int i = 0; i < 100; i++) {
            s = new RandomString(3).nextString();
            assertEquals(3, s.length());
            assertTrue(s.matches("^[a-zA-Z0-9]+$"));
            System.out.println(s);
        }
    }
}