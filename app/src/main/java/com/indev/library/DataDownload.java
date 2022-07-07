package com.indev.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;

import org.json.JSONObject;

import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataDownload
{


    private String[] tables = {"qualification","book_category","library","language","activity_list","sources_of_resource"};
    SharedPrefHelper sharedPrefHelper;
    //ProgressDialog dialog;
    Context context;
    android.app.Dialog change_language_alert;
    @SuppressLint("StaticFieldLeak")
    public void getMasterTables(final Activity context) {
        final SqliteDatabase sqliteHelper = new SqliteDatabase(context);
        //  dialog=new ProgressDialog(context);
        //  dialog = ProgressDialog.show(context, "", "Please Wait...", true);

        sharedPrefHelper = new SharedPrefHelper(context);
        sqliteHelper.openDataBase();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (int j = 0; j < tables.length; j++) {
                    DataDownloadInput dataDownloadInput = new DataDownloadInput();
                    dataDownloadInput.setTable_name(tables[j]);
                    Gson mGson = new Gson();
                    String data = mGson.toJson(dataDownloadInput);
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, data);

                    final Library_API apiService = ClientAPI.getClient().create(Library_API.class);
                    Call<JsonArray> call = apiService.getSpinner(body);
                    final int finalJ = j;
                    call.enqueue(new Callback<JsonArray>() {

                        @Override
                        public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                            try {
                                JsonArray data = response.body();
                                sqliteHelper.dropTable(tables[finalJ]);

                                for (int i = 0; i < data.size(); i++) {
                                    JSONObject singledata = new JSONObject(data.get(i).toString());
                                    Iterator keys = singledata.keys();
                                    ContentValues contentValues = new ContentValues();
                                    while (keys.hasNext()) {
                                        String currentDynamicKey = (String) keys.next();
                                        contentValues.put(currentDynamicKey, singledata.get(currentDynamicKey).toString());
                                    }

                                    sqliteHelper.saveMasterTable(contentValues, tables[finalJ]);

                                }

                                if (tables[finalJ].equals("sources_of_resource")) {
                                    sharedPrefHelper = new SharedPrefHelper(context);
                                    sharedPrefHelper.setString("isSplashLoaded", "Yes");
                                //  Change_Language(context);
                                        Intent intent = new Intent(context, LoginActivity.class);
                                      context.startActivity(intent);
                                     context.finish();
                                }
                            } catch (Exception s) {
                                s.printStackTrace();
                            }

                        }
                        @Override
                        public void onFailure(Call<JsonArray> call, Throwable t) {
                            Log.d("", "");
                        }
                    });

                }
                return null;

            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }


}
