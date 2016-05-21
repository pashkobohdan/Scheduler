package com.pashkobohdan.scheduler.library.timeWorker;

/**
 * Created by Bohdan Pashko on 04.05.2016.
 */
public class Lecture {
    private int lectureID;
    private Subject subject;

    private Time startTime;

    private Time endTime;

    private Day day;

    private int numberOfWeek = BOTH_WEEK;
    public static final int FIRST_WEEK = 0;
    public static final int SECOND_WEEK = 1;
    public static final int BOTH_WEEK = 2;

    public Lecture() {
    }

    public Lecture(Subject subject, Time startTime, Time endTime, Day day, int numberOfWeek, int lectureID) {
        this.setSubject(subject);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setDay(day);
        this.setNumberOfWeek(numberOfWeek);
        this.setLectureID(lectureID);
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public int getNumberOfWeek() {
        return numberOfWeek;
    }

    public void setNumberOfWeek(int oneOfWek) {
        this.numberOfWeek = oneOfWek;
    }

    public int getLectureID() {
        return lectureID;
    }

    public void setLectureID(int lectureID) {
        this.lectureID = lectureID;
    }
}
