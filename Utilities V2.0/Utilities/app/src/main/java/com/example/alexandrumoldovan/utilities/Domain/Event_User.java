package com.example.alexandrumoldovan.utilities.Domain;

public class Event_User {

    private Integer ID;
    private Integer user;
    private Integer event;
    private String status;

    public Event_User(Integer user, Integer event, String status) {
        this.user = user;
        this.event = event;
        this.status = status;
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

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Event_User{" +
                "ID=" + ID +
                ", user=" + user +
                ", event=" + event +
                ", status='" + status + '\'' +
                '}';
    }
}
