package com.convergence.travelarrangement.model;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("token")
    private String token;

    public LoginModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
