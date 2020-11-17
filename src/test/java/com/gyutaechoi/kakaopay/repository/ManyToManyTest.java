package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.ChatRoom;
import com.gyutaechoi.kakaopay.entity.KakaoPayUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ManyToManyTest {

    @Autowired
    private KakaoPayUserRepository kakaoPayUserRepository;

    @Test
    public void manyToManyMappingTest() {
        KakaoPayUser lovelace = kakaoPayUserRepository.findById(1L).orElseThrow(() -> new RuntimeException(""));
        List<ChatRoom> chatRooms = lovelace.getChatRooms();

        assertEquals(3, chatRooms.size());
        assertEquals("러브레이스 외 9명", chatRooms.get(0).getChatRoomName());
        assertEquals("러브레이스 외 2명", chatRooms.get(1).getChatRoomName());
        assertEquals("채팅방3", chatRooms.get(2).getChatRoomName());

        List<KakaoPayUser> usersInSecondChatRoom = lovelace.getChatRooms().get(1).getKakaoPayUsers();
        assertEquals(3, usersInSecondChatRoom.size());
        assertEquals("Ada Lovelace", usersInSecondChatRoom.get(0).getUsername());
        assertEquals("Elon Musk", usersInSecondChatRoom.get(1).getUsername());
        assertEquals("Larry Page", usersInSecondChatRoom.get(2).getUsername());
    }

    @Test
    public void fetchJoinTest() {

    }
}
