package com.example.alexandrumoldovan.utilities.Models;

public class Address {

    private Integer ID;
    private String address;

    public Address(String address) {
        this.address = address;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "ID=" + ID +
                ", address='" + address + '\'' +
                '}';
    }
}
