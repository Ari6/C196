package com.ntlts.c196;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;

import com.ntlts.c196.database.AssessmentDB;
import com.ntlts.c196.database.AssessmentHelper;
import com.ntlts.c196.database.CourseHelper;

import java.util.Calendar;
import java.util.List;

public class AssessmentDetailActivity extends AppCompatActivity {
    private Toolbar assessmentDetailToolbar;
    private EditText assessmentTitleEdit;
    private EditText assessmentPerformance;
    private TextView assessmentDueDate;
    private TextView assessmentNote;
    private EditText assessmentGoalDate;
    private Spinner spinnerOaPa;
    private Spinner spinnerCourse;
    int assessmentId;
    int courseId;
    private String[] oaPaList = {"OA", "PA"};
    private List<Course> courseList;
    AssessmentHelper ah;
    SQLiteDatabase assessmentDb;
    boolean ret = false;
    CourseHelper ch;
    SQLiteDatabase courseDb;
    private ShareActionProvider shareActionProvider;
    DatePickerDialog.OnDateSetListener goalDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_detail);

        //Toolbar
        assessmentDetailToolbar = findViewById(R.id.assessmentDetailToolbar);
        setSupportActionBar(assessmentDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //getData
        Intent intent = getIntent();
        assessmentId = intent.getIntExtra("com.ntlts.c196.ASSESSMENTID", 0);
        ah = new AssessmentHelper(this);
        assessmentDb = ah.getWritableDatabase();
        final Assessment assessment = ah.getAssessment(assessmentDb, assessmentId);
        ch = new CourseHelper(this);
        courseDb = ch.getReadableDatabase();
        Course courseForDue = ch.getCourse(courseDb, assessment.getCourseId());
        //setIDs
        assessmentTitleEdit = findViewById(R.id.assessmentTitleEdit);
        assessmentPerformance = findViewById(R.id.assessmentPerformanceEdit);
        assessmentDueDate = findViewById(R.id.assessmentDueDateEdit);
        assessmentGoalDate = findViewById(R.id.assessmentGoalDateEdit);
        assessmentNote = findViewById(R.id.assessmentNote);
        assessmentTitleEdit.setText(assessment.getTitle());
        assessmentPerformance.setText(assessment.getPerformance());
        //assessmentDueDate.setText(assessment.getDueDate());
        assessmentDueDate.setText(courseForDue.getDueDate());
        assessmentGoalDate.setText(assessment.getGoalDate());
        assessmentNote.setText(courseForDue.getNote());

        //spinner OA/PA
        spinnerOaPa = findViewById(R.id.spinnerOaPa);
        ArrayAdapter<String> oaPaAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item);
        for(String str: oaPaList){
            oaPaAdapter.add(str);
        }
        spinnerOaPa.setAdapter(oaPaAdapter);

        //set selected spinnerOA/PA
        int spinnerOaPaPosition = 0;
        for(int i = 0; i < oaPaList.length; i++){
            if(oaPaList[i].equals(assessment.getOaPa())){
                spinnerOaPaPosition = i;
                break;
            }
        }
        spinnerOaPa.setSelection(spinnerOaPaPosition);

        //spinner Course
        spinnerCourse = findViewById(R.id.spinnerCourse);
        courseList = ch.getAllCourse(courseDb);
        ArrayAdapter<String> courseAdapter =
                new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        for(Course course: courseList){
            courseAdapter.add(course.getTitle());
        }
        spinnerCourse.setAdapter(courseAdapter);

        //set selected spinner Course
        int spinnerCoursePosition = 0;
        for(int i = 0; i < courseList.size(); i++){
            if(courseList.get(i).getId() == assessment.getCourseId()){
                spinnerCoursePosition = i;
                break;
            }
        }
        spinnerCourse.setSelection(spinnerCoursePosition);
        if(intent.getBooleanExtra("com.ntlts.c196.ADD", false) == false){
            /*
            date for Goal
            */
            assessmentGoalDate = findViewById(R.id.assessmentGoalDateEdit);
            assessmentGoalDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int year = Integer.parseInt(assessment.getGoalDate().substring(0,4));
                    int month = Integer.parseInt(assessment.getGoalDate().substring(5,7).replace(" ", "0"))-1;
                    int day = Integer.parseInt(assessment.getGoalDate().substring(8));

                    DatePickerDialog dialog = new DatePickerDialog(
                            AssessmentDetailActivity.this,
                            R.style.Theme_AppCompat,
                            goalDateSetListener,
                            year, month, day
                    );
                    dialog.show();
                }
            });
            goalDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    String date = year + "-" + month + "-" + day;
                    assessmentGoalDate.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
        } else {
                        /*
            date for Goal
            */
            assessmentGoalDate = findViewById(R.id.assessmentGoalDateEdit);
            assessmentGoalDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            AssessmentDetailActivity.this,
                            R.style.Theme_AppCompat,
                            goalDateSetListener,
                            year, month, day
                    );
                    dialog.show();
                }
            });
            goalDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    String date = year + "-" + month + "-" + day;
                    assessmentGoalDate.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
        }
    }
    public void assessmentSaveButtonOnClick(View view){
        Assessment assessment = new Assessment();
        assessment.setAssessmentId(assessmentId);
        assessment.setTitle(assessmentTitleEdit.getText().toString());
        assessment.setGoalDate(assessmentGoalDate.getText().toString());
        //assessment.setDueDate(assessmentDueDate.getText().toString());
        assessment.setPerformance(assessmentPerformance.getText().toString());
        assessment.setCourseId(courseList.get(spinnerCourse.getSelectedItemPosition()).getId());
        assessment.setOaPa(oaPaList[spinnerOaPa.getSelectedItemPosition()]);
        Intent intent = getIntent();
        if(intent.getBooleanExtra("com.ntlts.c196.ADD", false) == false) {
            ah.updateAssessment(assessmentDb, assessment);
            popupUpdated("The record has been updated.");
        } else {
            assessmentId = ah.insertAssessment(assessmentDb, assessment);
            popupUpdated("The record has been added.");
        }
    }

    public void assessmentDeleteButtonOnClick(View view){
        Intent intent = getIntent();
        if(intent.getBooleanExtra("com.ntlts.c196.ADD", false) == false) {
            if (popupDeleted(this)) {
                ah.deleteAssessment(assessmentDb, assessmentId);
                popupUpdated("The record has been deleted.");
                assessmentDb.close();
                courseDb.close();
                Intent intent2 = new Intent(this, AssessmentListActivity.class);
                intent.putExtra("com.ntlts.c196.COURSEID", courseId);
                startActivity(intent2);
            }
        }
    }
    private void popupUpdated(String message){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message message){
                throw new RuntimeException();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        handler.sendMessage(handler.obtainMessage());
                    }
                }).show();
        try {
            Looper.loop();
        } catch (RuntimeException e){}
    }

    public boolean popupDeleted(Context context) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                throw new RuntimeException();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ret = true;
                        handler.sendMessage(handler.obtainMessage());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        ret = false;
                        handler.sendMessage(handler.obtainMessage());
                    }
                }).show();
        try{
            Looper.loop();
        } catch (RuntimeException e){}
        return ret;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.println("DEBUG AD ID: " + id);
        Assessment assessment;
        switch (id) {
            case android.R.id.home:
                assessment = ah.getAssessment(assessmentDb, assessmentId);
                assessmentDb.close();
                courseDb.close();
                Intent intent = new Intent(this, AssessmentListActivity.class);
                System.out.println("DEBUG AD COURSE: " + assessment.getCourseId());
                intent.putExtra("com.ntlts.c196.COURSEID", assessment.getCourseId());
                intent.putExtra("com.ntlts.c196.ADD", false);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        shareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        //this below
        Assessment assessment = ah.getAssessment(assessmentDb, assessmentId);
        String str = assessment.getTitle() + "," + assessment.getOaPa() + "," +
                assessment.getOaPa() +"," +
                assessment.getGoalDate();
        Intent shareIntent = ShareCompat.IntentBuilder.from(this).setType("text/plain")
                .setText(str).getIntent();
        shareActionProvider.setShareIntent(shareIntent);
        return true;
    }
}
