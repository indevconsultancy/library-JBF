package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indev.library.Adapter.AdapterAddBook;
import com.indev.library.Adapter.AdapterReporting;
import com.indev.library.Model.ActivityReportingPojo;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.SqliteHelper.SqliteDatabase;

import java.util.ArrayList;

public class ReportingListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ActivityReportingPojo> arrayList = new ArrayList<>();
    SqliteDatabase sqliteDatabase;
    Context context = this;
    EditText etSearchBarBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting_list);
        getSupportActionBar().setTitle("List Reporting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sqliteDatabase = new SqliteDatabase(this);
        recyclerView = findViewById(R.id.rv1);
        etSearchBarBook=findViewById(R.id.etSearchBarBook);

        arrayList = sqliteDatabase.getReportingData();
        AdapterReporting adapterAddBook = new AdapterReporting(context, arrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterAddBook);
        FloatingActionButton addPW;
        addPW =findViewById(R.id.addPW);
        addPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportingListActivity.this, ActivityReportingActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }
        });
    }
}