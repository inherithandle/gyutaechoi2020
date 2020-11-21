package com.gyutaechoi.kakaopay.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = MoneyValidator.class)
public @interface MoneyShouldBeGreaterThanUsers {

    String message() default "인원수 보다 많은 금액을 입력하세요.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
