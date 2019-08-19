package com.ntlts.c196.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AssessmentHelper extends SQLiteOpenHelper {
    //DB information
    private static final int DATABSE_VERSION = 1;
    private static final String DATABASE_NAME = "Assessment.db";

    private static final String CREATE_ENTRIES =
            "CREATE TABLE " + AssessmentDB.AssessmentEntry.TABLE_NAME + " (" +
                    AssessmentDB.AssessmentEntry._ID + " INTEGER PRIMARY KEY," +
                    AssessmentDB.AssessmentEntry.TITLE + " TEXT," +
                    AssessmentDB.AssessmentEntry.OAPA + " TEXT," +
                    AssessmentDB.AssessmentEntry.COURSEID + " INTEGER)";
    private static final String DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AssessmentDB.AssessmentEntry.TABLE_NAME;

    public AssessmentHelper(Context context){
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
