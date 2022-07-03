package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.Model.ActivityReportingPojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.indev.library.utils.CommonClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityReportingActivity extends AppCompatActivity {
    Spinner sp_activity_reporting;
    ImageView imageView_profile1;
    Button alldataSubmit;
    ActivityReportingPojo activityReportingPojo;
    private static final int CAMERA_REQUEST=1888;
    SqliteDatabase sqliteDatabase;
    String base64="";
    ProgressDialog dialog;
    String[] str_activity = {"Select Activity Reporting", "Story telling", "Story writing","Exhibition","Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);
        getSupportActionBar().setTitle("Activity Reporting");
        sp_activity_reporting=findViewById(R.id.sp_activity_reporting);
        imageView_profile1=findViewById(R.id.imageView_profile1);
        alldataSubmit=findViewById(R.id.alldataSubmit);
        sqliteDatabase = new SqliteDatabase(getApplicationContext());

        imageView_profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt, CAMERA_REQUEST);

            }
        });
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.spinner_lists,str_activity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_activity_reporting.setAdapter(adapter);


      alldataSubmit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              activityReportingPojo=new ActivityReportingPojo();
              activityReportingPojo.setActivity_id(sp_activity_reporting.getSelectedItem().toString());
              activityReportingPojo.setLibrarain_id("1");
              activityReportingPojo.setActivity_image( base64);
              sqliteDatabase.Reporting(activityReportingPojo);


              long local_id = sqliteDatabase.Reporting(activityReportingPojo);
//              if (CommonClass.isInternetOn(getApplicationContext())) {
                  Gson gson = new Gson();
                  String data = gson.toJson(activityReportingPojo);
                  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                  RequestBody body = RequestBody.create(JSON, data);
                  addreporting(body, String.valueOf(local_id));
                  Log.e("reporting", "Add Activity Successfully: " + data);
//              }
//              else
//              {

              Toast.makeText(getApplicationContext(),"Add Success",Toast.LENGTH_SHORT).show();
//              Intent intent=new Intent(ActivityReportingActivity.this,ReportingListActivity.class);
//              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//              startActivity(intent);
          }

      });

      }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (data!=null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();

                base64 = encodeTobase64(photo);
                imageView_profile1.setImageBitmap(photo);
            }
        }
    }

    private void addreporting(RequestBody body, String lid) {
        dialog = ProgressDialog.show(this, "", "Please wait...", true);
        ClientAPI.getClient().create(Library_API.class).Reporting(body).enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("TAG", "onResponse: " + jsonObject.toString() );
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    String last_activity_id = jsonObject.optString("last_activity_id");
                    if(status.equals("1"))
                    {
                        sqliteDatabase.updateReporting("reporting", "local_id='" + lid + "'", last_activity_id, "id");
                        Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(ActivityReportingActivity.this, ReportingListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ActivityReportingActivity.this, "Not Reporting", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ActivityReportingActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Reporting", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });

    }
    private String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream byteArrayOS = null;
        try {
            System.gc();
            byteArrayOS = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOS);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOS);
        }
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
    }
}