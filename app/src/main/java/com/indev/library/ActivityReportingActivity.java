package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.indev.library.Model.ActivityReportingPojo;
import com.indev.library.SqliteHelper.SqliteDatabase;

import java.io.ByteArrayOutputStream;

public class ActivityReportingActivity extends AppCompatActivity {
    Spinner sp_activity_reporting;
    ImageView imageView_profile1;
    Button alldataSubmit;
    ActivityReportingPojo activityReportingPojo;
    private static final int CAMERA_REQUEST=1888;
    SqliteDatabase sqliteDatabase;
    String base64="";
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
              activityReportingPojo.setReporting(sp_activity_reporting.getSelectedItem().toString());
              activityReportingPojo.setPhoto( base64);
              sqliteDatabase.Reporting(activityReportingPojo);


              Toast.makeText(getApplicationContext(),"Add Success",Toast.LENGTH_SHORT).show();
              Intent intent=new Intent(ActivityReportingActivity.this,ReportingListActivity.class);
              startActivity(intent);
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