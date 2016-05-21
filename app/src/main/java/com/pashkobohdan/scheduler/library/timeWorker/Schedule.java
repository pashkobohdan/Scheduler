package com.pashkobohdan.scheduler.library.timeWorker;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Bohdan Pashko on 04.05.2016.
 */
public class Schedule {
    private List<Lecture> lectures;
    private Day firstWeekDay;


    public Schedule() {
    }

    public Schedule(List<Lecture> lectures, Day firstWeekDay) {
        this.lectures = lectures;
        this.firstWeekDay = firstWeekDay;
    }

    public List<Lecture> getLesionsAtDay(Date date) {
        List<Lecture> resultList = new LinkedList<>();
        for (Lecture lecture : lectures) {
            if (lecture.getDay() == Day.getDay(date.getDay() - 1)) {
                resultList.add(lecture);
            }
        }

        Collections.sort(resultList, new Comparator<Lecture>() {
            @Override
            public int compare(Lecture l1, Lecture l2) {
                return l1.getStartTime().getHour() > l2.getStartTime().getHour() ? 1 :
                        l1.getStartTime().getHour() < l2.getStartTime().getHour() ? -1 :
                                l1.getStartTime().getMinute() > l2.getStartTime().getMinute() ? 1 :
                                        -1;
            }
        });

        return resultList;
    }


    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public Day getFirstWeekDay() {
        return firstWeekDay;
    }

    public void setFirstWeekDay(Day firstWeekDay) {
        this.firstWeekDay = firstWeekDay;
    }
}
