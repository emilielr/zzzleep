package com.example.zzzleep.ui.statistics;

import java.io.Serializable;

public class SleepObject implements Serializable {
    private String date;
    private int hours;

    public String getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    public SleepObject(String date, int hours) {
        this.date = date;
        this.hours = hours;
    }
}
