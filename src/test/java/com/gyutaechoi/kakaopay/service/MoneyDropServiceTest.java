package com.gyutaechoi.kakaopay.service;

import com.gyutaechoi.kakaopay.dto.MoneyDropResponse;
import com.gyutaechoi.kakaopay.entity.MoneyGetter;
import com.gyutaechoi.kakaopay.repository.MoneyGetterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 돈뿌리기 통합 테스트
 */
@SpringBootTest
class MoneyDropServiceTest {

    @Autowired
    private MoneyGetterRepository moneyGetterRepository;

    @Autowired
    private MoneyDropService moneyDropService;

    @Test
    public void 러브레이스가_500원을_3명에게_뿌린다() {
        final long lovelace = 1L;
        String token = moneyDropService.addMoneyDrop(lovelace, "러브레이스 외 9명", 500, 3);

        assertEquals(3, token.length());
        System.out.println("돈뿌리기 토큰 : " + token);

        // 조회 쿼리 + 인서트 쿼리
        // 조회 쿼리는 유저가 정말로 채팅방에 존재하는지 확인용.
        // 인서트는 돈뿌리기 데이터 추가
    }

    @Test
    public void 빌게이츠는_러브레이스가_뿌린돈을_줍는다() {
        final long lovelace = 1L;
        final long gates = 2L;
        String token = moneyDropService.addMoneyDrop(lovelace, "러브레이스 외 9명", 500, 3);

        final Integer moneyToReceive = moneyDropService.tryToGetMoneyFromMoneyDrop(gates, "러브레이스 외 9명", token);

        System.out.println("받은 돈 : " + moneyToReceive);

        final MoneyGetter moneyGetter = moneyGetterRepository.findByUserNo(gates).orElseThrow(() -> new RuntimeException());
        assertEquals(moneyToReceive, moneyGetter.getAmount());
    }

    @Test
    public void 러브레이스가_뿌린돈을_러브레이스가_주울_수_없다() {
        final long lovelace = 1L;

        String token = moneyDropService.addMoneyDrop(lovelace, "러브레이스 외 9명", 500, 3);

        assertThrows(RuntimeException.class, () -> {
            moneyDropService.tryToGetMoneyFromMoneyDrop(lovelace, "러브레이스 외 9명", token);
        });
    }

    @Test
    public void 뿌린돈_조회() {
        final long lovelace = 1L;
        final long gates = 2L;
        final long musk = 3L;
        final String chatRoomName = "러브레이스 외 9명";

        String token = moneyDropService.addMoneyDrop(lovelace, chatRoomName, 500, 3);

        moneyDropService.tryToGetMoneyFromMoneyDrop(gates, chatRoomName, token);
        moneyDropService.tryToGetMoneyFromMoneyDrop(musk, chatRoomName, token);

        MoneyDropResponse response = moneyDropService.getMoneyDrop(lovelace, token);
        assertEquals(2, response.getMoneyGetters().size());
        assertEquals(500, response.getMoneyToDrop());
        assertEquals("윈도우맨", response.getMoneyGetters().get(0).getNickname());
        assertEquals("테슬라맨", response.getMoneyGetters().get(1).getNickname());
    }
}