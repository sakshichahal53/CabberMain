package com.example.sakshi.cabber.models;

/**
 * Created by sakshi on 17/6/18.
 */

public class SingleTrip {
    String time, car_name, cash;
    Boolean status;

    public SingleTrip(String time, String car_name, String cash, Boolean status) {
        this.time = time;
        this.car_name = car_name;
        this.cash = cash;
        this.status = status;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
