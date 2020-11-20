package com.gyutaechoi.kakaopay.util;

import org.junit.jupiter.api.Test;

class RandomStringTest {

    @Test
    void nextString() {
        for (int i = 0; i < 100; i++) {
            System.out.println(new RandomString(3).nextString());
        }
    }
}