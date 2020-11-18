package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.ChatRoom;
import com.gyutaechoi.kakaopay.entity.KakaoPayUser;
import com.gyutaechoi.kakaopay.entity.MoneyDrop;
import com.gyutaechoi.kakaopay.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class MoneyDropRepositoryTest {

    @Autowired
    KakaoPayUserRepository kakaoPayUserRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    MoneyDropRepository moneyDropRepository;

    @Test
    public void 러브레이스가_500원을_채팅방1에_뿌린다() {
        MoneyDrop savedMoneyDrop = userDropsMoneyonChatRoom(1L, 1L, 500);

        assertEquals(1L, savedMoneyDrop.getChatRoom().getChatRoomNo());
        assertEquals(1L, savedMoneyDrop.getDropper().getUserNo());
        System.out.println(savedMoneyDrop.getToken());
        System.out.println(savedMoneyDrop.getMoneyGetExpiredAfter());
        System.out.println(savedMoneyDrop.getViewExpiredAfter());
        // 인서트 쿼리 1번 발생 확인. 조회 쿼리 발생안했음을 확인
        // 분배 로직 필요
    }

    @Test
    public void 일론머스크가_돈을줍는다() {

    }

    private MoneyDrop userDropsMoneyonChatRoom(final long userNo, final long chatRoomNo, final int moneyToDrop) {
        final KakaoPayUser lovelace = kakaoPayUserRepository.getOne(1L);
        final ChatRoom firstChatRoom = chatRoomRepository.getOne(1L);
        final LocalDateTime now = LocalDateTime.now();

        MoneyDrop moneyDrop = new MoneyDrop();
        moneyDrop.setDropper(lovelace);
        moneyDrop.setChatRoom(firstChatRoom);
        moneyDrop.setToken(RandomUtil.generateRandomString(3));
        moneyDrop.setFirstBalance(moneyToDrop);
        moneyDrop.setCurrentBalance(moneyDrop.getFirstBalance());
        moneyDrop.setHowManyUsers(3);
        moneyDrop.setMoneyGetExpiredAfter(now.plus(10L, ChronoUnit.MINUTES));
        moneyDrop.setViewExpiredAfter(now.plus(7L, ChronoUnit.DAYS));

        return moneyDropRepository.save(moneyDrop);
    }



}