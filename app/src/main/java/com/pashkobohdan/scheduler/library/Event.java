package com.pashkobohdan.scheduler.library;

import com.pashkobohdan.scheduler.library.timeWorker.Time;

import java.util.Date;

/**
 * Created by Bohdan Pashko on 04.05.2016.
 */
public class Event {
    private int id;

    private String name;

    private int type;                           // тип задачи
    public static final int SIMPLE_TYPE = 0;    // simple event, like (buy the book)
    public static final int SUBJECT_TYPE = 1;   // subject event, like (study english)
    public static final int LAB_TYPE = 2;       // lab event, like (OOP - 3 labs to 20.05.16)

    private Time length;                         // длительность (например : 3 часа)

    private int priority;                       // приоритет задачи
    public static final int HARD_PRIORITY = 0;  // срочно !
    public static final int MIDDLE_PRIORITY = 1;// поскорее
    public static final int SMALL_PRIORITY = 2; // неважно

    private Date completeDate;                  // до какого числа (и времени) должно быть выполнено

    private Date startTime;                     // время начала (определяется динимически или задается вручную)

    private boolean isComplete;


    public static Event newInstance(String name, int type, int length, int priority, Date completeDate) {

        // !!!

        return null;
    }

    public Event() {
        this.name = null;
        this.type = SIMPLE_TYPE;
        this.length = null;
        this.priority = HARD_PRIORITY;
        this.completeDate = null;
        this.startTime = null;
        this.isComplete = false;
    }

    public Event(String name, int type, Time length, int priority, Date completeDate) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.priority = priority;
        this.completeDate = completeDate;
    }

    public Event(String name, int type, Time length, int priority, Date completeDate, Date startTime, boolean isComplete, int id) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.priority = priority;
        this.completeDate = completeDate;
        this.startTime = startTime;
        this.isComplete = isComplete;
        this.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Time getLength() {
        return length;
    }

    public void setLength(Time length) {
        this.length = length;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
