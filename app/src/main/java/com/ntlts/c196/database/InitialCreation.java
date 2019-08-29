package com.ntlts.c196.database;

import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.ntlts.c196.Assessment;
import com.ntlts.c196.Course;
import com.ntlts.c196.Term;
import com.ntlts.c196.Mentor;

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
        //course.setMentorId(1);
        course.setMentorName("NAMEEE");
        course.setMentorPhone("PHONEEE");
        course.setMentorEmail("EMAIL@EMAIL.COM");
        course.setTermId(1);
        CourseHelper ch = new CourseHelper(this);
        ch.insertCourse(db, course);
    }

    public void createMentor(SQLiteDatabase db){
        Mentor mentor = new Mentor();
        mentor.setName("Mentor 2");
        mentor.setPhone("206.555.1234");
        mentor.setEmail("mentor_2@mentor.com");
        MentorHelper mh = new MentorHelper(this);
        mh.insertMentor(db, mentor);
    }

    public void createAssessment(SQLiteDatabase db){
        Assessment assessment = new Assessment();
        assessment.setTitle("Assessment 1");
        assessment.setOaPa("OA");
        assessment.setPerformance("Passed");
        assessment.setCourseId(1);
        //assessment.setDueDate("2019-08-25");
        assessment.setGoalDate("2019-12-10");
        AssessmentHelper ah = new AssessmentHelper(this);
        ah.insertAssessment(db, assessment);
    }
}
