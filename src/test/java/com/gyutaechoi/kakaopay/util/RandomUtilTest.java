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
        int NUMBER_OF_TRIALS = 10;
        for (int i = 1; i <= NUMBER_OF_TRIALS; i++) {
            s = RandomUtil.generateRandomString(3);
            assertEquals(3, s.length());
            System.out.println(s);
        }
    }

    @Test
    public void 랜덤넘버생성_1에서_500까지() {
        final int min = 1;
        final int max = 500;
        int NUMBER_OF_TRIALS = 10000;

        Random r = new SecureRandom();
        int randomNum;
        for (int j = 1; j <= NUMBER_OF_TRIALS; j++) {
            randomNum = RandomUtil.generateRandomIntegerBetween1AndMax(r, max);
//            System.out.println(randomNum);
            assertThat(randomNum, greaterThanOrEqualTo(min));
            assertThat(randomNum, lessThanOrEqualTo(max));
        }
    }

    @Test
    public void rNextTest() {
        SecureRandom r = new SecureRandom();
        int NUMBER_OF_TRIALS = 100;
        for (int i = 0; i < NUMBER_OF_TRIALS; i++) {
            int randomNum = r.nextInt(3); // 0 - 2 생성
            assertThat(randomNum, greaterThanOrEqualTo(0));
            assertThat(randomNum, lessThanOrEqualTo(2));
        }
    }

}