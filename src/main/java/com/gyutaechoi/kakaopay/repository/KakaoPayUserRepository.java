package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.KakaoPayUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KakaoPayUserRepository extends JpaRepository<KakaoPayUser, Long> {

    // 이게 잘되는지 확인..
    @Query("select u from KakaoPayUser u join fetch u.chatRooms chatRoom where u.userNo = :userNo AND chatRoom.chatRoomName = :chatRoomName")
    Optional<KakaoPayUser> getUserAndChatRoomByUserNoAndChatRoomName(@Param("userNo") Long userNo, @Param("chatRoomName") String chatRoomName);
}
