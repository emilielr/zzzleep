package com.example.zzzleep.ui.statistics;

import java.io.Serializable;

public class SleepObject implements Serializable {
    private String date;
    private int hours;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public SleepObject(String date, int hours) {
        this.date = date;
        this.hours = hours;
    }
}
