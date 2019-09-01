package com.ntlts.c196;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void termOnClick(View view){
        Intent intent = new Intent(this, TermListActivity.class);
        startActivity(intent);
    }

    public void progressButtonOnClick(View view){
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }
}
