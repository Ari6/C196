package com.ntlts.c196.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.ntlts.c196.Course;
import com.ntlts.c196.Term;

import java.util.ArrayList;
import java.util.List;

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
                    CourseDB.CourseEntry.MENTORNAME + " TEXT," +
                    CourseDB.CourseEntry.MENTORPHONE + " TEXT," +
                    CourseDB.CourseEntry.MENTOREMAIL + " TEXT," +
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

    public List<Course> getCourseWithTermId(SQLiteDatabase db, long termId){
        String[] projection = {
                BaseColumns._ID,
                CourseDB.CourseEntry.TITLE,
                CourseDB.CourseEntry.START,
                CourseDB.CourseEntry.ANTICIPATED_DATE,
                CourseDB.CourseEntry.DUE_DATE,
                CourseDB.CourseEntry.NOTE,
                CourseDB.CourseEntry.STATUS,
                CourseDB.CourseEntry.MENTORNAME,
                CourseDB.CourseEntry.MENTORPHONE,
                CourseDB.CourseEntry.MENTOREMAIL,
                CourseDB.CourseEntry.TERMID
        };
        String selection = CourseDB.CourseEntry.TERMID + " = ?";
        String[] selectionArgs = {String.valueOf(termId)};
        String sortOrder = CourseDB.CourseEntry.START;
        Cursor cursor = db.query(
                CourseDB.CourseEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        List<Course> courseList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Course course = new Course();
            course.setId((int) cursor.getLong(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry._ID)));
            course.setTitle(cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.TITLE)));
            course.setStart(cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.START)));
            course.setAnticipatedEnd(cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.ANTICIPATED_DATE)));
            course.setDueDate(cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.DUE_DATE)));
            course.setNote(cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.NOTE)));
            course.setStatus(cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.STATUS)));
            //course.setMentorId(cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTORID));
            course.setTermId(cursor.getInt(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.TERMID)));
            course.setMentorName(cursor.getString(
                    cursor.getColumnIndex(CourseDB.CourseEntry.MENTORNAME)));
            course.setMentorEmail(cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTOREMAIL)));
            course.setMentorPhone(cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTORPHONE)));
            courseList.add(course);
        }
        cursor.close();
        return courseList;
    }

    public int insertCourse(SQLiteDatabase db, Course course){
        ContentValues values = new ContentValues();
        values.put(CourseDB.CourseEntry.TITLE, course.getTitle());
        values.put(CourseDB.CourseEntry.START, course.getStart());
        values.put(CourseDB.CourseEntry.ANTICIPATED_DATE, course.getAnticipatedEnd());
        values.put(CourseDB.CourseEntry.DUE_DATE, course.getDueDate());
        values.put(CourseDB.CourseEntry.NOTE, course.getNote());
        values.put(CourseDB.CourseEntry.STATUS, course.getStatus());
        //values.put(CourseDB.CourseEntry.MENTORID, course.getMentorId());
        values.put(CourseDB.CourseEntry.TERMID, course.getTermId());
        values.put(CourseDB.CourseEntry.MENTORNAME, course.getMentorName());
        values.put(CourseDB.CourseEntry.MENTORPHONE, course.getMentorPhone());
        values.put(CourseDB.CourseEntry.MENTOREMAIL, course.getMentorEmail());
        long newRowId = db.insert(CourseDB.CourseEntry.TABLE_NAME, null, values);
        return (int)newRowId;
    }

    public Course getCourse(SQLiteDatabase db, long courseId){
        String[] projection = {
                BaseColumns._ID,
                CourseDB.CourseEntry.TITLE,
                CourseDB.CourseEntry.START,
                CourseDB.CourseEntry.ANTICIPATED_DATE,
                CourseDB.CourseEntry.DUE_DATE,
                CourseDB.CourseEntry.NOTE,
                CourseDB.CourseEntry.STATUS,
                //CourseDB.CourseEntry.MENTORID,
                CourseDB.CourseEntry.MENTORNAME,
                CourseDB.CourseEntry.MENTORPHONE,
                CourseDB.CourseEntry.MENTOREMAIL,
                CourseDB.CourseEntry.TERMID
        };
        String selection = CourseDB.CourseEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(courseId)};
        Cursor cursor = db.query(
                CourseDB.CourseEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Course course = new Course();
        if (cursor.moveToNext()) {
            course.setId(cursor.getInt(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry._ID)));
            course.setTitle(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.TITLE)));
            course.setStart(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.START)));
            course.setAnticipatedEnd(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.ANTICIPATED_DATE)));
            course.setDueDate(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.DUE_DATE)));
            course.setNote(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.NOTE)));
            course.setStatus(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.STATUS)));
            /*course.setMentorId(
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTORID)));*/
            course.setTermId(
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.TERMID)));
            course.setMentorName(cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTORNAME)));
            course.setMentorPhone(cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTORPHONE)));
            course.setMentorEmail(cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTOREMAIL)));
        }
        cursor.close();
        return course;
    }

    public int deleteCourse(SQLiteDatabase db, int courseId){
        String selection = CourseDB.CourseEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(courseId)};
        int deletedRows = db.delete(CourseDB.CourseEntry.TABLE_NAME,
                selection,
                selectionArgs);
        return deletedRows;
    }
    public int updateCourse(SQLiteDatabase db, Course course){
        ContentValues values = new ContentValues();
        values.put(CourseDB.CourseEntry.TITLE, course.getTitle());
        values.put(CourseDB.CourseEntry.START, course.getStart());
        values.put(CourseDB.CourseEntry.ANTICIPATED_DATE, course.getAnticipatedEnd());
        values.put(CourseDB.CourseEntry.DUE_DATE, course.getDueDate());
        values.put(CourseDB.CourseEntry.NOTE, course.getNote());
        values.put(CourseDB.CourseEntry.STATUS, course.getStatus());
        //values.put(CourseDB.CourseEntry.MENTORID, course.getMentorId());
        values.put(CourseDB.CourseEntry.TERMID, course.getTermId());
        values.put(CourseDB.CourseEntry.MENTORNAME, course.getMentorName());
        values.put(CourseDB.CourseEntry.MENTORPHONE, course.getMentorPhone());
        values.put(CourseDB.CourseEntry.MENTOREMAIL, course.getMentorEmail());

        String selection = CourseDB.CourseEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(course.getId())};
        long newRowId = db.update(
                CourseDB.CourseEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return (int)newRowId;
    }
    public List<Course> getAllCourse(SQLiteDatabase db){
        String[] projection = {
                BaseColumns._ID,
                CourseDB.CourseEntry.TITLE,
                CourseDB.CourseEntry.START,
                CourseDB.CourseEntry.ANTICIPATED_DATE,
                CourseDB.CourseEntry.DUE_DATE,
                CourseDB.CourseEntry.NOTE,
                CourseDB.CourseEntry.STATUS,
                //CourseDB.CourseEntry.MENTORID,
                CourseDB.CourseEntry.MENTORNAME,
                CourseDB.CourseEntry.MENTORPHONE,
                CourseDB.CourseEntry.MENTOREMAIL,
                CourseDB.CourseEntry.TERMID
        };

        String sortOrder = CourseDB.CourseEntry.TITLE;
        Cursor cursor = db.query(
                CourseDB.CourseEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        List<Course> courseList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Course course = new Course();
            course.setId((int) cursor.getLong(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry._ID)));
            course.setTitle(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.TITLE)));
            course.setStart(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.START)));
            course.setAnticipatedEnd(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.ANTICIPATED_DATE)));
            course.setDueDate(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.DUE_DATE)));
            course.setNote(cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.NOTE)));
            course.setStatus(
                    cursor.getString(cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.STATUS)));
            //course.setMentorId(cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTORID));
            course.setTermId(cursor.getInt(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.TERMID)));
            course.setMentorName(cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTORNAME)));
            course.setMentorPhone(cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTORPHONE)));
            course.setMentorEmail(cursor.getString(
                    cursor.getColumnIndexOrThrow(CourseDB.CourseEntry.MENTOREMAIL)));
            courseList.add(course);
        }
        cursor.close();
        return courseList;
    }
}
