package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.KakaoPayUserView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class KakaoPayUserViewRepositoryTest {

    @Autowired
    private KakaoPayUserViewRepository kakaoPayUserViewRepository;

    @Test
    public void ManyToMany_유저와채팅방_단건조회() {
        // chatroom_id3에서 귀도 반 로썸(8), 피터 틸(4), 에이다 러브레이스(1) 대화중이라고 가정 (data.sql 33번째 줄)
        // 피터틸이 속한 chatroom_id3을 찾는다.
        final long X_USER_ID = 8L; // 귀도 반 로썸의 user_no
        final String X_ROOM_ID = "chatroom_id3";

        KakaoPayUserView kakaoPayUser = kakaoPayUserViewRepository.getUserAndChatRoomUserNoAndChatRoomName(X_USER_ID, X_ROOM_ID)
                .orElseThrow(() -> new RuntimeException());

        assertEquals("파이썬맨", kakaoPayUser.getNickname());
        assertEquals(X_ROOM_ID, kakaoPayUser.getChatRooms().get(0).getChatRoomName());
    }
}
