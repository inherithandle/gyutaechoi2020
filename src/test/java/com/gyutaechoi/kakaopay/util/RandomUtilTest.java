package com.gyutaechoi.kakaopay.util;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
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

    @Test
    public void 랜덤넘버생성_1에서_500까지() {
        final int min = 1;
        final int max = 500;
        Random r = new SecureRandom();
        int randomNum;
        for (int j = 0; j < 10; j++) {
            randomNum = RandomUtil.generateRandomInteger(r, min, max);
            assertThat(randomNum, greaterThanOrEqualTo(min));
            assertThat(randomNum, lessThanOrEqualTo(max));
        }
    }

}