package com.pashkobohdan.scheduler.library.dateBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.pashkobohdan.scheduler.library.Event;
import com.pashkobohdan.scheduler.library.timeWorker.Day;
import com.pashkobohdan.scheduler.library.timeWorker.Lecture;
import com.pashkobohdan.scheduler.library.timeWorker.Subject;
import com.pashkobohdan.scheduler.library.timeWorker.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Bohdan Pashko on 17.05.2016.
 */
public class ReadData {
    public static final String DATABASE_NAME = "scheduler.db";
    public static final int DATABASE_VERSION = 1;

    private static List<Subject> subjectList = new LinkedList<>();
    private static List<Lecture> lecturesList = new LinkedList<>();
    private static List<Event> eventList = new LinkedList<>();

    private static DatabaseHelper mDatabaseHelper;
    private static SQLiteDatabase mSqLiteDatabase;

    private static Context context;

    // refresh
    public static boolean refreshSubjects() {
        List<Subject> resultList = new LinkedList<>();

        try {
            Cursor cursorEvents = mSqLiteDatabase.query(DatabaseHelper.SUBJECT_DATABASE_TABLE, new String[]{
                            DatabaseHelper.SUBJECT_ID,
                            DatabaseHelper.SUBJECT_NAME,
                            DatabaseHelper.TEACHER_NAME,
                            DatabaseHelper.SUBJECT_TYPE,
                            DatabaseHelper.HOURS},
                    null, null,
                    null, null, null);
            int subjectId;
            String subjectName, teacherName;
            int subjectType, hours;
            Subject subject;

            while (cursorEvents.moveToNext()) {
                subjectId = cursorEvents.getInt(cursorEvents.getColumnIndex(DatabaseHelper.SUBJECT_ID));
                hours = cursorEvents.getInt(cursorEvents.getColumnIndex(DatabaseHelper.HOURS));
                subjectName = cursorEvents.getString(cursorEvents.getColumnIndex(DatabaseHelper.SUBJECT_NAME));
                teacherName = cursorEvents.getString(cursorEvents.getColumnIndex(DatabaseHelper.TEACHER_NAME));
                subjectType = cursorEvents.getInt(cursorEvents.getColumnIndex(DatabaseHelper.SUBJECT_TYPE));

                subject = new Subject(subjectId, subjectName, teacherName, hours, subjectType);

                resultList.add(subject);

            }
            cursorEvents.close();

            subjectList = resultList;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean refreshLectures() {
        if (!refreshSubjects()) {
            return false;
        }

        List<Lecture> resultList = new LinkedList<>();

        try {
            Cursor cursorEvents = mSqLiteDatabase.query(DatabaseHelper.LECTURE_DATABASE_TABLE, new String[]{
                            DatabaseHelper.LECTURE_ID,
                            DatabaseHelper.SUBJECT_ID,
                            DatabaseHelper.DAY_OF_WEEK,
                            DatabaseHelper.START_TIME,
                            DatabaseHelper.END_TIME,
                            DatabaseHelper.NUMBER_OF_WEEK},
                    null, null,
                    null, null, null);


            int lectureID, numberOfWeek;
            int subjectID;
            String startTime, endTime, dayOfWeek;
            Subject subject = null;

            Lecture lecture;

            //read data !!!!!!!!!!! from database

            while (cursorEvents.moveToNext()) {
                lectureID = cursorEvents.getInt(cursorEvents.getColumnIndex(DatabaseHelper.LECTURE_ID));
                subjectID = cursorEvents.getInt(cursorEvents.getColumnIndex(DatabaseHelper.SUBJECT_ID));
                for (Subject s : subjectList) {
                    if (s.getId() == subjectID) {
                        subject = s;
                    }
                }

                startTime = cursorEvents.getString(cursorEvents.getColumnIndex(DatabaseHelper.START_TIME));
                endTime = cursorEvents.getString(cursorEvents.getColumnIndex(DatabaseHelper.END_TIME));
                dayOfWeek = cursorEvents.getString(cursorEvents.getColumnIndex(DatabaseHelper.DAY_OF_WEEK));
                numberOfWeek = cursorEvents.getInt(cursorEvents.getColumnIndex(DatabaseHelper.NUMBER_OF_WEEK));

                lecture = new Lecture(subject, Time.newInstance(startTime), Time.newInstance(endTime), Day.getDay(dayOfWeek), numberOfWeek, lectureID);

                resultList.add(lecture);
            }
            cursorEvents.close();

            lecturesList = resultList;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean refreshEvents() {
        List<Event> resultList = new LinkedList<>();

        try {
            Cursor cursorEvents = mSqLiteDatabase.query(DatabaseHelper.EVENTS_DATABASE_TABLE, new String[]{
                            DatabaseHelper.EVENTS_ID,
                            DatabaseHelper.EVENT_NAME,
                            DatabaseHelper.EVENT_TYPE,
                            DatabaseHelper.EVENT_PRIORITY,
                            DatabaseHelper.EVENT_LENGTH,
                            DatabaseHelper.EVENT_END_TIME,
                            DatabaseHelper.EVENT_START_TIME,
                            DatabaseHelper.EVENT_IS_COMPLETE},
                    null, null,
                    null, null, null);


            int eventId;
            String eventName;
            Date eventEndTime = null, eventStartTime = null;
            int eventType, eventPriority;
            Time eventLength;
            boolean isComplete;
            Event event;

            //read data !!!!!!!!!!! from database

            while (cursorEvents.moveToNext()) {
                eventId = cursorEvents.getInt(cursorEvents.getColumnIndex(DatabaseHelper.EVENTS_ID));

                eventName = cursorEvents.getString(cursorEvents.getColumnIndex(DatabaseHelper.EVENT_NAME));

                eventType = cursorEvents.getInt(cursorEvents.getColumnIndex(DatabaseHelper.EVENT_TYPE));
                eventPriority = cursorEvents.getInt(cursorEvents.getColumnIndex(DatabaseHelper.EVENT_PRIORITY));
                eventLength = Time.newInstance(cursorEvents.getString(cursorEvents.getColumnIndex(DatabaseHelper.EVENT_LENGTH)));
                try {
                    eventEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(cursorEvents.getString(cursorEvents.getColumnIndex(DatabaseHelper.EVENT_END_TIME)));
                    eventStartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(cursorEvents.getString(cursorEvents.getColumnIndex(DatabaseHelper.EVENT_START_TIME)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                isComplete = cursorEvents.getInt(cursorEvents.getColumnIndex(DatabaseHelper.EVENT_IS_COMPLETE)) == 0 ? false : true;

                event = new Event(eventName, eventType, eventLength, eventPriority, eventEndTime, eventStartTime, isComplete, eventId);

                resultList.add(event);
            }
            cursorEvents.close();

            eventList = resultList;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //


    //delete
    public static boolean deleteSubject(Subject subject) {
        mSqLiteDatabase.delete(DatabaseHelper.SUBJECT_DATABASE_TABLE,
                mDatabaseHelper.SUBJECT_ID + " = ?", new String[]{Integer.toString(subject.getId())});

        return true;
    }

    public static boolean deleteLecture(Lecture lecture) {
        mSqLiteDatabase.delete(DatabaseHelper.LECTURE_DATABASE_TABLE,
                mDatabaseHelper.LECTURE_ID + " = ?", new String[]{Integer.toString(lecture.getLectureID())});

        return true;
    }

    public static boolean deleteEvent(Event event) {
        mSqLiteDatabase.delete(DatabaseHelper.EVENTS_DATABASE_TABLE,
                mDatabaseHelper.EVENTS_ID + " = ?", new String[]{Integer.toString(event.getId())});

        return true;
    }
    //


    //edit
    public static boolean editSubject(Subject subject) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SUBJECT_NAME, subject.getName());
        values.put(DatabaseHelper.HOURS, subject.getHours());
        values.put(DatabaseHelper.TEACHER_NAME, subject.getTeacher());
        values.put(DatabaseHelper.SUBJECT_TYPE, subject.getSubjectType());


        mSqLiteDatabase.update(DatabaseHelper.SUBJECT_DATABASE_TABLE, values,
                mDatabaseHelper.SUBJECT_ID + " = ?", new String[]{Integer.toString(subject.getId())});
        return true;
    }

    public static boolean editLecture(Lecture lecture) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SUBJECT_ID, lecture.getSubject().getId());
        values.put(DatabaseHelper.DAY_OF_WEEK, Day.getStringDay(lecture.getDay()));
        values.put(DatabaseHelper.START_TIME, lecture.getStartTime().toString());
        values.put(DatabaseHelper.END_TIME, lecture.getEndTime().toString());
        values.put(DatabaseHelper.NUMBER_OF_WEEK, lecture.getNumberOfWeek());


        mSqLiteDatabase.update(DatabaseHelper.LECTURE_DATABASE_TABLE, values,
                mDatabaseHelper.LECTURE_ID + " = ?", new String[]{Integer.toString(lecture.getLectureID())});
        return true;
    }

    public static boolean editEvent(Event event) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.EVENT_NAME, event.getName());
        values.put(DatabaseHelper.EVENT_TYPE, event.getType());
        values.put(DatabaseHelper.EVENT_PRIORITY, event.getPriority());
        values.put(DatabaseHelper.EVENT_LENGTH, event.getLength().toString());
        values.put(DatabaseHelper.EVENT_END_TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(event.getCompleteDate()));
        values.put(DatabaseHelper.EVENT_START_TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(event.getStartTime()));
        values.put(DatabaseHelper.EVENT_IS_COMPLETE, event.isComplete());

        return mSqLiteDatabase.update(DatabaseHelper.EVENTS_DATABASE_TABLE, values,
                mDatabaseHelper.EVENTS_ID + " = ?", new String[]{Integer.toString(event.getId())}) == -1 ? false : true;
    }
    //


    //add
    public static boolean addSubject(Subject subject) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SUBJECT_NAME, subject.getName());
        values.put(DatabaseHelper.HOURS, subject.getHours());
        values.put(DatabaseHelper.TEACHER_NAME, subject.getTeacher());
        values.put(DatabaseHelper.SUBJECT_TYPE, subject.getSubjectType());

        return mSqLiteDatabase.insert(DatabaseHelper.SUBJECT_DATABASE_TABLE, null, values) == -1 ? false : true;
    }

    public static boolean addLecture(Lecture lecture) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SUBJECT_ID, lecture.getSubject().getId());
        values.put(DatabaseHelper.DAY_OF_WEEK, Day.getStringDay(lecture.getDay()));
        values.put(DatabaseHelper.START_TIME, lecture.getStartTime().toString());
        values.put(DatabaseHelper.END_TIME, lecture.getEndTime().toString());
        values.put(DatabaseHelper.NUMBER_OF_WEEK, lecture.getNumberOfWeek());


