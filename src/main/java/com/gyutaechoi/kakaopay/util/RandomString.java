package com.gyutaechoi.kakaopay.util;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

/**
 * package-private class.
 * RandomUtil.java만 이 클래스를 사용할 수 있다.
 */
class RandomString {

    /**
     * 랜덤 스트링 생성하기.
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = alphanumeric[random.nextInt(alphanumeric.length)];
        return new String(buf);
    }

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String lower = upper.toLowerCase(Locale.ROOT);
    public static final String digits = "0123456789";
    public static final char[] alphanumeric = (upper + lower + digits).toCharArray();
    private final Random random;
    private final char[] buf;

    public RandomString(int length) {
        if (length < 1) throw new RuntimeException("1보다 커야 한다.");
        this.random = new SecureRandom();
        this.buf = new char[length];
    }

}