package com.gyutaechoi.kakaopay.dto;

/**
 * 뿌리기 API 호출시 사용되는 클래스
 */
public class MoneyDropRequest {

    private int moneyToDrop; // 뿌릴 금액
    private int howManyUsers; // 돈을 받을수 있는 최대 인원

    public MoneyDropRequest() {
    }

    public MoneyDropRequest(int moneyToDrop, int howManyUsers) {
        this.moneyToDrop = moneyToDrop;
        this.howManyUsers = howManyUsers;
    }

    public int getMoneyToDrop() {
        return moneyToDrop;
    }

    public void setMoneyToDrop(int moneyToDrop) {
        this.moneyToDrop = moneyToDrop;
    }

    public int getHowManyUsers() {
        return howManyUsers;
    }

    public void setHowManyUsers(int howManyUsers) {
        this.howManyUsers = howManyUsers;
    }
}
