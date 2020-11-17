package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select c from ChatRoom c inner join fetch c.userChatRooms userChatRoom inner join fetch userChatRoom.kakaoPayUser u where u.userNo = :userNo and c.chatRoomName = :chatRoomName")
    Optional<ChatRoom> getUserAndChatRoomUserNoAndChatRoomName(long userNo, String chatRoomName);
}
