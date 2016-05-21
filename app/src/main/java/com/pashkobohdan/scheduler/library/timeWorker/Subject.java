package com.pashkobohdan.scheduler.library.timeWorker;

/**
 * Created by Bohdan Pashko on 04.05.2016.
 */
public class Subject {
    private int id;

    private String name;

    private String teacher;

    private int hours;
    public static final int HOURS_8 = 8;
    public static final int HOURS_16 = 16;
    public static final int HOURS_32 = 32;

    private int subjectType;
    public static final int TECHNICAL = 0;    // simple event, like (buy the book)
    public static final int HUMANITARIAN = 1;   // subject event, like (study english)
    public static final int SPORT = 2;       // lab event, like (OOP - 3 labs to 20.05.16)

    public Subject() {
        subjectType = TECHNICAL;
        hours = HOURS_16;
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, String teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public Subject(int id, String name, String teacher, int hours, int subjectType) {
        this.setId(id);
        this.name = name;
        this.teacher = teacher;
        this.hours = hours;
        this.subjectType = subjectType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(int subjectType) {
        this.subjectType = subjectType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
