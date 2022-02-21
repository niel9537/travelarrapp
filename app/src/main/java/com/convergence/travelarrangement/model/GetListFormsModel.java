package com.convergence.travelarrangement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetListFormsModel {
    @SerializedName("listForms")
    @Expose
    private List<ListForm> listForms;
    @SerializedName("message")
    @Expose
    private String message;

    public GetListFormsModel(List<ListForm> listForms, String message) {
        this.listForms = listForms;
        this.message = message;
    }

    public List<ListForm> getListForms() {
        return listForms;
    }

    public void setListForms(List<ListForm> listForms) {
        this.listForms = listForms;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
