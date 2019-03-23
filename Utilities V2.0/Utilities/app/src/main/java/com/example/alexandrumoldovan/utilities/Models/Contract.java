package com.example.alexandrumoldovan.utilities.Models;

public class Contract {
    private Integer ID;
    private Integer user;
    private String water;
    private String gas;
    private String electricity;
    private String garage;

    public Contract(Integer user, String water, String gas, String electricity, String garage) {
        this.user = user;
        this.water = water;
        this.gas = gas;
        this.electricity = electricity;
        this.garage = garage;
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

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "ID=" + ID +
                ", user=" + user +
                ", water='" + water + '\'' +
                ", gas='" + gas + '\'' +
                ", electricity='" + electricity + '\'' +
                ", garage='" + garage + '\'' +
                '}';
    }
}
