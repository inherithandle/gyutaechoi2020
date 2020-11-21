package com.gyutaechoi.kakaopay.dto;

import io.swagger.annotations.ApiModel;

/**
 * 돈뿌리기 API 응답 클래스
 */
@ApiModel(description = "돈 뿌리기 API 응답 모델")
public class MoneyDropPostResponse {

    private String token;

    public MoneyDropPostResponse() {
    }

    public MoneyDropPostResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
