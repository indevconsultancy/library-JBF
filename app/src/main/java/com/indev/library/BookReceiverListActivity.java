package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.Adapter.AdapterIssueBook;
import com.indev.library.Adapter.Click_Listener;
import com.indev.library.Model.BookIssuePojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookReceiverListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<BookIssuePojo> arrayList = new ArrayList<>();
    SqliteDatabase sqliteDatabase;
    Context context = this;
    SharedPrefHelper sharedPrefHelper;
    EditText et_SearchBar;
    BookIssuePojo bookIssuePojo;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_receiver_list);
        FloatingActionButton addPW;
        addPW =findViewById(R.id.addPW);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPrefHelper=new SharedPrefHelper(context);
        bookIssuePojo=new BookIssuePojo();
        String check= sharedPrefHelper.getString("list_type","");
        if (check.equalsIgnoreCase("issue")){
            getSupportActionBar().setTitle("Issue List");
        }else {

            getSupportActionBar().setTitle("Receiver List");
        }

        sqliteDatabase = new SqliteDatabase(this);
        recyclerView = findViewById(R.id.issu_list);
        et_SearchBar=findViewById(R.id.et_SearchBar);

        arrayList = sqliteDatabase.getBookIssueList("");
        AdapterIssueBook adapterAddBook = new AdapterIssueBook(context, arrayList, new Click_Listener() {
            @Override
            public void itemClick(String transaction_id, String currentDate, int clickStatus, int position) {
                 bookIssuePojo.setTransaction_id(transaction_id);
                 bookIssuePojo.setRecieved_date(currentDate);

                Gson gson = new Gson();
                String data = gson.toJson(bookIssuePojo);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);
                addBookIssueRegistration(body);
                Log.e("transaction_book", "registration: " + data);

            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterAddBook);

        et_SearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search = et_SearchBar.getText().toString();
                if(search.equals("")) {
                    arrayList = sqliteDatabase.getBookIssueList1("");
                }else{
                    arrayList = sqliteDatabase.getBookIssueList1(search);
                }
                AdapterIssueBook registerAdapter = new AdapterIssueBook(BookReceiverListActivity.this, arrayList, new Click_Listener() {
                    @Override
                    public void itemClick(String transaction_id, String currentDate, int clickStatus, int position) {


                    }
                });
                int counter = arrayList.size();
                //  FarmerCount.setText("Farmer 0"+counter);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(BookReceiverListActivity.this));
                recyclerView.setAdapter(registerAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        String check1= sharedPrefHelper.getString("list_type","");
        if (check1.equalsIgnoreCase("issue")){
            addPW.setVisibility(View.VISIBLE);
        }else {
           addPW.setVisibility(View.GONE);
        }
        addPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookReceiverListActivity.this, IssueButtonActivity.class);
                sharedPrefHelper.setString("spinner_type","spinner_add");

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(BookReceiverListActivity.this,MainMenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void addBookIssueRegistration(RequestBody body) {
        dialog = ProgressDialog.show(this, "", "Please wait...", true);
        ClientAPI.getClient().create(Library_API.class).UpdateReturnBook(body).enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("TAG", "onResponse: " + jsonObject.toString() );
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    Log.e("TAG", "onIssue: "+ jsonObject.toString() );

                    if(status.equals("1"))
                    {
//                        Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(BookReceiverListActivity.this, "Not Receive", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(BookReceiverListActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Receive Success", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });

    }
}