package com.gyutaechoi.kakaopay.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.SecureRandom;
import java.util.Random;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DistributeMoneyServiceManagerTest {

    @Mock
    DistributeMoneyNotAllUsersService distributeMoneyNotAllUsersService;

    @Mock
    DistributeMoneyAllUsersService distributeMoneyAllUsersService;

    @InjectMocks
    DistributeMoneyServiceManager distributeMoneyServiceManager = new DistributeMoneyServiceManager();

    /**
     * 20명 이하일 때, distributeMoneyAllUsersService를 호출해야 한다.
     */
    @Test
    void distributeAllUsersMoney() {
        Random r = new SecureRandom();
        distributeMoneyServiceManager.distributeMoney(100, 3, r);
        verify(distributeMoneyAllUsersService).distributeMoney(100, 3, r);

        distributeMoneyServiceManager.distributeMoney(100, 20, r);
        verify(distributeMoneyAllUsersService).distributeMoney(100, 20, r);
    }

    /**
     * 21명 이상일 때, distributeMoneyNotAllUsersService를 호출해야 한다.
     */
    @Test
    public void distributeNotAllUsersMoney() {
        Random r = new SecureRandom();
        distributeMoneyServiceManager.distributeMoney(100, 21, r);
        verify(distributeMoneyNotAllUsersService).distributeMoney(100, 21, r);
    }
}