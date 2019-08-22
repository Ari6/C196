package com.ntlts.c196.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.ntlts.c196.Term;

import java.util.ArrayList;
import java.util.List;

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
    public long insertTerm(SQLiteDatabase db, Term term){
        ContentValues values = new ContentValues();
        values.put(TermDB.TermEntry.TITLE, term.getTitle());
        values.put(TermDB.TermEntry.START_DATE, term.getStartDate());
        values.put(TermDB.TermEntry.END_DATE, term.getEndDate());
        long newRowId = db.insert(
                TermDB.TermEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    public int deleteTerm(SQLiteDatabase db, int id){
        String selection =
                TermDB.TermEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        int deletedRows = db.delete(TermDB.TermEntry.TABLE_NAME,
                selection, selectionArgs);
        return deletedRows;
    }

    public int updateTerm(SQLiteDatabase db, Term term){
        ContentValues values = new ContentValues();
        values.put(TermDB.TermEntry.TITLE, term.getTitle());
        values.put(TermDB.TermEntry.START_DATE, term.getStartDate());
        values.put(TermDB.TermEntry.END_DATE, term.getEndDate());

        String selection = TermDB.TermEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(term.getId())};
        int count = db.update(
                TermDB.TermEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        return count;
    }

    public List<Term> selectAllTerm(SQLiteDatabase db){
        String[] projection = {
                BaseColumns._ID,
                TermDB.TermEntry.TITLE,
                TermDB.TermEntry.START_DATE,
                TermDB.TermEntry.END_DATE
        };
        String sortOrder = TermDB.TermEntry.START_DATE;
        Cursor cursor = db.query(
                TermDB.TermEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        List<Term> termList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Term term = new Term();
            term.setId((int) cursor.getLong(
                    cursor.getColumnIndexOrThrow(TermDB.TermEntry._ID)));
            term.setTitle(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(TermDB.TermEntry.TITLE)));
            term.setStartDate(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(TermDB.TermEntry.START_DATE)));
            term.setEndDate(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(TermDB.TermEntry.END_DATE)));
            termList.add(term);
        }
        cursor.close();
        return termList;
    }
    public Term selectTerm(SQLiteDatabase db, int id){
        String[] projection = {
                BaseColumns._ID,
                TermDB.TermEntry.TITLE,
                TermDB.TermEntry.START_DATE,
                TermDB.TermEntry.END_DATE
        };
        String selection = TermDB.TermEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        String sortOrder = TermDB.TermEntry.START_DATE;
        Cursor cursor = db.query(
                TermDB.TermEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        Term term = new Term();
        if (cursor.moveToNext()) {
            term.setId((int) cursor.getLong(
                    cursor.getColumnIndexOrThrow(TermDB.TermEntry._ID)));
            term.setTitle(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(TermDB.TermEntry.TITLE)));
            term.setStartDate(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(TermDB.TermEntry.START_DATE)));
            term.setEndDate(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(TermDB.TermEntry.END_DATE)));
        }
        cursor.close();
        return term;
    }
}
