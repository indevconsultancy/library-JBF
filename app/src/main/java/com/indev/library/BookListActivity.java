package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indev.library.Adapter.AdapterAddBook;
import com.indev.library.Adapter.AdapterIssueBook;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.SqliteHelper.SqliteDatabase;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<AddBookPojo> arrayList = new ArrayList<>();
    SqliteDatabase sqliteDatabase;
    Context context = this;
    EditText etSearchBarBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        getSupportActionBar().setTitle("List a Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         sqliteDatabase = new SqliteDatabase(this);
        recyclerView = findViewById(R.id.rv1);
        etSearchBarBook=findViewById(R.id.etSearchBarBook);

        arrayList = sqliteDatabase.getRegistrationData2();
        AdapterAddBook adapterAddBook = new AdapterAddBook(context, arrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterAddBook);
        etSearchBarBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String search = etSearchBarBook.getText().toString().trim();
                arrayList = sqliteDatabase.getAddBookList(search);
                AdapterAddBook registerAdapter = new AdapterAddBook(BookListActivity.this, arrayList);
                int counter = arrayList.size();
                //  FarmerCount.setText("Farmer 0"+counter);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(BookListActivity.this));
                recyclerView.setAdapter(registerAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        FloatingActionButton addPW;
        addPW =findViewById(R.id.addPW);
        addPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookListActivity.this, AddBookNameActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(BookListActivity.this,MainMenuActivity.class);
        startActivity(intent);
        finish();
    }
}