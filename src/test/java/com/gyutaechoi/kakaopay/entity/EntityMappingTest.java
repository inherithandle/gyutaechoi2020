package com.gyutaechoi.kakaopay.entity;

import com.gyutaechoi.kakaopay.repository.ChatRoomRepository;
import com.gyutaechoi.kakaopay.repository.KakaoPayUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class EntityMappingTest {

    /**
     * @Entity 매핑이 제대로 되었는지 확인
     */
    @Test
    public void entityMappingTest() {

    }

    @Autowired
    private KakaoPayUserRepository kakaoPayUserRepository;

    /**
     * data.sql 제대로 로딩했는지 확인
     */
    @Test
    public void dataSqlTest() {
        KakaoPayUser lovelace = kakaoPayUserRepository.findById(1L).orElseThrow(() -> new RuntimeException(""));
        List<KakaoPayUser> allUsers = kakaoPayUserRepository.findAll();

        assertEquals(1L, lovelace.getUserNo());
        assertEquals("Ada Lovelace", lovelace.getUsername());
        assertEquals("원더코딩우먼", lovelace.getNickname());
        assertEquals("user1", lovelace.getUserId());
        assertEquals(10, allUsers.size());
    }

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    public void chatroomTest() {
        ChatRoom chatRoom = chatRoomRepository.findById(1L).orElseThrow(() -> new RuntimeException(""));
        assertEquals(1, chatRoom.getChatRoomNo());
    }
}
