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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        moneyDrop.setNumOfMoneyGetters(0);
        moneyDrop.setMoneyGetExpiredAfter(now.plus(10L, ChronoUnit.MINUTES));
        moneyDrop.setViewExpiredAfter(now.plus(7L, ChronoUnit.DAYS));

        List<Integer> distribution = new ArrayList<>(3);
        distribution.add(100);
        distribution.add(300);
        distribution.add(200);
        moneyDrop.setDistribution(distribution);

        return moneyDropRepository.save(moneyDrop);
    }

    @Test
    public void converterTest() {
        userDropsMoneyonChatRoom(1L, 1L, 500);
        MoneyDrop moneyDrop = moneyDropRepository.findById(1L).orElseThrow(() -> new RuntimeException());

        assertEquals(100, moneyDrop.getDistribution().get(0));
        assertEquals(300, moneyDrop.getDistribution().get(1));
        assertEquals(200, moneyDrop.getDistribution().get(2));
    }

    @Test
    public void findMoneyDropByToken() {
        Optional<MoneyDrop> abC = moneyDropRepository.findMoneyDropByToken("AbC");
        if (abC.isPresent()) {
            throw new RuntimeException("돈을 뿌린적 없으니 데이터가 찾아지면 익셉션 던진다.");
        }
        // select 쿼리 확인
    }



}