package com.convergence.travelarrangement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListForm {
    @SerializedName("id_ticketarr")
    @Expose
    private String idTicketarr;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("travelreason")
    @Expose
    private String travelreason;
    @SerializedName("fromcity")
    @Expose
    private String fromcity;
    @SerializedName("tocity")
    @Expose
    private String tocity;
    @SerializedName("dates")
    @Expose
    private String dates;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("urgent")
    @Expose
    private String urgent;
    @SerializedName("transport")
    @Expose
    private String transport;
    @SerializedName("hotel")
    @Expose
    private String hotel;
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("status")
    @Expose
    private String status;

    public ListForm(String idTicketarr, String name, String nik, String division, String phonenumber, String email, String travelreason, String fromcity, String tocity, String dates, String duration, String urgent, String transport, String hotel, String budget, String status) {
        this.idTicketarr = idTicketarr;
        this.name = name;
        this.nik = nik;
        this.division = division;
        this.phonenumber = phonenumber;
        this.email = email;
        this.travelreason = travelreason;
        this.fromcity = fromcity;
        this.tocity = tocity;
        this.dates = dates;
        this.duration = duration;
        this.urgent = urgent;
        this.transport = transport;
        this.hotel = hotel;
        this.budget = budget;
        this.status = status;
    }

    public String getIdTicketarr() {
        return idTicketarr;
    }

    public void setIdTicketarr(String idTicketarr) {
        this.idTicketarr = idTicketarr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTravelreason() {
        return travelreason;
    }

    public void setTravelreason(String travelreason) {
        this.travelreason = travelreason;
    }

    public String getFromcity() {
        return fromcity;
    }

    public void setFromcity(String fromcity) {
        this.fromcity = fromcity;
    }

    public String getTocity() {
        return tocity;
    }

    public void setTocity(String tocity) {
        this.tocity = tocity;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
