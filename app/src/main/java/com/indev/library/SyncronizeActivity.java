package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.Model.BookIssuePojo;
import com.indev.library.Model.SubscriberPojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SqliteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncronizeActivity extends AppCompatActivity {
    TextView tv_syn_addbook,tv_syn_subscriber,tv_syn_issue,syn_return;
    ArrayList<AddBookPojo>addBookPojoArrayList;
    ArrayList<SubscriberPojo>subscriberPojoArrayList;
    ArrayList<BookIssuePojo>bookIssuePojoArrayList;
    SqliteDatabase sqliteDatabase;
    int countAddBook=0;
    int countSubscriber=0;
    int countBookIssue=0;
    int countBookReturn=0;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncronize);
        getSupportActionBar().setTitle("Data Synchronize");
        intializeAll();

        addBookPojoArrayList = sqliteDatabase.getSt_AddBookSyn();
        countAddBook = addBookPojoArrayList.size();
        if(countAddBook>0) {
            tv_syn_addbook.setText(countAddBook + "");

        }
        tv_syn_addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataCount ();
            }
        });

        //Subscriber
        subscriberPojoArrayList = sqliteDatabase.getSt_SubscriberSyn();
        countSubscriber = subscriberPojoArrayList.size();
        if(countSubscriber>0) {
            tv_syn_subscriber.setText(countSubscriber + "");

        }
        tv_syn_subscriber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataCountSubscriber();
            }
        });
        //Book Issue
        bookIssuePojoArrayList = sqliteDatabase.getSt_BookIssueSyn();
        countBookIssue = bookIssuePojoArrayList.size();
        if(countBookIssue>0) {
            tv_syn_issue.setText(countBookIssue + "");

        }
        tv_syn_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataCountIssue();
            }
        });
        //BookReciver
        bookIssuePojoArrayList = sqliteDatabase.getSt_BookIssueSyn();
        countBookIssue = bookIssuePojoArrayList.size();
        if(countBookReturn>0) {
            syn_return.setText(countBookReturn + "");

        }
        syn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataCountReturn();            }
        });

    }
    private void intializeAll(){
        tv_syn_addbook=findViewById(R.id.tv_syn_addbook);
        tv_syn_subscriber=findViewById(R.id.tv_syn_subscriber);
        tv_syn_issue=findViewById(R.id.tv_syn_issue);
        syn_return=findViewById(R.id.syn_return);
        addBookPojoArrayList=new ArrayList<>();
        sqliteDatabase=new SqliteDatabase(this);
        subscriberPojoArrayList=new ArrayList<>();
        bookIssuePojoArrayList=new ArrayList<>();
    }
    private void setDataCount () {
        addBookPojoArrayList = sqliteDatabase.getSt_AddBookSyn();
        countAddBook = addBookPojoArrayList.size();
        if (countAddBook > 0) {
            for (int i = 0; i < addBookPojoArrayList.size(); i++) {
                Gson gson = new Gson();
                String data = gson.toJson(addBookPojoArrayList.get(i));
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);
                addBookRegistration(body,addBookPojoArrayList.get(i).getResource_unique_id());
                Log.e("resource", "registration: " + data);
            }
//
        }
    }
    private void setDataCountSubscriber () {
        subscriberPojoArrayList = sqliteDatabase.getSt_SubscriberSyn();
        countSubscriber = subscriberPojoArrayList.size();
        if (countSubscriber > 0) {
            for (int i = 0; i < subscriberPojoArrayList.size(); i++) {
                Gson gson = new Gson();
                String data = gson.toJson(subscriberPojoArrayList.get(i));
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);
                addSubscribeRegistration(body,subscriberPojoArrayList.get(i).getSubscriber_unique_id());
                Log.e("resource", "registration: " + data);
            }
//
        }
    }
    private void setDataCountIssue () {
        bookIssuePojoArrayList = sqliteDatabase.getSt_BookIssueSyn();
        countBookIssue = bookIssuePojoArrayList.size();
        if (countBookIssue > 0) {
            for (int i = 0; i < bookIssuePojoArrayList.size(); i++) {
                Gson gson = new Gson();
                String data = gson.toJson(bookIssuePojoArrayList.get(i));
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);
                addBookIssueRegistration(body,bookIssuePojoArrayList.get(i).getLocal_id());
                Log.e("resource", "registration: " + data);
            }
//
        }
    }
    private void setDataCountReturn () {
        bookIssuePojoArrayList = sqliteDatabase.getSt_BookIssueSyn();
        countBookIssue = bookIssuePojoArrayList.size();
        if (countBookReturn > 0) {
            for (int i = 0; i < bookIssuePojoArrayList.size(); i++) {
                Gson gson = new Gson();
                String data = gson.toJson(bookIssuePojoArrayList.get(i));
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);
                addBookRecived(body,bookIssuePojoArrayList.get(i).getLocal_id());
                Log.e("resource", "registration: " + data);
            }
//
        }
    }
    private void addBookRegistration(RequestBody body, String lid) {
        dialog = ProgressDialog.show(this, "", "Please wait...", true);
        ClientAPI.getClient().create(Library_API.class).AddBookRegistration(body).enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("TAG", "onResponse: " + jsonObject.toString() );
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    String last_book_id = jsonObject.optString("last_book_id");
                    if(status.equals("1"))
                    {
                        sqliteDatabase.update("resource", "resource_unique_id='" + lid + "'", last_book_id, "resource_id");
                        tv_syn_addbook.setText("0 Pending Data");
                        dialog.dismiss();

                    }
                    else{
                        Toast.makeText(SyncronizeActivity.this, "Book Not Registered", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SyncronizeActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Book Registration", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });

    }
    private void addSubscribeRegistration(RequestBody body, String lid) {
        dialog = ProgressDialog.show(this, "", "Please wait...", true);
        ClientAPI.getClient().create(Library_API.class).AddSubscriberRegistration(body).enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("TAG", "onResponse: " + jsonObject.toString() );
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    String last_subscriber_id = jsonObject.optString("last_subscriber_id");
                    if(status.equals("1"))
                    {
                        sqliteDatabase.updatee("subscriber", "subscriber_unique_id='" + lid + "'", last_subscriber_id, "subscriber_id");
                        tv_syn_subscriber.setText("0 Pending Data");
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(SyncronizeActivity.this, "Subscriber Not Registered", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SyncronizeActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Subscriber Registration", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });


    }
    private void addBookIssueRegistration(RequestBody body, String lid) {
        dialog = ProgressDialog.show(this, "", "Please wait...", true);
        ClientAPI.getClient().create(Library_API.class).AddIssueRegistration(body).enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("TAG", "onResponse: " + jsonObject.toString() );
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    String last_transaction_id = jsonObject.optString("last_transaction_id");
                    if(status.equals("1"))
                    {
                        sqliteDatabase.updateee("transaction_book", "local_id='" + lid + "'", last_transaction_id, "transaction_id");

                        tv_syn_issue.setText("0 Pending Data");
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(SyncronizeActivity.this, "Not Issue", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SyncronizeActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Issue Success", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });

    }
    private void addBookRecived(RequestBody body, String lid) {
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
                        sqliteDatabase.updateeeReturnFlag("transaction_book", "local_id='" + lid + "'");

                        tv_syn_issue.setText("0 Pending Data");
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(SyncronizeActivity.this, "Not Receive", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SyncronizeActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Receive Success", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });

    }
}