        return mSqLiteDatabase.insert(DatabaseHelper.LECTURE_DATABASE_TABLE, null, values) == -1 ? false : true;
    }

    public static boolean addEvent(Event event) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.EVENT_NAME, event.getName());
        values.put(DatabaseHelper.EVENT_TYPE, event.getType());
        values.put(DatabaseHelper.EVENT_PRIORITY, event.getPriority());
        values.put(DatabaseHelper.EVENT_LENGTH, event.getLength().toString());
        values.put(DatabaseHelper.EVENT_END_TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(event.getCompleteDate()));
        values.put(DatabaseHelper.EVENT_START_TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(event.getStartTime()));
        values.put(DatabaseHelper.EVENT_IS_COMPLETE, event.isComplete());

        mSqLiteDatabase.insert(DatabaseHelper.EVENTS_DATABASE_TABLE, null, values);
        return true;
    }
    //


    public static boolean setContext(Context context) {
        ReadData.context = context;
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
            mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
            return true;
        } else {
            return false;
        }
    }

    public static List<Subject> getSubjectList() {
        return subjectList;
    }

    public static void setSubjectList(List<Subject> subjectList) {
        ReadData.subjectList = subjectList;
    }

    public static List<Lecture> getLecturesList() {
        return lecturesList;
    }

    public static void setLecturesList(List<Lecture> lecturesList) {
        ReadData.lecturesList = lecturesList;
    }

    public static List<Event> getEventList() {
        return eventList;
    }

    public static void setEventList(List<Event> eventList) {
        ReadData.eventList = eventList;
    }
}
