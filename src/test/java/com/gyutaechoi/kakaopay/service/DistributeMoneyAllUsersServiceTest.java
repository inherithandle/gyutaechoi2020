package com.gyutaechoi.kakaopay.service;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistributeMoneyAllUsersServiceTest {

    private DistributeMoneyAllUsersService distributeMoneyAllUsersService = new DistributeMoneyAllUsersService();

    /**
     * 500원을 9명에게 분배해서, 9명이 받는 돈이 뿌리는 돈과 같아야 한다.
     * 어떠한 경우에도 분배된 돈이 뿌린 돈보다 많아서는 안된다.
     */
    @Test
    public void moneyTest() {
        final int NUMBER_OF_TRIALS = 100;
        final int moneyToDrop = 500;
        final int howManyUsers = 20;

        for (int i = 1; i <= NUMBER_OF_TRIALS; i++) {
            List<Integer> integers = distributeMoneyAllUsersService.distributeMoney(moneyToDrop, howManyUsers, new SecureRandom());
            System.out.println(i + "th try: " + integers);

            Integer sum = integers.stream().collect(Collectors.summingInt(Integer::intValue));
            if (sum > moneyToDrop) {
                throw new RuntimeException("[심각]: 분배된 돈이 뿌린 돈보다 많습니다. 당장 고쳐주세요.");
            }

            assertEquals(moneyToDrop, sum);
        }


    }

    /**
     * 극단적인 값 코너케이스 테스트
     * 10원을 9명에게 나눠주기
     */
    @Test
    public void cornerTest() {
        final int NUMBER_OF_TRIALS = 100;
        final int moneyToDrop = 10;
        final int howManyUsers = 9;

        for (int i = 1; i <= NUMBER_OF_TRIALS; i++) {
            List<Integer> integers = distributeMoneyAllUsersService.distributeMoney(moneyToDrop, howManyUsers, new SecureRandom());
            System.out.println(i + "th try: " +integers);

            Integer sum = integers.stream().collect(Collectors.summingInt(Integer::intValue));
            assertEquals(moneyToDrop, sum);

            // 10원을 9명에게 나눠주면, 8명 모두 1원을 받고, 1명만 2원을 받는다.
            assertEquals(1, integers.stream().filter(money -> money == 2).count());
            assertEquals(8, integers.stream().filter(money -> money == 1).count());

        }
    }

    /**
     * 극단적인 값 코너케이스 테스트
     * 21원을 20명에게 나눠주기
     */
    @Test
    public void cornerTest2() {
        final int NUMBER_OF_TRIALS = 100;
        final int moneyToDrop = 21;
        final int howManyUsers = 20;

        for (int i = 1; i <= NUMBER_OF_TRIALS; i++) {
            List<Integer> integers = distributeMoneyAllUsersService.distributeMoney(moneyToDrop, howManyUsers, new SecureRandom());
            System.out.println(i + "th try: " +integers);

            Integer sum = integers.stream().collect(Collectors.summingInt(Integer::intValue));
            assertEquals(moneyToDrop, sum);

            // 10원을 9명에게 나눠주면, 19명 모두 1원을 받고, 1명만 2원을 받는다.
            assertEquals(1, integers.stream().filter(money -> money == 2).count());
            assertEquals(19, integers.stream().filter(money -> money ==1).count());

        }
    }

    @Test
    public void streamCountApiTest() {
        ArrayList<Integer> ints = new ArrayList<>();
        Collections.addAll(ints, 1,2,5,3,3,2,3);

        assertEquals(2, ints.stream().filter(i -> i == 2).count());
        assertEquals(1, ints.stream().filter(i -> i == 1).count());
        assertEquals(3, ints.stream().filter(i -> i == 3).count());
    }
}