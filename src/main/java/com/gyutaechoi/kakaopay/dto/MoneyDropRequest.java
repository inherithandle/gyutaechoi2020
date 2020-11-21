package com.gyutaechoi.kakaopay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 뿌리기 API 호출시 사용되는 클래스
 */
@ApiModel(description = "돈 뿌리기 요청 모델")
public class MoneyDropRequest {

    @ApiModelProperty(notes = "뿌릴 금액", required = true, example = "500")
    private int moneyToDrop;
    @ApiModelProperty(notes = "돈을 받을 수 있는 최대 인원", required = true, example = "3")
    private int howManyUsers;

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
