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
}
