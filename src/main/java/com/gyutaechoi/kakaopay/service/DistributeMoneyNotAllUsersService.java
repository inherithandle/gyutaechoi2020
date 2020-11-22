package com.gyutaechoi.kakaopay.service;

import com.gyutaechoi.kakaopay.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 돈 받을 인원이 21명 이상 일 때, 이 구현체를 사용한다.
 */
@Service
public class DistributeMoneyNotAllUsersService implements DistributeMoneyService {

    @Override
    public List<Integer> distributeMoney(int moneyToDrop, int howManyUsers, Random r) {
        int currentBalance = moneyToDrop;

        ArrayList<Integer> result = new ArrayList<>();
        int money;
        for (int i = 0; i < howManyUsers; i++) {
            if (currentBalance == 0) {
                result.add(0);
                break;
            }
            money = RandomUtil.generateRandomInteger(r, currentBalance);
            currentBalance -= money;
            result.add(money);
        }

        return result;
    }
}
