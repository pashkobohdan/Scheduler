package com.pashkobohdan.scheduler.library.timeWorker;


/**
 * Created by Bohdan Pashko on 04.05.2016.
 */
public class Time {
    private int hour;
    private int minute;


    public static Time newInstance(String arg) {
        try {
            String[] hour_minute = arg.split(":");
            return new Time(Integer.parseInt(hour_minute[0]), Integer.parseInt(hour_minute[1]));
        } catch (Exception e) {
            return null;
        }
    }

    public Time(int hour, int minute) {
        this.setHour(hour);
        this.setMinute(minute);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    @Override
    public String toString() {
        return hour + ":" + minute;
    }

    @Override
    public boolean equals(Object o) {
        Time t = (Time) o;
        return hour == t.getHour() && minute == t.getMinute();
    }
}
