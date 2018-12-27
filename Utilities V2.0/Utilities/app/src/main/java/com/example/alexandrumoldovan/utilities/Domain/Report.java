package com.example.alexandrumoldovan.utilities.Domain;

public class Report {

    private Integer ID;
    private Integer user;
    private String utility;
    private Integer quantity;
    private String month;
    private String date;

    public Report(Integer user, String utility, Integer quantity, String month, String date) {
        this.user = user;
        this.utility = utility;
        this.quantity = quantity;
        this.month = month;
        this.date = date;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Report{" +
                "ID=" + ID +
                ", user=" + user +
                ", utility='" + utility + '\'' +
                ", quantity=" + quantity +
                ", month='" + month + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
