package com.gyutaechoi.kakaopay.util;

import java.util.Random;

public class RandomUtil {

    public static String generateRandomString(final int length) {
        return new RandomString(length).nextString();
    }

    public static int generateRandomInteger(Random r, final int min, final int max) {
        if (min == max) return 1;
        return r.nextInt(max - min) + min;
    }
}
