package com.example.alexandrumoldovan.utilities.Models;

public class Charges {

    private Integer ID;
    private Integer garage;
    private Integer reparations;
    private Integer cleaning;
    private String month;
    private String address;

    public Charges(Integer ID, Integer garage, Integer reparations, Integer cleaning, String month, String address) {
        this.ID = ID;
        this.garage = garage;
        this.reparations = reparations;
        this.cleaning = cleaning;
        this.month = month;
        this.address = address;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getGarage() {
        return garage;
    }

    public void setGarage(Integer garage) {
        this.garage = garage;
    }

    public Integer getReparations() {
        return reparations;
    }

    public void setReparations(Integer reparations) {
        this.reparations = reparations;
    }

    public Integer getCleaning() {
        return cleaning;
    }

    public void setCleaning(Integer cleaning) {
        this.cleaning = cleaning;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Charges{" +
                "ID=" + ID +
                ", garage=" + garage +
                ", reparations=" + reparations +
                ", cleaning=" + cleaning +
                ", month='" + month + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
