package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.ChatRoom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class KakaoPayUserRepositoryTest {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Test
    void findUserAndChatRoomByUserNoAndChatRoomName() {
        // 채팅방3에서 귀도 반 로썸, 피터 틸, 에이다 러브레이스 대화중이라고 가정 (data.sql)
        // 피터틸이 속한 채팅방3을 찾는다.
        final long X_USER_ID = 8L; // 귀도 반 로썸의 user_no
        final String X_ROOM_ID = "채팅방3";

        ChatRoom chatRoom = chatRoomRepository.getUserAndChatRoomUserNoAndChatRoomName(X_USER_ID, X_ROOM_ID)
                .orElseThrow(() -> new RuntimeException());

        assertEquals(X_ROOM_ID, chatRoom.getChatRoomName());
    }

    @Test
    public void chatRoomNotFound() {
        // 빌게이츠는 채팅방3에 없기 때문에 조회 불가.
        final long X_USER_ID = 2L; // 빌게이츠
        final String X_ROOM_ID = "채팅방3";

        assertThrows(RuntimeException.class, () -> {
            chatRoomRepository.getUserAndChatRoomUserNoAndChatRoomName(X_USER_ID, X_ROOM_ID)
                    .orElseThrow(() -> new RuntimeException());
        });
    }
}