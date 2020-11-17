package com.gyutaechoi.kakaopay.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
public class KakaoPayUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @NaturalId
    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(length = 15)
    private String username;

    @Column(length = 25)
    private String nickname;

    @Column(length = 80)
    private String password;

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KakaoPayUser that = (KakaoPayUser) o;

        if (getUserNo() != null ? !getUserNo().equals(that.getUserNo()) : that.getUserNo() != null) return false;
        return getUserId() != null ? getUserId().equals(that.getUserId()) : that.getUserId() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserNo() != null ? getUserNo().hashCode() : 0;
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        return result;
    }
}
