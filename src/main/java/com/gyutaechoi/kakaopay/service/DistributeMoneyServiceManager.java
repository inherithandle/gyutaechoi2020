package com.gyutaechoi.kakaopay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * MoneyDropService가 사용하는 돈 분배 로직 구현체
 */
@Service
public class DistributeMoneyServiceManager implements DistributeMoneyService {

    private DistributeMoneyAllUsersService distributeMoneyAllUsersService;
    private DistributeMoneyNotAllUsersService distributeMoneyNotAllUsersService;

    private static final int TOO_MANY_USERS = 20;

    /**
     * 채팅방 유저가 21명 이상이면 distributeMoneyNotAllUsersService 사용하여 돈을 분배
     * 채팅방 유저가 20명 이하이면 distributeMoneyAllUsersService 사용하여 돈을 분배
     */
    @Override
    public List<Integer> distributeMoney(int moneyToDrop, int howManyUsers, Random r) {
        if (howManyUsers > TOO_MANY_USERS) {
            return distributeMoneyNotAllUsersService.distributeMoney(moneyToDrop, howManyUsers, r);
        } else {
            return distributeMoneyAllUsersService.distributeMoney(moneyToDrop, howManyUsers, r);
        }
    }

    @Autowired
    public void setDistributeMoneyAllUsersService(DistributeMoneyAllUsersService distributeMoneyAllUsersService) {
        this.distributeMoneyAllUsersService = distributeMoneyAllUsersService;
    }

    @Autowired
    public void setDistributeMoneyNotAllUsersService(DistributeMoneyNotAllUsersService distributeMoneyNotAllUsersService) {
        this.distributeMoneyNotAllUsersService = distributeMoneyNotAllUsersService;
    }
}
