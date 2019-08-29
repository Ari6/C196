package com.ntlts.c196;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ntlts.c196.database.AssessmentHelper;
import com.ntlts.c196.database.CourseHelper;
import com.ntlts.c196.database.InitialCreation;
import com.ntlts.c196.database.MentorHelper;
import com.ntlts.c196.database.TermHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetailActivity extends AppCompatActivity
        implements AssessmentAdapter.OnAssessmentClickListener {

    EditText courseAnticipatedEnd;
    EditText courseNoteText;
    EditText courseTitleText;
    EditText courseDueDate;
    EditText courseStartText;
    EditText courseStatus;
    Spinner spinner;
    //Spinner spinnerMentor;
    EditText mentorName;
    EditText mentorEmail;
    EditText mentorPhone;
    RecyclerView courseAssessmentRecyclerView;
    String[] STATUS = {"in Progress", "completed", "dropped", "plan to take"};
    Spinner spinnerStatus;

    //TextView courseMentor;
    int courseId;
    //int mentorId;
    boolean ret = false;
    SQLiteDatabase courseDb;
    CourseHelper ch;
    //SQLiteDatabase mentorDb;
    //MentorHelper mh;
    List<Term> termList;
    private int termId;
    Toolbar courseDetailToolbar;
    Course course;
    //List<Mentor> mentorList;
    List<Assessment> assessmentList;
    AssessmentHelper assessmentHelper;
    SQLiteDatabase  assessmentDb;
    RecyclerView.LayoutManager assessmentLayoutManager;
    ShareActionProvider shareActionProvider;
    ToggleButton courseAlert;
    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener anticipatedDateSetListener;
    private DatePickerDialog.OnDateSetListener dueDateSetListener;
    private Button courseOn;
    private Button courseOff;
    private Button courseDeleteButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);
        //Toolbar
        courseDetailToolbar = findViewById(R.id.courseDetailToolbar);
        setSupportActionBar(courseDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        courseId = intent.getIntExtra("com.ntlts.c196.COURSEID", 0);
        /*
        Database section
         */
        ch = new CourseHelper(this);
        courseDb = ch.getWritableDatabase();
        course = ch.getCourse(courseDb, courseId);

        TermHelper th = new TermHelper(this);
        SQLiteDatabase termDb = th.getWritableDatabase();
        /*
        Set spinner
         */
        termList = th.selectAllTerm(termDb);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item);
        for(Term term: termList){
            adapter.add(term.getTitle());
        }
        spinner.setAdapter(adapter);
        int spinnerPosition = 0;
        for(int i = 0; i < termList.size(); i++){
            if(termList.get(i).getId() == course.getTermId()){
                spinnerPosition = i;
                break;
            }
        }
        spinner.setSelection(spinnerPosition);
        //Spinner for Status
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item);
        for(String str: STATUS){
            adapterStatus.add(str);
        }
        spinnerStatus.setAdapter(adapterStatus);
        int spinnerStatusPosition = 0;
        for(int i = 0; i< STATUS.length; i++){
            if(STATUS[i].equals(course.getStatus())){
                spinnerStatusPosition = i;
                break;
            }
        }
        spinnerStatus.setSelection(spinnerStatusPosition);

        /*
        set data to screen
         */
        courseTitleText = findViewById(R.id.courseTitleText);
        courseStartText = findViewById(R.id.courseStartText);
        courseAnticipatedEnd = findViewById(R.id.courseAnticipatedEnd);

        //courseStatus = findViewById(R.id.courseStatus);
        courseNoteText = findViewById(R.id.courseNoteText);
        //spinnerMentor = findViewById(R.id.spinnerMentor);
        mentorName = findViewById(R.id.mentorName);
        mentorPhone = findViewById(R.id.mentorPhone);
        mentorEmail = findViewById(R.id.mentorEmail);
        courseAssessmentRecyclerView = findViewById(R.id.courseAssessmentRecyclerView);
        //Toggle Button
        /*courseAlert = findViewById(R.id.courseAlert);
        courseAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent intent=new Intent(CourseDetailActivity.this, MyReceiver.class);
                    intent.putExtra("com.ntlts.c196.FROM", "START");
                    Intent intentEnd = new Intent(CourseDetailActivity.this, MyReceiver.class);
                    intentEnd.putExtra("com.ntlts.c196.FROM", "END");
                    PendingIntent sender= PendingIntent.getBroadcast(CourseDetailActivity.this,1,intent,0);
                    AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    PendingIntent senderEnd= PendingIntent.getBroadcast(CourseDetailActivity.this,2,intentEnd,0);
                    AlarmManager alarmManagerEnd=(AlarmManager)getSystemService(Context.ALARM_SERVICE);                    //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000, sender);
                    String startStr = course.getStart(); //yyyy-MM-dd
                    String endStr = course.getAnticipatedEnd();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    long milliStart = 0;
                    long milliEnd = 0;
                    try{
                        Date startDate = sdf.parse(startStr);
                        Date endDate = sdf.parse(endStr);
                        milliStart = startDate.getTime();
                        milliEnd = endDate.getTime();
                    } catch (ParseException e){
                        e.printStackTrace();
                    }
                    alarmManager.set(AlarmManager.RTC_WAKEUP, milliStart, sender);
                    alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, milliEnd, senderEnd);
                } else {
                    // The toggle is disabled
                    Intent intent = new Intent(CourseDetailActivity.this, MyReceiver.class);
                    PendingIntent sender= PendingIntent.getBroadcast(CourseDetailActivity.this,1,intent,0);
                    AlarmManager alarmManage= (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManage.cancel(sender);
                    Intent intent2 = new Intent(CourseDetailActivity.this, MyReceiver.class);
                    PendingIntent sender2 = PendingIntent.getBroadcast(CourseDetailActivity.this,2,intent2,0);
                    AlarmManager alarmManage2 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManage2.cancel(sender2);
                }
            }
        });
        */
        if(intent.getBooleanExtra("com.ntlts.c196.ADD",false) == false) {
            courseTitleText.setText(course.getTitle());
            courseAnticipatedEnd.setText(course.getAnticipatedEnd());
            //courseDueDate.setText(course.getDueDate());
            //courseStatus.setText(course.getStatus());
            courseNoteText.setText(course.getNote());
            mentorName.setText(course.getMentorName());
            mentorPhone.setText(course.getMentorPhone());
            mentorEmail.setText(course.getMentorEmail());
            //mentorId = course.getMentorId();
            termId = course.getTermId();
            assessmentHelper = new AssessmentHelper(this);
            assessmentDb = assessmentHelper.getWritableDatabase();
            assessmentList = assessmentHelper.getAssessmentWithCourseId(assessmentDb, courseId);
            courseAssessmentRecyclerView = findViewById(R.id.courseAssessmentRecyclerView);
            assessmentLayoutManager = new LinearLayoutManager(this);
            AssessmentAdapter assessmentAdapter = new AssessmentAdapter(assessmentList, this);
            courseAssessmentRecyclerView.setLayoutManager(assessmentLayoutManager);
            courseAssessmentRecyclerView.setAdapter(assessmentAdapter);
            /*
            date for start
             */
            courseStartText.setText(course.getStart());
            courseStartText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int year = Integer.parseInt(course.getStart().substring(0,4));
                    int month = Integer.parseInt(course.getStart().substring(5,7).replace(" ", "0"))-1;
                    int day = Integer.parseInt(course.getStart().substring(8));

                    DatePickerDialog dialog = new DatePickerDialog(
                            CourseDetailActivity.this,
                            R.style.Theme_AppCompat,
                            startDateSetListener,
                            year, month, day
                    );

                    dialog.show();
                }
            });
            startDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    Log.d("CourseDetailActivity", "onDateSet: ");
                    String date = year + "-" + month + "-" + day;
                    courseStartText.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
                        /*
            date for anticipated
             */
            courseAnticipatedEnd.setText(course.getAnticipatedEnd());
            courseAnticipatedEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int year = Integer.parseInt(course.getAnticipatedEnd().substring(0,4));
                    int month = Integer.parseInt(course.getAnticipatedEnd().substring(5,7).replace(" ", "0"))-1;
                    int day = Integer.parseInt(course.getAnticipatedEnd().substring(8));

                    DatePickerDialog dialog = new DatePickerDialog(
                            CourseDetailActivity.this,
                            R.style.Theme_AppCompat,
                            anticipatedDateSetListener,
                            year, month, day
                    );

                    dialog.show();
                }
            });
            anticipatedDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    String date = year + "-" + month + "-" + day;
                    courseAnticipatedEnd.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
            /*
            date for due
            */
            courseDueDate = findViewById(R.id.courseDueDate);
            courseDueDate.setText(course.getDueDate());
            courseDueDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int year = Integer.parseInt(course.getDueDate().substring(0,4));
                    int month = Integer.parseInt(course.getDueDate().substring(5,7).replace(" ", "0"))-1;
                    int day = Integer.parseInt(course.getDueDate().substring(8));

                    DatePickerDialog dialog = new DatePickerDialog(
                            CourseDetailActivity.this,
                            R.style.Theme_AppCompat,
                            dueDateSetListener,
                            year, month, day
                    );

                    dialog.show();
                }
            });
            dueDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    Log.d("CourseDetailActivity", "onDateSet: ");
                    String date = year + "-" + month + "-" + day;
                    courseDueDate.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
        } else { //When add
            termId = intent.getIntExtra("com.ntlts.c196.TERMID", 0);
            /*
            Disable buttons
             */
            courseOn = findViewById(R.id.courseOn);
            courseOff = findViewById(R.id.courseOff);
            courseDeleteButton = findViewById(R.id.courseDeleteButton);
            courseOn.setEnabled(false);
            courseOff.setEnabled(false);
            courseDeleteButton.setEnabled(false);
            /*
            date for start
             */
            courseStartText.setText(course.getStart());
            courseStartText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            CourseDetailActivity.this,
                            R.style.Theme_AppCompat,
                            startDateSetListener,
                            year, month, day
                    );

                    dialog.show();
                }
            });
            startDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    Log.d("CourseDetailActivity", "onDateSet: ");
                    String date = year + "-" + month + "-" + day;
                    courseStartText.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
                        /*
            date for anticipated
             */
            courseAnticipatedEnd.setText(course.getAnticipatedEnd());
            courseAnticipatedEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            CourseDetailActivity.this,
                            R.style.Theme_AppCompat,
                            anticipatedDateSetListener,
                            year, month, day
                    );

                    dialog.show();
                }
            });
            anticipatedDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    String date = year + "-" + month + "-" + day;
                    courseAnticipatedEnd.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
            /*
            date for due
            */
            courseDueDate = findViewById(R.id.courseDueDate);
            courseDueDate.setText(course.getDueDate());
            courseDueDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            CourseDetailActivity.this,
                            R.style.Theme_AppCompat,
                            dueDateSetListener,
                            year, month, day
                    );

                    dialog.show();
                }
            });
            dueDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    Log.d("CourseDetailActivity", "onDateSet: ");
                    String date = year + "-" + month + "-" + day;
                    courseDueDate.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
        }

    }

    public void courseSaveButtonOnClick(View view){
        if(!(courseStartText.getText().toString().equals("")) &&
                !(courseAnticipatedEnd.getText().toString().equals("")) &&
                !(courseDueDate.getText().toString().equals(""))) {
            Course course = new Course();
            course.setId(courseId);
            course.setTitle(courseTitleText.getText().toString());
            course.setStart(courseStartText.getText().toString());
            course.setAnticipatedEnd(courseAnticipatedEnd.getText().toString());
            course.setDueDate(courseDueDate.getText().toString());
            //course.setStatus(courseStatus.getText().toString());
            course.setStatus(spinnerStatus.getSelectedItem().toString());
            course.setNote(courseNoteText.getText().toString());
            course.setMentorName(mentorName.getText().toString());
            course.setMentorPhone(mentorPhone.getText().toString());
            course.setMentorEmail(mentorEmail.getText().toString());
            //course.setMentorId((int)mentorList.get(spinnerMentor.getSelectedItemPosition()).getMentorId());
            course.setTermId(termList.get(spinner.getSelectedItemPosition()).getId());
            Intent intent2 = getIntent();
            if (intent2.getBooleanExtra("com.ntlts.c196.ADD", false) == false) {
                ch.updateCourse(courseDb, course);
                termId = ch.getCourse(courseDb, course.getId()).getTermId();
                popupUpdated("The record has been updated.");
            } else {
                courseId = ch.insertCourse(courseDb, course);
                termId = ch.getCourse(courseDb, course.getId()).getTermId();
                popupUpdated("The record has been added.");
            }
        } else {
            popupUpdated("Please set Start, anticipated, and due dates.");
        }
    }

    public void assignmentDetailOnClick(View view){
        Intent intent = new Intent(this, AssessmentListActivity.class);
        intent.putExtra("com.ntlts.c196.COURSEID", course.getId());
        startActivityForResult(intent, 3);
    }

    public void courseDeleteButtonOnClick(View view){
        if(popupDeleted(this)){
            ch.deleteCourse(courseDb, courseId);
            popupUpdated("The record has been deleted.");
            courseDb.close();
            //mentorDb.close();
            Intent intent = new Intent(this, CourseListActivity.class);
            intent.putExtra("com.ntlts.c196.TERMID", termId);
            //startActivity(intent);
            finish();
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
        switch (id) {
            case android.R.id.home:
                Course course = ch.getCourse(courseDb, courseId);
                courseDb.close();
                Intent intent = new Intent();
                System.out.println("DEBUG CD TERMID: " + termId);
                intent.putExtra("com.ntlts.c196.TERMID", termId);
                intent.putExtra("com.ntlts.c196.ADD", false);
                setResult(RESULT_OK, intent);
                finish();
                return true; //This true is truly important... otherwise onActiveResult is not called.
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int request, int result, Intent intent) {
        //Toolbar
        if(request == 3) {
            if (result == RESULT_OK) {
                courseDetailToolbar = findViewById(R.id.courseDetailToolbar);
                setSupportActionBar(courseDetailToolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                courseId = intent.getIntExtra("com.ntlts.c196.COURSEID", 0);
        /*
        Database section
         */
                ch = new CourseHelper(this);
                courseDb = ch.getWritableDatabase();
                course = ch.getCourse(courseDb, courseId);

                TermHelper th = new TermHelper(this);
                SQLiteDatabase termDb = th.getWritableDatabase();
        /*
        Set spinner
         */
                termList = th.selectAllTerm(termDb);
                spinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, R.layout.support_simple_spinner_dropdown_item);
                for (Term term : termList) {
                    adapter.add(term.getTitle());
                }
                spinner.setAdapter(adapter);
                int spinnerPosition = 0;
                for (int i = 0; i < termList.size(); i++) {
                    if (termList.get(i).getId() == course.getTermId()) {
                        spinnerPosition = i;
                        break;
                    }
                }
                spinner.setSelection(spinnerPosition);
                //Spinner for Status
                spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
                ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(
                        this, R.layout.support_simple_spinner_dropdown_item);
                for(String str: STATUS){
                    adapterStatus.add(str);
                }
                spinnerStatus.setAdapter(adapterStatus);
                int spinnerStatusPosition = 0;
                for(int i = 0; i< STATUS.length; i++){
                    if(STATUS[i].equals(course.getStatus())){
                        spinnerStatusPosition = i;
                        break;
                    }
                }
                spinnerStatus.setSelection(spinnerStatusPosition);
        /*
        set data to screen
         */
                courseTitleText = findViewById(R.id.courseTitleText);
                courseStartText = findViewById(R.id.courseStartText);
                courseAnticipatedEnd = findViewById(R.id.courseAnticipatedEnd);
                courseDueDate = findViewById(R.id.courseDueDate);
                //courseStatus = findViewById(R.id.courseStatus);
                courseNoteText = findViewById(R.id.courseNoteText);
                mentorName = findViewById(R.id.mentorName);
                mentorPhone = findViewById(R.id.mentorPhone);
                mentorEmail = findViewById(R.id.mentorEmail);

                courseTitleText.setText(course.getTitle());
                //courseStartText.setText(course.getStart());
                //courseAnticipatedEnd.setText(course.getAnticipatedEnd());
                //courseDueDate.setText(course.getDueDate());
                //courseStatus.setText(course.getStatus());
                courseNoteText.setText(course.getNote());
                mentorName.setText(course.getMentorName());
                mentorPhone.setText(course.getMentorPhone());
                mentorEmail.setText(course.getMentorEmail());
                termId = course.getTermId();
                /*
                date for start
                */
                courseStartText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int year = Integer.parseInt(course.getStart().substring(0,4));
                        int month = Integer.parseInt(course.getStart().substring(5,7).replace(" ", "0"))-1;
                        int day = Integer.parseInt(course.getStart().substring(8));

                        DatePickerDialog dialog = new DatePickerDialog(
                                CourseDetailActivity.this,
                                R.style.Theme_AppCompat,
                                startDateSetListener,
                                year, month, day
                        );
                        dialog.show();
                    }
                });
                startDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month += 1;
                        String date = year + "-" + month + "-" + day;
                        courseStartText.setText(String.format("%4d-%02d-%02d", year, month, day));
                    }
                };
                        /*
            date for anticipated
             */
                //courseAnticipatedEnd.setText(course.getAnticipatedEnd());
                courseAnticipatedEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int year = Integer.parseInt(course.getAnticipatedEnd().substring(0,4));
                        int month = Integer.parseInt(course.getAnticipatedEnd().substring(5,7).replace(" ", "0"))-1;
                        int day = Integer.parseInt(course.getAnticipatedEnd().substring(8));

                        DatePickerDialog dialog = new DatePickerDialog(
                                CourseDetailActivity.this,
                                R.style.Theme_AppCompat,
                                anticipatedDateSetListener,
                                year, month, day
                        );

                        dialog.show();
                    }
                });
                anticipatedDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month += 1;
                        String date = year + "-" + month + "-" + day;
                        courseAnticipatedEnd.setText(String.format("%4d-%02d-%02d", year, month, day));
                    }
                };
            /*
            date for due
            */
                courseDueDate = findViewById(R.id.courseDueDate);
                courseDueDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int year = Integer.parseInt(course.getDueDate().substring(0,4));
                        int month = Integer.parseInt(course.getDueDate().substring(5,7).replace(" ", "0"))-1;
                        int day = Integer.parseInt(course.getDueDate().substring(8));

                        DatePickerDialog dialog = new DatePickerDialog(
                                CourseDetailActivity.this,
                                R.style.Theme_AppCompat,
                                dueDateSetListener,
                                year, month, day
                        );

                        dialog.show();
                    }
                });
                dueDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month += 1;
                        Log.d("CourseDetailActivity", "onDateSet: ");
                        String date = year + "-" + month + "-" + day;
                        courseDueDate.setText(String.format("%4d-%02d-%02d", year, month, day));
                    }
                };
            }
        }
    }
    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, AssessmentDetailActivity.class);
        intent.putExtra(
                "com.ntlts.c196.ASSESSMENTID", assessmentList.get(position).getAssessmentId());
        startActivityForResult(intent, 4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        shareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        //this below
        System.out.println("DEBUG CD ONCREATE COURSEID: " + courseId);
        Course course = ch.getCourse(courseDb, courseId);
        String str = course.getNote();
        Intent shareIntent = ShareCompat.IntentBuilder.from(this).setType("text/plain")
                .setText(str).getIntent();
        shareActionProvider.setShareIntent(shareIntent);
        return true;
    }
    public void courseOnOnClick(View view ){
        Intent intent=new Intent(CourseDetailActivity.this, MyReceiver.class);
        intent.putExtra("com.ntlts.c196.FROM", "START");
        Intent intentEnd = new Intent(CourseDetailActivity.this, MyReceiver.class);
        intentEnd.putExtra("com.ntlts.c196.FROM", "END");
        PendingIntent sender= PendingIntent.getBroadcast(CourseDetailActivity.this,1,intent,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        PendingIntent senderEnd= PendingIntent.getBroadcast(CourseDetailActivity.this,2,intentEnd,0);
        AlarmManager alarmManagerEnd=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        String startStr = course.getStart(); //yyyy-MM-dd
        String endStr = course.getAnticipatedEnd();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long milliStart = 0;
        long milliEnd = 0;
        try{
            Date startDate = sdf.parse(startStr);
            Date endDate = sdf.parse(endStr);
            milliStart = startDate.getTime();
            milliEnd = endDate.getTime();
        } catch (ParseException e){
            e.printStackTrace();
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, milliStart, sender);
        alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, milliEnd, senderEnd);
    }
    public void courseOffOnClick(View view){
        Intent intent = new Intent(CourseDetailActivity.this, MyReceiver.class);
        PendingIntent sender= PendingIntent.getBroadcast(CourseDetailActivity.this,1,intent,0);
        AlarmManager alarmManage= (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManage.cancel(sender);
        Intent intent2 = new Intent(CourseDetailActivity.this, MyReceiver.class);
        PendingIntent sender2 = PendingIntent.getBroadcast(CourseDetailActivity.this,2,intent2,0);
        AlarmManager alarmManage2 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManage2.cancel(sender2);
    }
}
