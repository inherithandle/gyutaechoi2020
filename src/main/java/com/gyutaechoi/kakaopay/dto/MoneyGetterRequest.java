package com.gyutaechoi.kakaopay.dto;

public class MoneyGetterRequest {

    private String token;

    public MoneyGetterRequest() {

    }

    public MoneyGetterRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
