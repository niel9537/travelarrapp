package com.convergence.travelarrangement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitFormModel {
    @SerializedName("submitForms")
    @Expose
    private Boolean submitForms;
    @SerializedName("message")
    @Expose
    private String message;

    public SubmitFormModel(Boolean submitForms, String message) {
        this.submitForms = submitForms;
        this.message = message;
    }

    public Boolean getSubmitForms() {
        return submitForms;
    }

    public void setSubmitForms(Boolean submitForms) {
        this.submitForms = submitForms;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
