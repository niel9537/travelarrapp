package com.convergence.travelarrangement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SetListFormsModel {
    @SerializedName("setForm")
    @Expose
    private Boolean setForm;
    @SerializedName("message")
    @Expose
    private String message;

    public SetListFormsModel(Boolean setForm, String message) {
        this.setForm = setForm;
        this.message = message;
    }

    public Boolean getSetForm() {
        return setForm;
    }

    public void setSetForm(Boolean setForm) {
        this.setForm = setForm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
