package com.ntlts.c196.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.ntlts.c196.Mentor;

import java.util.ArrayList;
import java.util.List;

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

    public Mentor selectMentor(SQLiteDatabase db, int mentorId){
        String[] projection = {
                BaseColumns._ID,
                MentorDB.MentorEntry.NAME,
                MentorDB.MentorEntry.PHONE,
                MentorDB.MentorEntry.EMAIL
        };
        String selection = MentorDB.MentorEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(mentorId)};
        Cursor cursor = db.query(
                MentorDB.MentorEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Mentor mentor = new Mentor();
        while(cursor.moveToNext()){
            mentor.setMentorId(
                    cursor.getInt(cursor.getColumnIndexOrThrow(MentorDB.MentorEntry._ID)));
            mentor.setName(
                    cursor.getString(cursor.getColumnIndexOrThrow(MentorDB.MentorEntry.NAME)));
            mentor.setPhone(
                    cursor.getString(cursor.getColumnIndexOrThrow(MentorDB.MentorEntry.PHONE)));
            mentor.setEmail(
                    cursor.getString(cursor.getColumnIndexOrThrow(MentorDB.MentorEntry.EMAIL)));
        }
        cursor.close();
        return mentor;
    }

    public long insertMentor(SQLiteDatabase db, Mentor mentor){
        ContentValues values = new ContentValues();
        values.put(MentorDB.MentorEntry.NAME, mentor.getName());
        values.put(MentorDB.MentorEntry.PHONE, mentor.getPhone());
        values.put(MentorDB.MentorEntry.EMAIL, mentor.getEmail());

        long newRowId = db.insert(MentorDB.MentorEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    public List<Mentor> getAllMentor(SQLiteDatabase db){
        String[] projection = {
                BaseColumns._ID,
                MentorDB.MentorEntry.NAME,
                MentorDB.MentorEntry.PHONE,
                MentorDB.MentorEntry.EMAIL
        };
        Cursor cursor = db.query(
                MentorDB.MentorEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                MentorDB.MentorEntry.NAME
        );
        List<Mentor> mentorList = new ArrayList<>();
        while(cursor.moveToNext()){
            Mentor mentor = new Mentor();
            mentor.setMentorId(
                    cursor.getInt(cursor.getColumnIndexOrThrow(MentorDB.MentorEntry._ID)));
            mentor.setName(
                    cursor.getString(cursor.getColumnIndexOrThrow(MentorDB.MentorEntry.NAME)));
            mentor.setPhone(
                    cursor.getString(cursor.getColumnIndexOrThrow(MentorDB.MentorEntry.PHONE)));
            mentor.setEmail(
                    cursor.getString(cursor.getColumnIndexOrThrow(MentorDB.MentorEntry.EMAIL)));
            mentorList.add(mentor);
        }
        cursor.close();
        return mentorList;
    }
}
