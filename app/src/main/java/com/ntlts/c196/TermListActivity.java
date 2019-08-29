package com.ntlts.c196;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ntlts.c196.database.InitialCreation;
import com.ntlts.c196.database.TermDB;
import com.ntlts.c196.database.TermHelper;

import java.util.ArrayList;
import java.util.List;

public class TermListActivity extends AppCompatActivity
        implements TermAdapter.OnClickListener {
    RecyclerView termRecycleView;
    RecyclerView.LayoutManager layoutManager;
    List<Term> termList;
    TermAdapter termAdapter;
    CheckBox checkBox;
    SQLiteDatabase db;
    Toolbar termListToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_list_layout);

        termListToolbar = (Toolbar) findViewById(R.id.termListToolbar);
        setSupportActionBar(termListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Get all term
        TermHelper termDbHelper = new TermHelper(this);
        db = termDbHelper.getWritableDatabase();

        //DEMO
        //InitialCreation ic = new InitialCreation();
        //ic.createTerm(db);
        //DEMO END

        termList = termDbHelper.selectAllTerm(db);
        //Get database data end
        termRecycleView = (RecyclerView) findViewById(R.id.termRecycleView);
        layoutManager = new LinearLayoutManager(this);
        //((LinearLayoutManager)layoutManager).setOrientation(RecyclerView.VERTICAL);
        TermAdapter termAdapter = new TermAdapter(termList, this);
        termRecycleView.setLayoutManager(layoutManager);
        termRecycleView.setAdapter(termAdapter);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, TermDetailActivity.class);
        intent.putExtra("com.ntlts.c196.TERMID", termList.get(position).getId());
        intent.putExtra("com.ntlts.c196.ADD", false);
        //startActivity(intent);
        startActivityForResult(intent, 5);
        db.close();
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
    public void termAdd(View view){
        Intent intent = new Intent(this, TermDetailActivity.class);
        intent.putExtra("com.ntlts.c196.ADD", true);
        startActivityForResult(intent,5);
    }

}
