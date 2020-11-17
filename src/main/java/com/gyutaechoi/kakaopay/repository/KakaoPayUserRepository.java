package com.gyutaechoi.kakaopay.repository;

import com.gyutaechoi.kakaopay.entity.KakaoPayUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KakaoPayUserRepository extends JpaRepository<KakaoPayUser, Long> {

    @Query("select distinct u from KakaoPayUser u left join fetch u.userChatRooms chatRoom inner join fetch chatRoom.kakaoPayUser ORDER BY u.userNo")
    List<KakaoPayUser> selectAll();


}
