package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.Model.BookIssuePojo;
import com.indev.library.Model.LibraryPojo;
import com.indev.library.Model.LoginPojo;
import com.indev.library.Model.SubscriberPojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    TextView tvForgotPass;
    CheckBox cb_showPassword;
    SqliteDatabase sqliteDatabase;
    SharedPrefHelper sharedPrefHelper;
    private Context context = this;
    private ProgressDialog dialog;

//    SharedPrefHelper sharedPrefHelper;
//    SqliteDatabase sqliteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intializeAll();
        showPassword();
        sharedPrefHelper = new SharedPrefHelper(context);
        sqliteDatabase = new SqliteDatabase(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    callLoginApi();
                }
//                Intent intent=new Intent(LoginActivity.this,MainMenuActivity.class);
//                startActivity(intent);

            }
        });
        getSupportActionBar().hide();
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }


    private void intializeAll()
    {

        email =findViewById(R.id.email);
        password =findViewById(R.id.password);
//        sharedPrefHelper = new SharedPrefHelper(context);
//        sqliteDatabase = new SqliteDatabase(this);
        dialog = new ProgressDialog(context);
        cb_showPassword=findViewById(R.id.cb_showPassword);
        login =findViewById(R.id.login);
        tvForgotPass=findViewById(R.id.tvForgotPass);


    }

    private boolean checkValidation() {
        if (email.getText().toString().trim().length() == 0) {
            Toast.makeText(context, R.string.pleae_enter_user_name, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().trim().length() == 0) {
            Toast.makeText(context, R.string.pleae_enter_password, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void callLoginApi() {
        dialog = ProgressDialog.show(context, "", getString(R.string.plase), true);
        LoginPojo loginn = new LoginPojo();

        loginn.setEmail(email.getText().toString().trim());
        loginn.setPassword(password.getText().toString().trim());
        sharedPrefHelper.setString("setPassword", password.getText().toString().trim());

        Gson gson = new Gson();
        String data = gson.toJson(loginn);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        ClientAPI.getClient().create(Library_API.class).LoginApi(body).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().toString());
//                    dialog.dismiss();
                    Log.e("jbchjbch", "onResponse: " + jsonObject.toString());
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    if (status.equalsIgnoreCase("1")) {

                        String id = jsonObject.optString("librarain_id");
                        String name = jsonObject.optString("name");
                        sharedPrefHelper.setString("librarain_id", id);
                        sharedPrefHelper.setString("isLogin", "yes");
                        sharedPrefHelper.getString("user_name", name);

                        call_ResourceDataDownload();
                        call_ActivityReportingDataDownload();
                        call_SubscriberDataDownload();
                        call_BookIssueDataDownload();
                        call_LibraryDataDownload();




                    } else {
                        Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("LOGIN SCREEN ", "====" + t.getMessage());
            }
        });

    }
    private void showPassword() {
        cb_showPassword.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                // show password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                // hide password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }
    private void call_SubscriberDataDownload() {
        new AsyncTask<Void, Void, Void>(){
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {
                SubscriberPojo subscriberPojo = new SubscriberPojo();
                String librarain_id=sharedPrefHelper.getString("librarain_id", "");
                subscriberPojo.setLibrarain_id(librarain_id);
                Gson gson = new Gson();
                String data = gson.toJson(subscriberPojo);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);

                final Library_API apiService = ClientAPI.getClient().create(Library_API.class);
                Call<JsonArray> call = apiService.DatadownloadSubscriber(body);
//                    final int finalJ = j;
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        try{
                            JsonArray data = response.body();
                            sqliteDatabase.dropTable("subscriber");

                            if(data.size()>0) {
                                for (int i = 0; i < data.size(); i++) {
                                    JSONObject singledata = new JSONObject(data.get(i).toString());
                                    Iterator keys = singledata.keys();
                                    ContentValues contentValues = new ContentValues();
                                    while (keys.hasNext()) {
                                        String currentDynamicKey = (String) keys.next();
                                        contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                    }
                                    sqliteDatabase.saveMasterTable(contentValues, "subscriber");
                                }
//                                Intent intent = new Intent(MainMenuActivity.this, SubscriberListActivity.class);
//                                startActivity(intent);
////                                finish();
//                                //call_candidateData();
//                            }else{
//                                Intent intent = new Intent(MainMenuActivity.this, SubscriberListActivity.class);
//                                startActivity(intent);
////                                finish();
//                                dialog.dismiss();
                            }

                        }catch (Exception e){
                            Toast.makeText(context, "Something is wrong", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Log.d("Failure", ""+t.getMessage());
                    }
                });

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }
        }.execute();
    }


    private void call_ResourceDataDownload() {
        new AsyncTask<Void, Void, Void>(){
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {
                AddBookPojo addBookPojo = new AddBookPojo();
                String librarain_id=sharedPrefHelper.getString("librarain_id", "");
                addBookPojo.setLibrarain_id(librarain_id);
                Gson gson = new Gson();
                String data = gson.toJson(addBookPojo);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);

                final Library_API apiService = ClientAPI.getClient().create(Library_API.class);
                Call<JsonArray> call = apiService.DatadownloadResourse(body);
//                    final int finalJ = j;
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        try{
                            JsonArray data = response.body();
                            sqliteDatabase.dropTable("resource");

                            if(data.size()>0) {
                                for (int i = 0; i < data.size(); i++) {
                                    JSONObject singledata = new JSONObject(data.get(i).toString());
                                    Iterator keys = singledata.keys();
                                    ContentValues contentValues = new ContentValues();
                                    while (keys.hasNext()) {
                                        String currentDynamicKey = (String) keys.next();
                                        contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                    }
                                    sqliteDatabase.saveMasterTable(contentValues, "resource");
                                }
//                                Intent intent = new Intent(MainMenuActivity.this, BookListActivity.class);
//                                startActivity(intent);
////                                finish();
////                                //call_candidateData();
//                            }else{
//                                Intent intent = new Intent(MainMenuActivity.this, BookListActivity.class);
//                                startActivity(intent);
////                                finish();
//                                dialog.dismiss();
                            }

                        }catch (Exception e){
                            Toast.makeText(context, "Something is wrong", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Log.d("Failure", ""+t.getMessage());
                    }
                });

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }
        }.execute();
    }
    private void call_ActivityReportingDataDownload() {
        new AsyncTask<Void, Void, Void>(){
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {
                AddBookPojo addBookPojo = new AddBookPojo();
                String librarain_id=sharedPrefHelper.getString("librarain_id", "");
                addBookPojo.setLibrarain_id(librarain_id);
                Gson gson = new Gson();
                String data = gson.toJson(addBookPojo);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);

                final Library_API apiService = ClientAPI.getClient().create(Library_API.class);
                Call<JsonArray> call = apiService.DatadownloadActivityReporting(body);
//                    final int finalJ = j;
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        try{
                            JsonArray data = response.body();
                            sqliteDatabase.dropTable("activity_reporting");

                            if(data.size()>0) {
                                for (int i = 0; i < data.size(); i++) {
                                    JSONObject singledata = new JSONObject(data.get(i).toString());
                                    Iterator keys = singledata.keys();
                                    ContentValues contentValues = new ContentValues();
                                    while (keys.hasNext()) {
                                        String currentDynamicKey = (String) keys.next();
                                        contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                    }
                                    sqliteDatabase.saveMasterTable(contentValues, "activity_reporting");
                                }
//                                Intent intent = new Intent(MainMenuActivity.this, BookListActivity.class);
//                                startActivity(intent);
////                                finish();
////                                //call_candidateData();
//                            }else{
//                                Intent intent = new Intent(MainMenuActivity.this, BookListActivity.class);
//                                startActivity(intent);
////                                finish();
//                                dialog.dismiss();
                            }

                        }catch (Exception e){
                            Toast.makeText(context, "Something is wrong", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Log.d("Failure", ""+t.getMessage());
                    }
                });

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }
        }.execute();
    }
    private void call_BookIssueDataDownload() {
        new AsyncTask<Void, Void, Void>(){
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {
                BookIssuePojo bookIssuePojo = new BookIssuePojo();
                String librarain_id=sharedPrefHelper.getString("librarain_id", "");
                bookIssuePojo.setLibrarain_id(librarain_id);
                Gson gson = new Gson();
                String data = gson.toJson(bookIssuePojo);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);

                final Library_API apiService = ClientAPI.getClient().create(Library_API.class);
                Call<JsonArray> call = apiService.DatadownloadIssuBook(body);
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        try{
                            JsonArray data = response.body();
                            sqliteDatabase.dropTable("transaction_book");

                            if(data.size()>0) {
                                for (int i = 0; i < data.size(); i++) {
                                    JSONObject singledata = new JSONObject(data.get(i).toString());
                                    Iterator keys = singledata.keys();
                                    ContentValues contentValues = new ContentValues();
                                    while (keys.hasNext()) {
                                        String currentDynamicKey = (String) keys.next();
                                        contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                    }
                                    sqliteDatabase.saveMasterTable(contentValues, "transaction_book");
                                }
                                Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
                                startActivity(intent);
                            }

                        }catch (Exception e){
                            Toast.makeText(context, "Something is wrong", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Log.d("Failure", ""+t.getMessage());
                    }
                });

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }
        }.execute();
    }

    private void call_LibraryDataDownload() {
        new AsyncTask<Void, Void, Void>(){
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {
               // LibraryPojo libraryPojo = new LibraryPojo();
               // String librarain_id=sharedPrefHelper.getString("librarain_id", "");
                DataDownloadInput dataDownloadInput = new DataDownloadInput();
                dataDownloadInput.setTable_name("library");
                Gson gson = new Gson();
                String data = gson.toJson(dataDownloadInput);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, data);

                final Library_API apiService = ClientAPI.getClient().create(Library_API.class);
                Call<JsonArray> call = apiService.getSpinner(body);
//                    final int finalJ = j;
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        try{
                            JsonArray data = response.body();
                            sqliteDatabase.dropTable("library");

                            if(data.size()>0) {
                                for (int i = 0; i < data.size(); i++) {
                                    JSONObject singledata = new JSONObject(data.get(i).toString());
                                    Iterator keys = singledata.keys();
                                    ContentValues contentValues = new ContentValues();
                                    while (keys.hasNext()) {
                                        String currentDynamicKey = (String) keys.next();
                                        contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                    }
                                    sqliteDatabase.saveMasterTable(contentValues, "library");
                                }
//                                Intent intent = new Intent(MainMenuActivity.this, SubscriberListActivity.class);
//                                startActivity(intent);
////                                finish();
//                                //call_candidateData();
//                            }else{
//                                Intent intent = new Intent(MainMenuActivity.this, SubscriberListActivity.class);
//                                startActivity(intent);
////                                finish();
//                                dialog.dismiss();
                            }

                        }catch (Exception e){
                            Toast.makeText(context, "Something is wrong", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Log.d("Failure", ""+t.getMessage());
                    }
                });

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }
        }.execute();
    }
}