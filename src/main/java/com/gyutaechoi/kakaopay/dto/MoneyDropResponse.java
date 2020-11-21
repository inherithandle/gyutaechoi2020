package com.gyutaechoi.kakaopay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(description = "돈 뿌리기 API 조회")
public class MoneyDropResponse {

    @ApiModelProperty(notes = "돈을 뿌린 시간")
    LocalDateTime createdDateTime; // 돈 뿌린 시간

    @ApiModelProperty(notes = "뿌릴 금액")
    private int moneyToDrop; // 뿌린 금액

    @ApiModelProperty(notes = "받기 완료된 금액")
    private int droppedMoney; // 받기 완료된 금액

    @ApiModelProperty(notes = "돈 받은 유저들")
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
