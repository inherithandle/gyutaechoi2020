package com.gyutaechoi.kakaopay.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtil {

    public static String generateRandomString(final int length) {
        final int leftLimit = 48;
        final int rightLimit = 126;
        Random random = new SecureRandom();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
