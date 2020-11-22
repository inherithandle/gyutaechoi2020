package com.gyutaechoi.kakaopay.validator;

import com.gyutaechoi.kakaopay.dto.MoneyDropRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyValidatorTest {

    @Test
    void isValidTest() {

        MoneyValidator validator = new MoneyValidator();
        MoneyDropRequest moneyDrop = new MoneyDropRequest();

        moneyDrop.setHowManyUsers(100);
        moneyDrop.setMoneyToDrop(50);
        assertFalse(validator.isValid(moneyDrop, null));

        moneyDrop.setHowManyUsers(3);
        moneyDrop.setMoneyToDrop(500);
        assertTrue(validator.isValid(moneyDrop, null));


        moneyDrop.setHowManyUsers(500);
        moneyDrop.setMoneyToDrop(500);
        assertTrue(validator.isValid(moneyDrop, null));

        moneyDrop.setHowManyUsers(500);
        moneyDrop.setMoneyToDrop(499);
        assertFalse(validator.isValid(moneyDrop, null));
    }
}