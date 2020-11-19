package com.gyutaechoi.kakaopay.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MoneyDropResponse {

    LocalDateTime createdDateTime;

    private int moneyToDrop; // 뿌린 금액

    private int droppedMoney; // 받기 완료된 금액

    List<MoneyGetterResponse> moneyGetters;

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public int getMoneyToDrop() {
        return moneyToDrop;
    }

    public void setMoneyToDrop(int moneyToDrop) {
        this.moneyToDrop = moneyToDrop;
    }

    public int getDroppedMoney() {
        return droppedMoney;
    }

    public void setDroppedMoney(int droppedMoney) {
        this.droppedMoney = droppedMoney;
    }

    public List<MoneyGetterResponse> getMoneyGetters() {
        return moneyGetters;
    }

    public void setMoneyGetters(List<MoneyGetterResponse> moneyGetters) {
        this.moneyGetters = moneyGetters;
    }
}
