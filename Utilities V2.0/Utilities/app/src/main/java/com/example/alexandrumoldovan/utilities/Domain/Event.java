package com.example.alexandrumoldovan.utilities.Domain;

public class Event {

    private Integer ID;
    private String title;
    private String details;
    private String address;

    public Event(String title, String details, String address) {
        this.title = title;
        this.details = details;
        this.address = address;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Event{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
