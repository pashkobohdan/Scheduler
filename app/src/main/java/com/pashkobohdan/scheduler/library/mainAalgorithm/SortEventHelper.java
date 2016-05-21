package com.pashkobohdan.scheduler.library.mainAalgorithm;

import com.pashkobohdan.scheduler.library.Event;
import com.pashkobohdan.scheduler.library.timeWorker.Lecture;

import java.util.List;

/**
 * Main helper class.
 * This class will sort events or put current event to best place
 * <p>
 * Created by Bohdan Pashko on 13.05.2016.
 */
public class SortEventHelper {
    private List<Lecture> lectureList;
    private List<Event> eventList;
    private Event currentEvent;

    /**
     * Simple constructor (don't use !).
     * Only with setters
     */
    public SortEventHelper() {
    }

    /**
     * Constructor - sort algorithm.
     * Sort the all events of them priority and duration.
     * @param lectureList
     * @param eventList
     */
    public SortEventHelper(List<Lecture> lectureList, List<Event> eventList) {
        this.lectureList = lectureList;
        this.eventList = eventList;
    }

    /**
     * @param lectureList list with lectures (student schedule)
     * @param eventList list with events
     * @param currentEvent current event ( if tou want to set him to place)
     */
    public SortEventHelper(List<Lecture> lectureList, List<Event> eventList, Event currentEvent) {
        this.lectureList = lectureList;
        this.eventList = eventList;
        this.currentEvent = currentEvent;
    }


    public List<Lecture> getLectureList() {
        return lectureList;
    }

    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }
}
