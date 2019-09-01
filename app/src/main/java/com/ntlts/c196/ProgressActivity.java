package com.ntlts.c196;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ntlts.c196.database.AssessmentHelper;
import com.ntlts.c196.database.CourseHelper;
import com.ntlts.c196.database.TermHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProgressActivity extends AppCompatActivity
        implements ProgressAdapter.OnClickListener {
    private TextView progressTermName;
    private TextView progressCourseName;
    private TextView progressAnticipatedEndDate;
    private TextView progressAssessment;
    private Toolbar progressToolbar;
    private SQLiteDatabase termDb;
    private SQLiteDatabase courseDb;
    private SQLiteDatabase assessmentDb;
    private TermHelper th;
    private CourseHelper ch;
    private AssessmentHelper ah;
    private List<Term> termList;
    private List<Course> courseList;
    private List<Assessment> assessmentList;
    private List<Progress> progressList;
    private RecyclerView progressRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);

        //Toolbar
        progressToolbar = findViewById(R.id.progressToolbar);
        setSupportActionBar(progressToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Database
        th = new TermHelper(this);
        ch = new CourseHelper(this);
        ah = new AssessmentHelper(this);
        termDb = th.getReadableDatabase();
        courseDb = ch.getReadableDatabase();
        assessmentDb = ah.getReadableDatabase();

        termList = th.selectAllTerm(termDb);
        courseList = ch.getAllCourse(courseDb);
        assessmentList = ah.getAllAssessment(assessmentDb);
        progressList = makeProgressList(termList, courseList, assessmentList);
        progressList = progressList.stream().sorted(
                Comparator.comparing(Progress::getTermName)
                    .thenComparing(Progress::getCourseName))
                    .collect(Collectors.toList());
        progressRecyclerView = findViewById(R.id.progressRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        ProgressAdapter adapter = new ProgressAdapter(progressList, this);
        progressRecyclerView.setLayoutManager(layoutManager);
        progressRecyclerView.setAdapter(adapter);


    }
    private List<Progress> makeProgressList(
            List<Term> termList, List<Course> courseList, List<Assessment> assessmentList){
        List<Progress> progressListTemp = new ArrayList<>();
        //assessmentList.stream().map(Assessment::getCourseId).forEach(System.out::println);
        assessmentList = assessmentList.stream().sorted((a, b)->a.getCourseId()-b.getCourseId()).collect(Collectors.toList());

        for(Course course : courseList) {
            Progress progress = new Progress();
            progress.setCourseName(course.getTitle());
            progress.setAnticipatedEndDate(course.getAnticipatedEnd());
            for (Term term : termList) {
                //System.out.println("DEBUG PA C-term: T-term: " + course.getTermId() +" : " + term.getId());
                if (course.getTermId() == term.getId()) {
                    //Log.d("PA TERMNAME", term.getTitle());
                    progress.setTermName(term.getTitle());
                    break;
                }
            }
            int total = 0, passed = 0;
            int preKey = 0;
            for(Assessment assessment : assessmentList){
                if(course.getId() == assessment.getCourseId()) {
                    total++;
                    if (assessment.getCourseId() == preKey) {
                        passed++;
                    } else {
                        preKey = assessment.getCourseId();
                    }
                }
            }
            progress.setAssessmentProgress(passed + " / " + total);
            //System.out.println("DEBUG PA Progress: " + progress.getTermName() + " / " + progress.getAssessmentProgress());
            progressListTemp.add(progress);
        }
        return progressListTemp;
    }

    @Override
    public void onClick(int position) {

    }
}
