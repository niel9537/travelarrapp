package com.convergence.travelarrangement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("registrasi")
    @Expose
    private Boolean registrasi;
    @SerializedName("message")
    @Expose
    private String message;

    public RegisterModel(Boolean registrasi, String message) {
        this.registrasi = registrasi;
        this.message = message;
    }

    public Boolean getRegistrasi() {
        return registrasi;
    }

    public void setRegistrasi(Boolean registrasi) {
        this.registrasi = registrasi;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
