package com.pashkobohdan.scheduler.library.timeWorker;

/**
 * Created by Bohdan Pashko on 04.05.2016.
 */
public enum Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public static Day getDay(int number) {
        if (number < 0 || number > 7) {
            return null;
        }
        switch (number) {
            case 0:
                return MONDAY;
            case 1:
                return TUESDAY;
            case 2:
                return WEDNESDAY;
            case 3:
                return THURSDAY;
            case 4:
                return FRIDAY;
            case 5:
                return SATURDAY;
            case 6:
                return SUNDAY;
            default:
                return null;
        }
    }

    static public String getStringDay(int number) {
        if (number < 0 || number > 7) {
            return null;
        }
        switch (number) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
            default:
                return null;
        }
    }

    static public String getStringDay(Day day) {
        switch (day) {
            case MONDAY:
                return "Monday";
            case TUESDAY:
                return "Tuesday";
            case WEDNESDAY:
                return "Wednesday";
            case THURSDAY:
                return "Thursday";
            case FRIDAY:
                return "Friday";
            case SATURDAY:
                return "Saturday";
            case SUNDAY:
                return "Sunday";
            default:
                return null;
        }
    }

    static public Day getDay(String day) {
        switch (day) {
            case "Monday":
                return MONDAY;
            case "Tuesday":
                return TUESDAY;
            case "Wednesday":
                return WEDNESDAY;
            case "Thursday":
                return THURSDAY;
            case "Friday":
                return FRIDAY;
            case "Saturday":
                return SATURDAY;
            case "Sunday":
                return SUNDAY;
            default:
                return null;
        }
    }

    static public int getDayNumber(Day day) {
        switch (day) {
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            case SATURDAY:
                return 6;
            case SUNDAY:
                return 7;
            default:
                return -1;
        }
    }
}
