package com.gyutaechoi.kakaopay.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Immutable
@Table(name = "chatRoom")
public class ChatRoomView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomNo;

    @Column(nullable = false, length = 50)
    private String chatRoomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorUserNo")
    private KakaoPayUserView creator;

    @ManyToMany(mappedBy = "chatRooms")
    private List<KakaoPayUserView> kakaoPayUsers = new ArrayList<>(); // 채팅룸 유저들.

    public Long getChatRoomNo() {
        return chatRoomNo;
    }

    public void setChatRoomNo(Long chatRoomNo) {
        this.chatRoomNo = chatRoomNo;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public KakaoPayUserView getCreator() {
        return creator;
    }

    public void setCreator(KakaoPayUserView creator) {
        this.creator = creator;
    }

    public List<KakaoPayUserView> getKakaoPayUsers() {
        return kakaoPayUsers;
    }

    public void setKakaoPayUsers(List<KakaoPayUserView> kakaoPayUsers) {
        this.kakaoPayUsers = kakaoPayUsers;
    }
}
