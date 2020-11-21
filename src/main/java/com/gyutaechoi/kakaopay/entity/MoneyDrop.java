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
    private List<MoneyGetter> moneyGetters = new ArrayList<>(); // 뿌린 금액을 받은 사람들

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @NaturalId
    @Column(length = 3, nullable = false)
    private String token;

    @Column(nullable = false)
    private Integer howManyUsers; // 뿌린 금액을 받을 수 있는 사람 수

    @Column(nullable = false)
    private Integer numOfMoneyGetters; // 뿌린 돈을 주운 사람 수

    @Convert(converter = IntegerListConverter.class)
    @Column(nullable = false, length = 2000)
    private List<Integer> distribution; // 돈 분배

    @Column(nullable = false)
    private Integer firstBalance; // 최초로 설정한 뿌릴 금액

    @Column(nullable = false)
    private Integer currentBalance; // 남은 금액

    private LocalDateTime createdDateTime;

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

    public Integer getHowManyUsers() {
        return howManyUsers;
    }

    public void setHowManyUsers(Integer howManyUsers) {
        this.howManyUsers = howManyUsers;
    }

    public Integer getNumOfMoneyGetters() {
        return numOfMoneyGetters;
    }

    public void setNumOfMoneyGetters(Integer numOfMoneyGetters) {
        this.numOfMoneyGetters = numOfMoneyGetters;
    }

    public List<Integer> getDistribution() {
        return distribution;
    }

    public void setDistribution(List<Integer> distribution) {
        this.distribution = distribution;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public void incrementNumOfMoneyGetters() {
        this.numOfMoneyGetters = numOfMoneyGetters + 1;
    }

    public void decrementCurrentBalanceBy(int moneyToGive) {
        this.currentBalance -= moneyToGive;
    }
}
