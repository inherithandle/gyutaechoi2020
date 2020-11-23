package com.gyutaechoi.kakaopay.service;

import com.gyutaechoi.kakaopay.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 돈 받는 인원이 20명 이하 일 때, 이 구현체를 사용한다.
 * 500원을 5명에게 뿌릴 때, 모든 사람이 1원 이상 받을 수 있게 해준다.
 */
@Service
public class DistributeMoneyAllUsersService implements DistributeMoneyService {

    @Override
    public List<Integer> distributeMoney(int moneyToDrop, int howManyUsers, Random r) {
        int currentBalance = moneyToDrop;
        List<Integer> result = new ArrayList<>();
        int money;
        int i = 0;
        while (i < howManyUsers - 1) {
            money = RandomUtil.generateRandomIntegerBetween1AndMax(r, currentBalance);
            currentBalance -= money;
            result.add(money);

            if (currentBalance == 0) {
                // 5명에게 분배하려다 3명째에서 모든 돈을 다 분배한경우, 가장 많이 분배받은 유저의 금액을 절반을 가져와서 다시 분배한다.
                int max = result.get(0);
                int maxIndex = 0;
                for (int j = 1; j < result.size(); j++) {
                    if (result.get(j) > max) {
                        max = result.get(j);
                        maxIndex = j;
                    }
                }
                final int half = max / 2;
                currentBalance = half;
                result.set(maxIndex, result.get(maxIndex) - half);
            }
            i++;
        }

        // 마지막 사람에게 분배할 돈이 없으면 가장 많이 받은 사람의 절반을 분배한다.
        if (currentBalance == 0) {
            int max = result.get(0);
            int maxIndex = 0;
            for (int j = 1; j < result.size(); j++) {
                if (result.get(j) > max) {
                    max = result.get(j);
                    maxIndex = j;
                }
            }

            final int half = max / 2;
            result.set(maxIndex, result.get(maxIndex) - half);
            result.add(half);
        } else {
            // 마지막 사람에게 나머지 돈을 분배한다.
            result.add(currentBalance);
        }
        return result;
    }
}
