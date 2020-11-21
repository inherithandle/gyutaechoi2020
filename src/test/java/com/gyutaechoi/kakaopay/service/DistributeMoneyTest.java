package com.gyutaechoi.kakaopay.service;

import com.gyutaechoi.kakaopay.entity.MoneyDrop;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * 돈 분배 로직 단위 테스
 */
public class DistributeMoneyTest {

    private MoneyDropService moneyDropService = new MoneyDropService();

    @Test
    public void distributeMoneyLogicTest() {
        final int NUMER_OF_TRIALS = 1000;
        final int howManyUsers = 3;
        final int firstBalance = 500;
        MoneyDrop moneyDrop = new MoneyDrop();
        moneyDrop.setFirstBalance(firstBalance);
        moneyDrop.setHowManyUsers(howManyUsers);
        Random r = new SecureRandom();
        int perfectDistributionCnt = 0;

        for (int i = 1; i <= NUMER_OF_TRIALS; i++) {
            List<Integer> integers = moneyDropService.distributeMoney(moneyDrop, r);
            System.out.println(i + "th try : " + integers);
            Integer sum = integers.stream().collect(Collectors.summingInt(Integer::intValue));
            if (sum == firstBalance) {
                perfectDistributionCnt++;
            }
            assertThat(integers.size(), greaterThan(1));
            assertThat(sum, lessThanOrEqualTo(firstBalance));

        }

        // 500원이 모든사람에게 분배되었는지 확인.
        // ex) [100, 200, 200]은 완전분배
        // ex) [50, 20, 200]은 완전분배가 아님
        System.out.println("완전 분배 횟수 : " + perfectDistributionCnt);
    }

    /**
     * 뿌릴 인원과, 뿌릴 금액의 차이가 별로 나지 않을 떄
     * 뿌릴 인원 3, 뿌릴 금액 4
     */
    @Test
    public void randomTest2() {
        final int howManyUsers = 500;
        final int firstBalance = 501;
        MoneyDrop moneyDrop = new MoneyDrop();
        moneyDrop.setFirstBalance(firstBalance);
        moneyDrop.setHowManyUsers(howManyUsers);
        Random r = new SecureRandom();

        List<Integer> integers = moneyDropService.distributeMoney(moneyDrop, r);
        assertThat(integers.size(), greaterThan(0));
        System.out.println(integers);
    }

    /**
     * 지정된 금액보다 더 많은 금액이 분배되지 않음을 체크
     * 500원을 뿌리려고 했는데 600원이 뿌려지지 않음을 확인한다.
     */
    @Test
    public void distributeTooMuchMoneyToTooManyUsers() {
        final int howManyUsers = 1500;
        final int money = 2_000_000_000;
        final int NUMBER_OF_TRIALS = 1000;

        for (int i = 1; i <= NUMBER_OF_TRIALS; i++) {
            MoneyDrop moneyDrop = new MoneyDrop();
            moneyDrop.setFirstBalance(money);
            moneyDrop.setHowManyUsers(howManyUsers);
            Random r = new SecureRandom();

            List<Integer> integers = moneyDropService.distributeMoney(moneyDrop, r);
            Integer sum = integers.stream().collect(Collectors.summingInt(Integer::intValue));

            if (sum > money) {
                throw new RuntimeException("[심각] 유저가 지정한 금액보다 많은 돈이 분배되었다!");
            }
            assertThat(integers.size(), greaterThan(0));
            System.out.println(i + "th try : " + integers);
        }
    }

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
