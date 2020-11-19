package com.gyutaechoi.kakaopay.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyDropTest {

    @Test
    void incrementNumOfMoneyGetters() {
        MoneyDrop moneyDrop = new MoneyDrop();
        moneyDrop.setNumOfMoneyGetters(10);

        moneyDrop.incrementNumOfMoneyGetters();

        assertEquals(11, moneyDrop.getNumOfMoneyGetters());
    }

    @Test
    void decrementCurrentBalanceBy() {
        MoneyDrop moneyDrop = new MoneyDrop();
        moneyDrop.setCurrentBalance(1000);

        moneyDrop.decrementCurrentBalanceBy(500);

        assertEquals(500, moneyDrop.getCurrentBalance());
    }
}