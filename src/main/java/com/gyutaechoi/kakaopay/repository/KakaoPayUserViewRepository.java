package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.KakaoPayUserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KakaoPayUserViewRepository extends JpaRepository<KakaoPayUserView, Long> {

    @Query("select u from KakaoPayUserView u left join fetch u.chatRooms c where u.userNo = :userNo and c.chatRoomName = :chatRoomName")
    Optional<KakaoPayUserView> getUserAndChatRoomUserNoAndChatRoomName(long userNo, String chatRoomName);
}
