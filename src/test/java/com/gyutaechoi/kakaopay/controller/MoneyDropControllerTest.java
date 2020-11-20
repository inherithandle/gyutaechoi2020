package com.gyutaechoi.kakaopay.controller;

import org.junit.jupiter.api.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class MoneyDropControllerTest {

    @Test
    public void urlDecodingTest() throws Exception{
        final String helloKorean = "안녕";
        final String encodedHelloKorean = "%EC%95%88%EB%85%95";
        assertThat(URLEncoder.encode(helloKorean, "UTF-8"), is(encodedHelloKorean));
        assertThat(URLDecoder.decode(encodedHelloKorean, "UTF-8"), is(helloKorean));

        final String hello = "hello12__A";
        assertThat(URLEncoder.encode(hello, "UTF-8"), is(hello));
    }

}