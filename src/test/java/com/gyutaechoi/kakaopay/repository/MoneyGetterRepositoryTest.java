package com.gyutaechoi.kakaopay.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MoneyGetterRepositoryTest {

    @Autowired
    private MoneyGetterRepository moneyGetterRepository;

    @Test
    void findByMoneyGetteryMoneyDropNoAndUserNo() {
        moneyGetterRepository.findMoneyGetterByMoneyDropNoAndUserNo(1L, 1L);
    }
}