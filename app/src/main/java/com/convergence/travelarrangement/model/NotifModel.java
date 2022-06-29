package com.convergence.travelarrangement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotifModel {
    @SerializedName("notif")
    @Expose
    private Boolean notif;
    @SerializedName("message")
    @Expose
    private String message;

    public NotifModel(Boolean notif, String message) {
        this.notif = notif;
        this.message = message;
    }

    public Boolean getNotif() {
        return notif;
    }

    public void setNotif(Boolean notif) {
        this.notif = notif;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
