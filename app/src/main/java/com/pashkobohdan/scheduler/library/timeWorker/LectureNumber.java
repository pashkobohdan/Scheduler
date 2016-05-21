package com.pashkobohdan.scheduler.library.timeWorker;

/**
 * Created by Bohdan Pashko on 17.05.2016.
 */
public class LectureNumber {
    private Time start;
    private Time end;

    public static final LectureNumber first = new LectureNumber(new Time(8, 30), new Time(10, 0));

    public static final LectureNumber second = new LectureNumber(new Time(10, 30), new Time(12, 0));

    public static final LectureNumber third = new LectureNumber(new Time(12, 30), new Time(14, 0));

    public static final LectureNumber four = new LectureNumber(new Time(14, 30), new Time(16, 0));

    public static final LectureNumber five = new LectureNumber(new Time(16, 30), new Time(18, 0));

    public static final LectureNumber six = new LectureNumber(new Time(18, 30), new Time(20, 0));


    public LectureNumber(Time start, Time end) {
        this.setStart(start);
        this.setEnd(end);
    }

    public static LectureNumber getLectureNumber(int number) {
        switch (number) {
            case 1:
                return first;
            case 2:
                return second;
            case 3:
                return third;
            case 4:
                return four;
            case 5:
                return five;
            case 6:
                return six;
            default:
                return null;
        }
    }

    public static int getLectureNumber(Time start, Time end) {
        for (int i = 1; i < 7; i++) {
            if (getLectureNumber(i).getStart().equals(start) && getLectureNumber(i).getEnd().equals(end)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return getStart().toString() + " - " + getEnd().toString();
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }
}
