package com.ntlts.c196;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ntlts.c196.database.AssessmentHelper;
import com.ntlts.c196.database.InitialCreation;

import java.util.List;

public class AssessmentListActivity extends AppCompatActivity 
    implements AssessmentAdapter.OnAssessmentClickListener {
    RecyclerView assessmentRecycleView;
    RecyclerView.LayoutManager layoutManager;
    List<Assessment> assessmentList;
    Toolbar assessmentListToolbar;
    int courseId;
    SQLiteDatabase db;
    AssessmentHelper assessmentHelper;
    FloatingActionButton assessmentAddFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_list_layout);

        //Toolbar
        assessmentListToolbar = findViewById(R.id.assessmentListToolbar);
        setSupportActionBar(assessmentListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //getId from selected
        Intent intent = getIntent();
        courseId = intent.getIntExtra("com.ntlts.c196.COURSEID", 0);
        //database
        assessmentHelper = new AssessmentHelper(this);
        db = assessmentHelper.getWritableDatabase();

        assessmentList = assessmentHelper.getAssessmentWithCourseId(db, courseId);
        assessmentRecycleView = findViewById(R.id.assessmentRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        AssessmentAdapter adapter = new AssessmentAdapter(assessmentList, this);
        assessmentRecycleView.setLayoutManager(layoutManager);
        assessmentRecycleView.setAdapter(adapter);
        assessmentAddFloat = findViewById(R.id.assessmentAddFloat);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, AssessmentDetailActivity.class);
        intent.putExtra(
                "com.ntlts.c196.ASSESSMENTID", assessmentList.get(position).getAssessmentId());
        intent.putExtra("com.ntlts.c196.COURSEID", assessmentList.get(position).getCourseId());
        startActivityForResult(intent, 4);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                db.close();
                Intent intent = new Intent(this, CourseDetailActivity.class);
                intent.putExtra("com.ntlts.c196.COURSEID", courseId);
                setResult(RESULT_OK, intent);
                finish();
                return true; //This true is truly important... otherwise onActiveResult is not called.
            //break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int request, int result, Intent intent) {
        System.out.println("DEBUG OnActivityResult AL");
        super.onActivityResult(request, result, intent);
        //Toolbar
        if (request == 4) {
            if (result == RESULT_OK) {
                //Toolbar
                assessmentListToolbar = findViewById(R.id.assessmentListToolbar);
                setSupportActionBar(assessmentListToolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                //getId from selected
                courseId = intent.getIntExtra("com.ntlts.c196.COURSEID", 0);
                System.out.println("DEBUG AL COURSE: " + courseId);
                //database
                assessmentHelper = new AssessmentHelper(this);
                db = assessmentHelper.getWritableDatabase();

                assessmentList = assessmentHelper.getAssessmentWithCourseId(db, courseId);
                assessmentRecycleView = findViewById(R.id.assessmentRecyclerView);
                layoutManager = new LinearLayoutManager(this);
                AssessmentAdapter adapter = new AssessmentAdapter(assessmentList, this);
                assessmentRecycleView.setLayoutManager(layoutManager);
                assessmentRecycleView.setAdapter(adapter);
                assessmentAddFloat = findViewById(R.id.assessmentAddFloat);
            }
        }
    }

    public void asseessmentAdd(View view){
        Intent intent = new Intent(this, AssessmentDetailActivity.class);
        intent.putExtra("com.ntlts.c196.ADD", true);
        intent.putExtra("com.ntlts.c196.COURSEID", courseId);
        startActivityForResult(intent, 4);
    }

}
