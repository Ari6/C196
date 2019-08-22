package com.ntlts.c196.database;

import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.ntlts.c196.Course;
import com.ntlts.c196.Term;

public class InitialCreation extends AppCompatActivity {
    public void createTerm(SQLiteDatabase db) {
        Term term = new Term();
        term.setTitle("Term 1");
        term.setStartDate("2019-08-20");
        term.setEndDate("2019-11-20");
        TermHelper termHelper = new TermHelper(this);
        termHelper.insertTerm(db, term);
    }

    public void createCourse(SQLiteDatabase db){
        Course course = new Course();
        course.setTitle("Course 1");
        course.setStart("2019-08-21");
        course.setAnticipatedEnd("2019-11-21");
        course.setDueDate("2019-12-31");
        course.setNote("Note");
        course.setStatus("Active");
        course.setMentorId(1);
        course.setTermId(1);
        CourseHelper ch = new CourseHelper(this);
        ch.insertCourse(db, course);
    }
}
