package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.KakaoPayUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class KakaoPayUserRepositoryTest {

    @Autowired
    KakaoPayUserRepository kakaoPayUserRepository;

    @Test
    void findUserAndChatRoomByUserNoAndChatRoomName() {
        final long X_USER_ID = 1L;
        final String X_ROOM_ID = "러브레이스 외 9명";
        KakaoPayUser user = kakaoPayUserRepository.getUserAndChatRoomByUserNoAndChatRoomName(X_USER_ID, X_ROOM_ID)
                .orElseThrow(() -> new RuntimeException());


        assertEquals(X_ROOM_ID, user.getChatRooms().get(0).getChatRoomName());
        assertEquals(1, user.getChatRooms().size());
    }
}