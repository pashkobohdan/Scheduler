package com.pashkobohdan.scheduler.library.dateBaseHelper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Bohdan Pashko on 05.05.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "scheduler.db";
    private static final int DATABASE_VERSION = 1;

//    // group schedules database
//    public static final String GROUP_ID = "group_id";
//    public static final String GROUP_NAME_COLUMN = "group_name";
//    public static final String UNIV_NAME_COLUMN = "univ_name";
//    public static final String DATE_OF_FIRST_WEEK_COLUMN = "date_of_first_week";
//    public static final String START_SEMESTER_DATE_COLUMN = "start_sem_date";
//    public static final String END_SEMESTER_DATE_COLUMN = "end_sem_date";
//
//    private static final String GROUP_SCHEDULE_DATABASE_TABLE = "group_schedules";
//    private static final String DATABASE_CREATE_GROUP_SCHEDULE_SCRIPT = "create table "
//            + GROUP_SCHEDULE_DATABASE_TABLE + " (" + GROUP_ID
//            + " integer primary key autoincrement, "
//            + GROUP_NAME_COLUMN + " text not null, "
//            + UNIV_NAME_COLUMN + " text not null, "
//            + DATE_OF_FIRST_WEEK_COLUMN + " text not null, "
//            + START_SEMESTER_DATE_COLUMN + " text, "
//            + END_SEMESTER_DATE_COLUMN + " text);";

    // subjects database
    public static final String SUBJECT_DATABASE_TABLE = "subjects";

    public static final String SUBJECT_ID = "subject_id";
    public static final String SUBJECT_NAME = "subject_name";
    public static final String TEACHER_NAME = "teacher_name";
    public static final String HOURS = "hours";
    public static final String SUBJECT_TYPE = "subject_type";

    private static final String DATABASE_CREATE_SUBJECT_SCRIPT = "create table "
            + SUBJECT_DATABASE_TABLE + " ("
            + SUBJECT_ID + " integer primary key autoincrement, "
            + SUBJECT_NAME + " text not null, "
            + TEACHER_NAME + " text, "
            + HOURS + " integer, "
            + SUBJECT_TYPE + " integer);";


    // lectures database
    public static final String LECTURE_DATABASE_TABLE = "lectures";

    public static final String LECTURE_ID = "lecture_id";
    public static final String DAY_OF_WEEK = "day_of_week";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String NUMBER_OF_WEEK = "number_of_week";


    private static final String DATABASE_CREATE_LECTURE_SCRIPT = "create table "
            + LECTURE_DATABASE_TABLE + " ("
            + LECTURE_ID + " integer primary key autoincrement, "
            + SUBJECT_ID + " integer, "
            + DAY_OF_WEEK + " text, "
            + START_TIME + " text, "
            + END_TIME + " text, "
            + NUMBER_OF_WEEK + " integer);";


    // events database
    public static final String EVENTS_DATABASE_TABLE = "events";

    public static final String EVENTS_ID = "event_id";
    public static final String EVENT_NAME = "event_name";
    public static final String EVENT_TYPE = "event_type";
    public static final String EVENT_PRIORITY = "event_priority";
    public static final String EVENT_LENGTH = "event_length";
    public static final String EVENT_END_TIME = "event_end_time";
    public static final String EVENT_START_TIME = "event_start_time";
    public static final String EVENT_IS_COMPLETE = "event_is_complete";

    private static final String DATABASE_CREATE_EVENTS_SCRIPT = "create table "
            + EVENTS_DATABASE_TABLE + " ("
            + EVENTS_ID + " integer primary key autoincrement, "
            + EVENT_NAME + " text, "
            + EVENT_TYPE + " integer, "
            + EVENT_PRIORITY + " integer, "
            + EVENT_LENGTH + " text, "
            + EVENT_END_TIME + " text, "
            + EVENT_START_TIME + " text, "
            + EVENT_IS_COMPLETE + " integer);";


    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SUBJECT_SCRIPT);
        db.execSQL(DATABASE_CREATE_LECTURE_SCRIPT);
        db.execSQL(DATABASE_CREATE_EVENTS_SCRIPT);

        Log.w("SQLite", "Create a new database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        db.execSQL("DROP TABLE IF IT EXISTS " + SUBJECT_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF IT EXISTS " + LECTURE_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_CREATE_EVENTS_SCRIPT);

        onCreate(db);
    }
}