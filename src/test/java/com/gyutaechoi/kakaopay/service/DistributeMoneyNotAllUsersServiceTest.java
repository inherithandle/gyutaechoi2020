package com.gyutaechoi.kakaopay.service;

import com.gyutaechoi.kakaopay.entity.MoneyDrop;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

class DistributeMoneyNotAllUsersServiceTest {

    private DistributeMoneyNotAllUsersService service = new DistributeMoneyNotAllUsersService();

    /**
     * 1000원을 500명에게 뿌리기
     */
    @Test
    public void randomTest2() {
        final int howManyUsers = 500;
        final int firstBalance = 1000;
        Random r = new SecureRandom();

        List<Integer> integers = service.distributeMoney(firstBalance, howManyUsers, r);
        assertThat(integers.size(), greaterThan(0));
        System.out.println(integers);
    }

    /**
     * 20억을 1500명에게 분배 테스트
     * 지정된 금액보다 더 많은 금액이 분배되지 않음을 체크
     * 20억을을 뿌리려고 했는데 20억을 초과해서 뿌려지지 않음을 확인한다.
     */
    @Test
    public void distributeTooMuchMoneyToTooManyUsers() {
        final int howManyUsers = 1500;
        final int money = 2_000_000_000;
        final int NUMBER_OF_TRIALS = 10;

        for (int i = 1; i <= NUMBER_OF_TRIALS; i++) {
            MoneyDrop moneyDrop = new MoneyDrop();
            moneyDrop.setFirstBalance(money);
            moneyDrop.setHowManyUsers(howManyUsers);
            Random r = new SecureRandom();

            List<Integer> integers = service.distributeMoney(money, howManyUsers, r);
            Integer sum = integers.stream().collect(Collectors.summingInt(Integer::intValue));

            if (sum > money) {
                throw new RuntimeException("[심각] 유저가 지정한 금액보다 많은 돈이 분배되었다!");
            }
            assertThat(integers.size(), greaterThan(0));
            System.out.println(i + "th try : " + integers);
        }
    }

}