package com.gyutaechoi.kakaopay.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomNo;

    @Column(nullable = false, length = 50)
    private String chatRoomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorUserNo")
    private KakaoPayUser creator;

    @OneToMany(mappedBy = "chatRoom")
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

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

    public KakaoPayUser getCreator() {
        return creator;
    }

    public void setCreator(KakaoPayUser creator) {
        this.creator = creator;
    }

    public List<UserChatRoom> getUserChatRooms() {
        return userChatRooms;
    }

    public void setUserChatRooms(List<UserChatRoom> userChatRooms) {
        this.userChatRooms = userChatRooms;
    }
}
