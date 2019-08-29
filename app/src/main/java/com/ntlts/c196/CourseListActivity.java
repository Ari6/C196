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

import com.ntlts.c196.database.CourseHelper;
import com.ntlts.c196.database.InitialCreation;

import java.util.List;

public class CourseListActivity extends AppCompatActivity
        implements CourseAdapter.OnCourseClickListener {
    RecyclerView courseRecycleView;
    RecyclerView.LayoutManager layoutManager;
    List<Course> courseList;
    Toolbar courseListToolbar;
    int termId;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_layout);

        //Toolbar
        courseListToolbar = findViewById(R.id.courseListToolbar);
        setSupportActionBar(courseListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //getId from selected
        Intent intent = getIntent();
        termId = intent.getIntExtra("com.ntlts.c196.TERMID", 0);
        //database
        CourseHelper courseHelper = new CourseHelper(this);
        db = courseHelper.getWritableDatabase();
        //DEMO
        //InitialCreation ic = new InitialCreation();
        //ic.createCourse(db);
        //DEMO
        courseList = courseHelper.getCourseWithTermId(db, termId);
        courseRecycleView = findViewById(R.id.couseRecycleView);
        layoutManager = new LinearLayoutManager(this);
        CourseAdapter adapter = new CourseAdapter(courseList, this);
        courseRecycleView.setLayoutManager(layoutManager);
        courseRecycleView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra(
                "com.ntlts.c196.COURSEID", courseList.get(position).getId());
        intent.putExtra("com.ntlts.c196.ADD", false);
        startActivityForResult(intent, 2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                db.close();
                Intent intent = new Intent(this, TermDetailActivity.class);
                intent.putExtra("com.ntlts.c196.TERMID", termId);
                setResult(RESULT_OK, intent);
                finish();
                return true; //This true is truly important... otherwise onActiveResult is not called.
                //break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int request, int result, Intent intent) {
        System.out.println("DEBUG OnActivityResult CL");
        super.onActivityResult(request, result, intent);
        //Toolbar
        if (request == 2) {
            if (result == RESULT_OK) {
                courseListToolbar = findViewById(R.id.courseListToolbar);
                setSupportActionBar(courseListToolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                //getId from selected
                termId = intent.getIntExtra("com.ntlts.c196.TERMID", 0);
                System.out.println("DEBUG CL TERMID: " + termId);
                //database
                CourseHelper courseHelper = new CourseHelper(this);
                db = courseHelper.getWritableDatabase();
                courseList = courseHelper.getCourseWithTermId(db, termId);
                /*
                RecyclerView
                 */
                courseRecycleView = findViewById(R.id.couseRecycleView);
                layoutManager = new LinearLayoutManager(this);
                CourseAdapter adapter = new CourseAdapter(courseList, this);
                courseRecycleView.setLayoutManager(layoutManager);
                courseRecycleView.setAdapter(adapter);
            }
        }
    }

    public void courseAdd(View view){
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra("com.ntlts.c196.ADD", true);
        startActivityForResult(intent, 2);
    }
}
