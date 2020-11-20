package com.gyutaechoi.kakaopay.dto;

public class MoneyGetterPostResponse {

    private int receivedMoney;

    public MoneyGetterPostResponse(int receivedMoney) {
        this.receivedMoney = receivedMoney;
    }

    public int getReceivedMoney() {
        return receivedMoney;
    }

    public void setReceivedMoney(int receivedMoney) {
        this.receivedMoney = receivedMoney;
    }
}
