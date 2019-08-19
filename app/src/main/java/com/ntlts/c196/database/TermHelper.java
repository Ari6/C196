package com.ntlts.c196.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TermHelper extends SQLiteOpenHelper {
    //DB version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Term.db";

    //Create and Delete database
    private static final String CREATE_ENTRIES =
            "CREATE TABLE " + TermDB.TermEntry.TABLE_NAME + " (" +
                    TermDB.TermEntry._ID + " INTEGER PRIMARY KEY," +
                    TermDB.TermEntry.TITLE + " TEXT," +
                    TermDB.TermEntry.START_DATE + " TEXT," +
                    TermDB.TermEntry.END_DATE + " TEXT)";
    private static final String DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TermDB.TermEntry.TABLE_NAME;
    public TermHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
