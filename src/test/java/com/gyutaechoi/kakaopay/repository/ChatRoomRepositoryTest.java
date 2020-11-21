package com.gyutaechoi.kakaopay.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
class ChatRoomRepositoryTest {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Test
    void countQueryTest() {
        final String chatRoomName = "chatroom_id1";
        final String chatRoomName2 = "chatroom_id2";
        final String chatRoomName3 = "chatroom_id3";

        Long cntChat1 = chatRoomRepository.countNumOfUsersByChatRoomName(chatRoomName);
        assertThat(cntChat1, equalTo(10L));

        Long cntChat2 = chatRoomRepository.countNumOfUsersByChatRoomName(chatRoomName2);
        assertThat(cntChat2, equalTo(3L));

        Long cntChat3 = chatRoomRepository.countNumOfUsersByChatRoomName(chatRoomName3);
        assertThat(cntChat3, equalTo(3L));
    }
}