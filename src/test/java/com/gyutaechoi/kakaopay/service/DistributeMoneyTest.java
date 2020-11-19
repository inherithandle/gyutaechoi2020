package com.gyutaechoi.kakaopay.service;

import com.gyutaechoi.kakaopay.entity.MoneyDrop;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

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
            assertEquals(howManyUsers, integers.size());
            assertThat(sum, lessThanOrEqualTo(firstBalance));

        }

        // 500원이 모든사람에게 분배되었는지 확인.
        // ex) [100, 200, 200]은 완전분배
        // ex) [50, 20, 200]은 완전분배가 아님
        System.out.println("완전 분배 횟수 : " + perfectDistributionCnt);
    }

    /**
     * 분배 로직은 돈 받을 사람에게 1원 이상 주기로 결정했습니다.
     * 500원 뿌리기, 3명 설정 했는데.
     * 난수 생성기가 첫번째에 500원을 뽑은 경우 분배 로직을 재시도하는 로직을 타야 한다.
     */
    @Test
    public void generateAndRetryTest() {
        final int howManyUsers = 3;
        final int firstBalance = 500;
        MoneyDrop moneyDrop = new MoneyDrop();
        moneyDrop.setFirstBalance(firstBalance);
        moneyDrop.setHowManyUsers(howManyUsers);

        Random r = Mockito.mock(Random.class);
        given(r.nextInt(499)).willReturn(499); // 난수 생성기가 500원을 리턴한다고 가정한다.
        given(r.nextInt(499)).willReturn(99); // 난수 생성기가 100원을 리턴한다고 가정한다.
        given(r.nextInt(399)).willReturn(199); // 난수 생성기가 200원을 리턴한다고 가정한다.
        given(r.nextInt(199)).willReturn(50); // 난수 생성기가 51원을 리턴한다고 가정한다.

        List<Integer> integers = moneyDropService.distributeMoney(moneyDrop, r);
        assertEquals(3, integers.size());
        assertThat(integers.toArray(), is(new int[] {100, 200, 51}));
    }

    /**
     * 분배 로직은 돈 받을 사람에게 1원 이상 주기로 결정했습니다.
     * 500원 뿌리기, 3명 설정 했는데.
     * 첫번째에서 300원, 두번째에서 200원 걸린경우, 3번째 사람에게 돈을 줄 수 없기 때문
     * 분배로직을 재시도해야 합니다.
     */
    @Test
    public void generateAndRetryTest2() {
        final int howManyUsers = 3;
        final int firstBalance = 500;
        MoneyDrop moneyDrop = new MoneyDrop();
        moneyDrop.setFirstBalance(firstBalance);
        moneyDrop.setHowManyUsers(howManyUsers);

        Random r = Mockito.mock(Random.class);
        given(r.nextInt(499)).willReturn(299); // 난수 생성기가 300원을 리턴한다고 가정한다.
        given(r.nextInt(199)).willReturn(199); // 난수 생성기가 200원을 리턴한다고 가정한다.

        given(r.nextInt(499)).willReturn(100); // 난수 생성기가 101원을 리턴한다고 가정한다.
        given(r.nextInt(398)).willReturn(50); // 난수 생성기가 51원을 리턴한다고 가정한다.
        given(r.nextInt(347)).willReturn(35); // 난수 생성기가 36원을 리턴한다고 가정한다.


        List<Integer> integers = moneyDropService.distributeMoney(moneyDrop, r);
        assertEquals(3, integers.size());
        assertThat(integers.toArray(), is(new int[] {101, 51, 36}));
    }

}
