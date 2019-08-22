package com.ntlts.c196;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ntlts.c196.database.TermHelper;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail);
        TermHelper termHelper = new TermHelper(this);
        //db = ((TermListActivity)getApplicationContext()).db;
        db = termHelper.getWritableDatabase();
        Intent intent = getIntent();
        termId = intent.getIntExtra("com.ntlts.c196.TERMID",0);
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

    public void termSaveOnClick(View view){
        Term term = new Term();
        term.setId(termId);
        term.setTitle(termTitleEdit.getText().toString());
        term.setStartDate((termStartEdit.getText().toString()));
        term.setEndDate(termEndEdit.getText().toString());

        TermHelper termHelp = new TermHelper(this);
        int result = termHelp.updateTerm(db, term);
        popupUpdated("The record has been update.");
    }

    public void termDetailOnClick(View view){
        Intent intent = new Intent(this, CourseListActivity.class);
        intent.putExtra("com.ntlts.c196.TERMID", termId);
        startActivity(intent);
    }

    public void termDeleteOnClick(View view){

        if(popupDeleted(this)) {
            popupUpdated("The record has been deleted.");
            TermHelper termHelper = new TermHelper(this);
            termHelper.deleteTerm(db, termId);
            db.close();
            Intent intent = new Intent(this, TermListActivity.class);
            startActivity(intent);
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

}
