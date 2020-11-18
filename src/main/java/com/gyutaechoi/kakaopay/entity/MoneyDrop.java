package com.gyutaechoi.kakaopay.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MoneyDrop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moneyDropNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dropperUserNo")
    private KakaoPayUser dropper; // 돈을 뿌린 사람.

    @OneToMany(mappedBy = "moneyDrop")
    private List<MoneyGetter> moneyGetters = new ArrayList<>(); // 뿌린 금액을  받은 사람들

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @NaturalId
    @Column(length = 3, nullable = false)
    private String token;

    @Column(nullable = false)
    private Integer howManyPeople; // 뿌린 금액을 받을 수 있는 사람

    @Column(nullable = false)
    private Integer firstBalance; // 최초로 설정한 뿌릴 금액

    @Column(nullable = false)
    private Integer currentBalance; // 남은 금액

    LocalDateTime viewExpiredAfter;

    LocalDateTime moneyGetExpiredAfter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyDrop moneyDrop = (MoneyDrop) o;

        if (!getMoneyDropNo().equals(moneyDrop.getMoneyDropNo())) return false;
        return getToken().equals(moneyDrop.getToken());
    }

    @Override
    public int hashCode() {
        int result = getMoneyDropNo().hashCode();
        result = 31 * result + getToken().hashCode();
        return result;
    }

    public Long getMoneyDropNo() {
        return moneyDropNo;
    }

    public void setMoneyDropNo(Long moneyDropNo) {
        this.moneyDropNo = moneyDropNo;
    }

    public KakaoPayUser getDropper() {
        return dropper;
    }

    public void setDropper(KakaoPayUser dropper) {
        this.dropper = dropper;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getFirstBalance() {
        return firstBalance;
    }

    public void setFirstBalance(Integer firstBalance) {
        this.firstBalance = firstBalance;
    }

    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public LocalDateTime getViewExpiredAfter() {
        return viewExpiredAfter;
    }

    public void setViewExpiredAfter(LocalDateTime viewExpiredAfter) {
        this.viewExpiredAfter = viewExpiredAfter;
    }

    public LocalDateTime getMoneyGetExpiredAfter() {
        return moneyGetExpiredAfter;
    }

    public void setMoneyGetExpiredAfter(LocalDateTime moneyGetExpiredAfter) {
        this.moneyGetExpiredAfter = moneyGetExpiredAfter;
    }

    public List<MoneyGetter> getMoneyGetters() {
        return moneyGetters;
    }

    public void setMoneyGetters(List<MoneyGetter> moneyGetters) {
        this.moneyGetters = moneyGetters;
    }

    public Integer getHowManyPeople() {
        return howManyPeople;
    }

    public void setHowManyUsers(Integer howManyPeople) {
        this.howManyPeople = howManyPeople;
    }
}
