package com.ntlts.c196;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_layout);

        //getId from selected
        Intent intent = getIntent();
        int id = intent.getIntExtra("com.ntlts.c196.TERMID", 0);
        //database
        CourseHelper courseHelper = new CourseHelper(this);
        SQLiteDatabase db = courseHelper.getWritableDatabase();
        //DEMO
        //InitialCreation ic = new InitialCreation();
        //ic.createCourse(db);
        //DEMO
        courseList = courseHelper.getCourseWithTermId(db, id);
        courseRecycleView = findViewById(R.id.couseRecycleView);
        layoutManager = new LinearLayoutManager(this);
        CourseAdapter adapter = new CourseAdapter(courseList, this);
        courseRecycleView.setLayoutManager(layoutManager);
        courseRecycleView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        startActivity(intent);
    }
}
