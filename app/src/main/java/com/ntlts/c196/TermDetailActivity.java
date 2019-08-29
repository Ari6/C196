package com.ntlts.c196;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ntlts.c196.database.CourseHelper;
import com.ntlts.c196.database.TermHelper;

import java.util.Calendar;

public class TermDetailActivity extends AppCompatActivity {
    private Button termSave;
    private Button termDetail;
    private Button termDelete;
    private EditText termTitleEdit;
    private EditText termStartEdit;
    private EditText termEndEdit;
    private int termId;
    SQLiteDatabase db;
    boolean ret = false;
    Toolbar termDetailToolbar;
    Term term;
    DatePickerDialog.OnDateSetListener startDateSetListener;
    DatePickerDialog.OnDateSetListener endDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail);
        //Toolbar
        termDetailToolbar = findViewById(R.id.termDetailToolbar);
        setSupportActionBar(termDetailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //db
        TermHelper termHelper = new TermHelper(this);
        db = termHelper.getWritableDatabase();
        Intent intent = getIntent();
        termTitleEdit = findViewById(R.id.termTitleEdit);
        termStartEdit = findViewById(R.id.termStartEdit);
        termEndEdit = findViewById(R.id.termEndEdit);
        termSave = findViewById(R.id.termSave);
        termDetail = findViewById(R.id.termDetail);
        termDelete = findViewById(R.id.termDelete);

        termId = intent.getIntExtra("com.ntlts.c196.TERMID", 0);
        if(intent.getBooleanExtra("com.ntlts.c196.ADD", false) == false) {
            term = termHelper.selectTerm(db, termId);
            termTitleEdit.setText(term.getTitle());
            termStartEdit.setText(term.getStartDate());
            termEndEdit.setText(term.getEndDate());

        /*
        date for start
         */
            termStartEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int year = Integer.parseInt(term.getStartDate().substring(0, 4));
                    int month = Integer.parseInt(term.getStartDate().substring(5, 7).replace(" ", "0")) - 1;
                    int day = Integer.parseInt(term.getStartDate().substring(8));

                    DatePickerDialog dialog = new DatePickerDialog(
                            TermDetailActivity.this,
                            R.style.Theme_AppCompat,
                            startDateSetListener,
                            year, month, day
                    );
                    //dialog.getWindow().setBackgroundDrawable(
                    //      new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });
            startDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    Log.d("CourseDetailActivity", "onDateSet: ");
                    String date = year + "-" + month + "-" + day;
                    termStartEdit.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
            /*
            date for end
             */
            termEndEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int year = Integer.parseInt(term.getEndDate().substring(0, 4));
                    int month = Integer.parseInt(term.getEndDate().substring(5, 7).replace(" ", "0")) - 1;
                    int day = Integer.parseInt(term.getEndDate().substring(8));

                    DatePickerDialog dialog = new DatePickerDialog(
                            TermDetailActivity.this,
                            R.style.Theme_AppCompat,
                            endDateSetListener,
                            year, month, day
                    );
                    //dialog.getWindow().setBackgroundDrawable(
                    //      new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });
            endDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    Log.d("CourseDetailActivity", "onDateSet: ");
                    String date = year + "-" + month + "-" + day;
                    termEndEdit.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
        } else {
            /*
            date for start
             */
            termStartEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            TermDetailActivity.this,
                            R.style.Theme_AppCompat,
                            startDateSetListener,
                            year, month, day
                    );
                    //dialog.getWindow().setBackgroundDrawable(
                    //      new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });
            startDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    Log.d("CourseDetailActivity", "onDateSet: ");
                    String date = year + "-" + month + "-" + day;
                    termStartEdit.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
                        /*
            date for end
             */
            termEndEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            TermDetailActivity.this,
                            R.style.Theme_AppCompat,
                            endDateSetListener,
                            year, month, day
                    );
                    //dialog.getWindow().setBackgroundDrawable(
                    //      new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });
            endDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month += 1;
                    Log.d("CourseDetailActivity", "onDateSet: ");
                    String date = year + "-" + month + "-" + day;
                    termEndEdit.setText(String.format("%4d-%02d-%02d", year, month, day));
                }
            };
        }
    }

    public void termSaveOnClick(View view){
        if((!(termStartEdit.getText().toString().equals(""))) && (!(termEndEdit.getText().toString().equals("")))) {
            Term term = new Term();
            term.setId(termId);
            term.setTitle(termTitleEdit.getText().toString());
            term.setStartDate((termStartEdit.getText().toString()));
            term.setEndDate(termEndEdit.getText().toString());

            TermHelper termHelp = new TermHelper(this);
            Intent intent = getIntent();
            if (intent.getBooleanExtra("com.ntlts.c196.ADD", false) == false) {
                int result = termHelp.updateTerm(db, term);
                popupUpdated("The record has been update.");
            } else {
                int result = (int) termHelp.insertTerm(db, term);
                popupUpdated("The record has been added.");
                db.close();
                Intent intent2 = new Intent(this, TermListActivity.class);
                startActivity(intent2);
            }
        } else {
            popupUpdated("Please select start and end date");
        }
    }

    public void termDetailOnClick(View view){
        Intent intent = new Intent(this, CourseListActivity.class);
        intent.putExtra("com.ntlts.c196.TERMID", termId);
        startActivityForResult(intent,1);
    }

    public void termDeleteOnClick(View view){
        CourseHelper ch = new CourseHelper(this);
        SQLiteDatabase courseDb = ch.getReadableDatabase();
        if(ch.getCourseWithTermId(courseDb, termId).size() == 0) {
            if (popupDeleted(this)) {
                popupUpdated("The record has been deleted.");
                TermHelper termHelper = new TermHelper(this);
                termHelper.deleteTerm(db, termId);
                db.close();
                Intent intent = new Intent(this, TermListActivity.class);
                //onPause();
                startActivity(intent);
            }
        } else {
            popupUpdated("The term has courses. Please delete all course.");
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
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                termId = intent.getIntExtra("com.ntlts.c196.TERMID", 0);

                termDetailToolbar = findViewById(R.id.termDetailToolbar);
                setSupportActionBar(termDetailToolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);

                //db
                TermHelper termHelper = new TermHelper(this);
                db = termHelper.getWritableDatabase();

                Term term = termHelper.selectTerm(db, termId);
                termTitleEdit = findViewById(R.id.termTitleEdit);
                termStartEdit = findViewById(R.id.termStartEdit);
                termEndEdit = findViewById(R.id.termEndEdit);
                termSave = findViewById(R.id.termSave);
                termDetail = findViewById(R.id.termDetail);
                termDelete = findViewById(R.id.termDelete);

                termTitleEdit.setText(term.getTitle());
                termStartEdit.setText(term.getStartDate());
                termEndEdit.setText(term.getEndDate());
            }
        }
    }
}
