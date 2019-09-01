package com.ntlts.c196.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.ntlts.c196.Assessment;
import com.ntlts.c196.Course;

import java.util.ArrayList;
import java.util.List;

public class AssessmentHelper extends SQLiteOpenHelper {
    //DB information
    private static final int DATABSE_VERSION = 1;
    private static final String DATABASE_NAME = "Assessment.db";

    private static final String CREATE_ENTRIES =
            "CREATE TABLE " + AssessmentDB.AssessmentEntry.TABLE_NAME + " (" +
            AssessmentDB.AssessmentEntry._ID + " INTEGER PRIMARY KEY," +
            AssessmentDB.AssessmentEntry.TITLE + " TEXT," +
            AssessmentDB.AssessmentEntry.OAPA + " TEXT," +
            AssessmentDB.AssessmentEntry.COURSEID + " INTEGER," +
            AssessmentDB.AssessmentEntry.PERFORMANCE + " TEXT," +
            AssessmentDB.AssessmentEntry.GOALDATE + " TEXT)";
            //AssessmentDB.AssessmentEntry.DUEDATE + " TEXT)";

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

    public List<Assessment> getAssessmentWithCourseId(SQLiteDatabase db, int courseId){
        String[] projection = {
            BaseColumns._ID,
            AssessmentDB.AssessmentEntry.TITLE,
            //AssessmentDB.AssessmentEntry.DUEDATE,
            AssessmentDB.AssessmentEntry.OAPA,
            AssessmentDB.AssessmentEntry.PERFORMANCE,
            AssessmentDB.AssessmentEntry.COURSEID,
            AssessmentDB.AssessmentEntry.GOALDATE
        };
        String selection = AssessmentDB.AssessmentEntry.COURSEID + " = ?";
        String[] selectionArgs = {String.valueOf(courseId)};
        String sortOrder = AssessmentDB.AssessmentEntry.GOALDATE;
        Cursor cursor = db.query(
                AssessmentDB.AssessmentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List<Assessment> assessmentList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Assessment assessment = new Assessment();
            assessment.setAssessmentId(cursor.getInt(
                    cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry._ID)));
            assessment.setTitle(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.TITLE)));
            assessment.setOaPa(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.OAPA)));
            /*assessment.setDueDate(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.DUEDATE)));*/
            assessment.setPerformance(cursor.getString(
                    cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.PERFORMANCE)));
            assessment.setCourseId(
                    cursor.getInt(cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.COURSEID)));
            assessment.setGoalDate(
                    cursor.getString(cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.GOALDATE)));
            assessmentList.add(assessment);
        }
        cursor.close();
        return assessmentList;
    }
    public Assessment getAssessment(SQLiteDatabase db, int assessmentId){
        String[] projection = {
                BaseColumns._ID,
                AssessmentDB.AssessmentEntry.TITLE,
                //AssessmentDB.AssessmentEntry.DUEDATE,
                AssessmentDB.AssessmentEntry.OAPA,
                AssessmentDB.AssessmentEntry.PERFORMANCE,
                AssessmentDB.AssessmentEntry.COURSEID,
                AssessmentDB.AssessmentEntry.GOALDATE
        };
        String selection = AssessmentDB.AssessmentEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(assessmentId)};
        String sortOrder = AssessmentDB.AssessmentEntry.GOALDATE;
        Cursor cursor = db.query(
                AssessmentDB.AssessmentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        Assessment assessment = new Assessment();
        if (cursor.moveToNext()) {
            assessment.setAssessmentId(cursor.getInt(
                    cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry._ID)));
            assessment.setTitle(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.TITLE)));
            assessment.setOaPa(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.OAPA)));
            /*assessment.setDueDate(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.DUEDATE)));*/
            assessment.setPerformance(cursor.getString(
                    cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.PERFORMANCE)));
            assessment.setCourseId(
                    cursor.getInt(cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.COURSEID)));
            assessment.setGoalDate(
                    cursor.getString(cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.GOALDATE)));
        }
        cursor.close();
        return assessment;
    }
    public int insertAssessment(SQLiteDatabase db, Assessment assessment){
        ContentValues values = new ContentValues();
        values.put(AssessmentDB.AssessmentEntry.TITLE, assessment.getTitle());
        values.put(AssessmentDB.AssessmentEntry.OAPA, assessment.getOaPa());
        values.put(AssessmentDB.AssessmentEntry.COURSEID, assessment.getCourseId());
        values.put(AssessmentDB.AssessmentEntry.PERFORMANCE, assessment.getPerformance());
        //values.put(AssessmentDB.AssessmentEntry.DUEDATE, assessment.getDueDate());
        values.put(AssessmentDB.AssessmentEntry.GOALDATE, assessment.getGoalDate());
        long newRowId = db.insert(AssessmentDB.AssessmentEntry.TABLE_NAME, null, values);
        Log.d("INSERTASSESSMENT: ", String.valueOf(newRowId));
        return (int) newRowId;
    }
    public int updateAssessment(SQLiteDatabase db, Assessment assessment){
        ContentValues values = new ContentValues();
        values.put(AssessmentDB.AssessmentEntry.TITLE, assessment.getTitle());
        values.put(AssessmentDB.AssessmentEntry.OAPA, assessment.getOaPa());
        values.put(AssessmentDB.AssessmentEntry.COURSEID, assessment.getCourseId());
        values.put(AssessmentDB.AssessmentEntry.PERFORMANCE, assessment.getPerformance());
        //values.put(AssessmentDB.AssessmentEntry.DUEDATE, assessment.getDueDate());
        values.put(AssessmentDB.AssessmentEntry.GOALDATE, assessment.getGoalDate());
        String selection = AssessmentDB.AssessmentEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(assessment.getAssessmentId())};
        long updateRowId = db.update(AssessmentDB.AssessmentEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return (int) updateRowId;
    }
    public int deleteAssessment(SQLiteDatabase db, int assessmentId){
        String selection = AssessmentDB.AssessmentEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(assessmentId)};
        int deletedRows = db.delete(AssessmentDB.AssessmentEntry.TABLE_NAME,
                selection,
                selectionArgs);
        return deletedRows;
    }
    public List<Assessment> getAllAssessment(SQLiteDatabase db){
        String[] projection = {
                BaseColumns._ID,
                AssessmentDB.AssessmentEntry.TITLE,
                //AssessmentDB.AssessmentEntry.DUEDATE,
                AssessmentDB.AssessmentEntry.OAPA,
                AssessmentDB.AssessmentEntry.PERFORMANCE,
                AssessmentDB.AssessmentEntry.COURSEID,
                AssessmentDB.AssessmentEntry.GOALDATE
        };
        String sortOrder = AssessmentDB.AssessmentEntry.GOALDATE;
        Cursor cursor = db.query(
                AssessmentDB.AssessmentEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<Assessment> assessmentList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Assessment assessment = new Assessment();
            assessment.setAssessmentId(cursor.getInt(
                    cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry._ID)));
            assessment.setTitle(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.TITLE)));
            assessment.setOaPa(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.OAPA)));
            /*assessment.setDueDate(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.DUEDATE)));*/
            assessment.setPerformance(cursor.getString(
                    cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.PERFORMANCE)));
            assessment.setCourseId(
                    cursor.getInt(cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.COURSEID)));
            assessment.setGoalDate(
                    cursor.getString(cursor.getColumnIndexOrThrow(AssessmentDB.AssessmentEntry.GOALDATE)));
            assessmentList.add(assessment);
        }
        cursor.close();
        return assessmentList;
    }
}
