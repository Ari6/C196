package com.ntlts.c196.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseHelper extends SQLiteOpenHelper {
    private static final int DATABSE_VERSION = 1;
    private static final String DATABASE_NAME = "Course.db";

    private static final String CREATE_ENTRIES =
            "CREATE TABLE " + CourseDB.CourseEntry.TABLE_NAME + " (" +
                    CourseDB.CourseEntry._ID + " INTEGER PRIMARY KEY," +
                    CourseDB.CourseEntry.TITLE + " TEXT," +
                    CourseDB.CourseEntry.START + " TEXT," +
                    CourseDB.CourseEntry.ANTICIPATED_DATE + " TEXT," +
                    CourseDB.CourseEntry.DUE_DATE + " TEXT," +
                    CourseDB.CourseEntry.NOTE + " TEXT," +
                    CourseDB.CourseEntry.STATUS + " TEXT," +
                    CourseDB.CourseEntry.MENTORID + " INTEGER," +
                    CourseDB.CourseEntry.TERMID + " INTEGER)";
    private static final String DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TermDB.TermEntry.TABLE_NAME;

    public CourseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }
}
