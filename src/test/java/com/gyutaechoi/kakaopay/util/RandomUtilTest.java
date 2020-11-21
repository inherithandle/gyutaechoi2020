package com.gyutaechoi.kakaopay.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomUtilTest {

    @Test
    public void randomStringTest() {
        String s;
        int NUMBER_OF_TRIALS = 10;
        for (int i = 1; i <= NUMBER_OF_TRIALS; i++) {
            s = RandomUtil.generateRandomString(3);
            assertEquals(3, s.length());
            System.out.println(s);
        }
    }

}