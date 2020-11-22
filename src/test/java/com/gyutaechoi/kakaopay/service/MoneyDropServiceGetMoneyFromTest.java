package com.gyutaechoi.kakaopay.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * MoneyDropService.getMoneyFrom 메서드 단위 테스트
 */
public class MoneyDropServiceGetMoneyFromTest {

    private MoneyDropService moneyDropService = new MoneyDropService();

    @Test
    public void indexTest() {
        List<Integer> integers = new ArrayList<>();
        Collections.addAll(integers, 1,23, 0, 1, 0);

        assertThat(moneyDropService.getMoneyFrom(integers, 4), equalTo(0));
        assertThat(moneyDropService.getMoneyFrom(integers, 5), equalTo(0));
        assertThat(moneyDropService.getMoneyFrom(integers, 6), equalTo(0));
        assertThat(moneyDropService.getMoneyFrom(integers, 7), equalTo(0));
    }

}
