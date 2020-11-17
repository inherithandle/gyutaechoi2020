package com.gyutaechoi.kakaopay.entity;

import javax.persistence.*;

@Entity
public class UserChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userChatRoomNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo")
    private KakaoPayUser kakaoPayUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoomNo")
    private ChatRoom chatRoom;

    public Long getUserChatRoomNo() {
        return userChatRoomNo;
    }

    public void setUserChatRoomNo(Long userChatRoomNo) {
        this.userChatRoomNo = userChatRoomNo;
    }

    public KakaoPayUser getKakaoPayUser() {
        return kakaoPayUser;
    }

    public void setKakaoPayUser(KakaoPayUser kakaoPayUser) {
        this.kakaoPayUser = kakaoPayUser;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
