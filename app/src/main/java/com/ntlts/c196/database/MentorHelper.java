package com.ntlts.c196.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MentorHelper extends SQLiteOpenHelper {
    //DB version
    private static int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "Mentor.db";

    //Create and delete
    private static final String CREATE_ENTRIES =
            "CREATE TABLE " + MentorDB.MentorEntry.TABLE_NAME + " (" +
                    MentorDB.MentorEntry._ID + " INTEGER PRIMARY KEY," +
                    MentorDB.MentorEntry.NAME + " TEXT," +
                    MentorDB.MentorEntry.PHONE + " TEXT," +
                    MentorDB.MentorEntry.EMAIL + " TEXT)";
    private static final String DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MentorDB.MentorEntry.TABLE_NAME;

    public MentorHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public void onDowngrade(SQLiteDatabase db, int i, int il) {
        onUpgrade(db, i, il);
    }
}
