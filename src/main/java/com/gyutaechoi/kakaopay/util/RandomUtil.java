package com.gyutaechoi.kakaopay.util;

import java.util.Random;

public class RandomUtil {

    public static String generateRandomString(final int length) {
        return new RandomString(length).nextString();
    }

    /**
     * 0-max 랜덤 정수를 생성한다.
     */
    public static int generateRandomInteger(Random r, final int max) {
        return r.nextInt(max + 1);
    }

    /**
     * 1 ~ max값을 생성.
     * @param r
     * @param max
     * @return
     */
    public static int generateRandomIntegerBetween1AndMax(Random r, final int max) {
        if (max == 1) return 1;
        return r.nextInt(max) + 1;
    }

    // r.nextInt(3), 0 ~ 2 정수 생성

    // r.next(500), 0 ~ 499 생성
    // r.next(500) + 1, 1 - 500 생성

    // r.next(max + 1), 1 ~ max 생성
}
