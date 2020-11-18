package com.gyutaechoi.kakaopay.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomUtilTest {

    @Test
    public void randomStringTest() {
        String s;
        for (int i = 0; i < 10; i++) {
            s = RandomUtil.generateRandomString(3);
            assertEquals(3, s.length());
            System.out.println(s);
        }
    }

}