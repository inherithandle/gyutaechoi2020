package com.gyutaechoi.kakaopay.validator;

import com.gyutaechoi.kakaopay.dto.MoneyDropRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MoneyValidator implements ConstraintValidator<MoneyShouldBeGreaterThanUsers, MoneyDropRequest> {

    @Override
    public void initialize(MoneyShouldBeGreaterThanUsers constraintAnnotation) {
    }

    @Override
    public boolean isValid(MoneyDropRequest value, ConstraintValidatorContext context) {
        final int howManyUsers = value.getHowManyUsers();
        final int moneyToDrop = value.getMoneyToDrop();
        return moneyToDrop >= howManyUsers;
    }
}
