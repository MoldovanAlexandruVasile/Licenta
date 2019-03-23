package com.example.alexandrumoldovan.utilities.Models;

public class Event {

    private Integer ID;
    private String title;
    private String details;
    private String date;
    private String hour;
    private String minute;
    private String address;

    public Event(String title, String details, String date, String hour, String minute, String address) {
        this.title = title;
        this.details = details;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "Event{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
