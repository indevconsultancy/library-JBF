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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.Model.ActivityReportingPojo;
import com.indev.library.Model.ReportingImagePojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.indev.library.utils.CommonClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityReportingActivity extends AppCompatActivity {
    Spinner sp_activity_reporting;
    ImageView iv_camera,iv_camera1,iv_camera2,iv_camera3;
    Button alldataSubmit;
    ActivityReportingPojo activityReportingPojo;
    private static final int CAMERA_REQUEST=1888;
    SqliteDatabase sqliteDatabase;
    String base64="";
    SharedPrefHelper sharedPrefHelper;
    ArrayList<ActivityReportingPojo>activityReportingPojoArrayList;
    ArrayList<ReportingImagePojo>reportingImagePojoArrayList;
    ProgressDialog dialog;
    HashMap<String, Integer> activityNameHM, libraryNameHM,resourcesNameHM,languageNameHM;
    ArrayList<String> activityNameArrayList,libraryArrayList,resourcesArrayList,languageArrayList;
    int activity_id = 0;
//    String[] str_activity = {"Select Activity Reporting", "Story telling", "Story writing","Exhibition","Other"};
    int count=1;
    String img1="";
    String img2="";
    String img3="";
    String image="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);
        getSupportActionBar().setTitle("Activity Reporting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPrefHelper=new SharedPrefHelper(this);
        sp_activity_reporting=findViewById(R.id.sp_activity_reporting);
        intilizeAll();
        getActivitySpinner();
        sqliteDatabase = new SqliteDatabase(getApplicationContext());

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<4) {
                    Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cInt, CAMERA_REQUEST);
                }
            }
        });
//        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.spinner_lists,str_activity);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_activity_reporting.setAdapter(adapter);


      alldataSubmit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(checkValidation()) {
                  activityReportingPojo = new ActivityReportingPojo();
                  ReportingImagePojo reportingImagePojo = new ReportingImagePojo();
                  activityReportingPojo.setActivity_id(String.valueOf(activity_id));
                  String uuid=CommonClass.getUUID();
                  activityReportingPojo.setUuid(uuid);
                  activityReportingPojo.setLibrarain_id(sharedPrefHelper.getString("librarain_id", ""));
                  image = img1;
                  if (!img2.equals("")) {
                      image = image + "," + img2;
                  }
                  if (!img3.equals("")) {
                      image = image + "," + img3;
                  }

                  activityReportingPojo.setActivity_image2(img1);


                  long local_id = sqliteDatabase.Reporting(activityReportingPojo);
                  ArrayList<String> imgg = new ArrayList<>();
                  imgg.add(img1);
                  if (!img2.equals("")) {
                      imgg.add(img2);
                  }
                  if (!img3.equals("")) {
                      imgg.add(img2);
                  }
                  for (int j=0;j<imgg.size();j++){
                      reportingImagePojo.setUuid(uuid);
                      reportingImagePojo.setImage(imgg.get(j));
                      sqliteDatabase.Reporting_Image(reportingImagePojo);
                  }


                  activityReportingPojoArrayList = sqliteDatabase.getReportingDataArray(String.valueOf(local_id));
                  reportingImagePojoArrayList = sqliteDatabase.getReportingImage(uuid);

//                  ArrayList<String> imgg = new ArrayList<>();
//                  imgg.add(img1);
//                  if (!img2.equals("")) {
//                      imgg.add(img2);
//                  }
//                  if (!img3.equals("")) {
//                      imgg.add(img2);
//                  }
                  if (CommonClass.isInternetOn(getApplicationContext())) {
                      activityReportingPojo.setActivity_id(activityReportingPojoArrayList.get(0).getActivity_id());
                      activityReportingPojo.setLibrarain_id(activityReportingPojoArrayList.get(0).getLibrarain_id());
                      activityReportingPojo.setActivity_image(reportingImagePojoArrayList);
                      Gson gson = new Gson();
                      String data = gson.toJson(activityReportingPojo);
                      MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                      RequestBody body = RequestBody.create(JSON, data);
                      addreporting(body, String.valueOf(local_id));
                      Log.e("reporting", "Add Activity Successfully: " + data);
                  } else {
                      Intent intent = new Intent(ActivityReportingActivity.this, ReportingListActivity.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                      startActivity(intent);
                  }
              }
          }

      });

      }

    private void intilizeAll() {
        iv_camera = findViewById(R.id.iv_camera);
        iv_camera1=findViewById(R.id.iv_camera1);
        iv_camera2=findViewById(R.id.iv_camera2);
        iv_camera3=findViewById(R.id.iv_camera3);
        sqliteDatabase=new SqliteDatabase(this);
        alldataSubmit=findViewById(R.id.alldataSubmit);
        activityNameHM=new HashMap<>();
        activityNameArrayList=new ArrayList<>();
    }
    private void getActivitySpinner() {

        activityNameArrayList.clear();
        activityNameHM = sqliteDatabase.getAllActivity();
        for (int i = 0; i < activityNameHM.size(); i++) {
            activityNameArrayList.add(activityNameHM.keySet().toArray()[i].toString().trim());
        }
        activityNameArrayList.add(0, "Select Activity Name");

        ArrayAdapter language_adapter = new ArrayAdapter(this,R.layout.spinner_lists, activityNameArrayList);
        language_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_activity_reporting.setAdapter(language_adapter);
        activity_id=0;
//        if (screen_type.equals("edit_profile")) {
//            st_state = sqliteDatabase.getPSStateSp(editpregnantWomenRegisterTable.getState_id());
//            int pos = state_adapter.getPosition(st_state);
//            sp_state.setSelection(pos);
//        }


        sp_activity_reporting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sp_activity_reporting.getSelectedItem().toString().trim().equalsIgnoreCase("Select Activity Name")) {
                    if (sp_activity_reporting.getSelectedItem().toString().trim() != null) {
                        activity_id = activityNameHM.get(sp_activity_reporting.getSelectedItem().toString().trim());
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();

                base64 = encodeTobase64(photo);
                if (count == 1) {
                    iv_camera1.setImageBitmap(photo);
                    img1 = encodeTobase64(photo);
                }
                if (count == 2) {
                    iv_camera2.setImageBitmap(photo);
                    img2 = encodeTobase64(photo);

                }
                if (count == 3) {
                    iv_camera3.setImageBitmap(photo);
                    img3 = encodeTobase64(photo);
                }
                count++;
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
                        sqliteDatabase.updateReporting("activity_reporting", "local_id='" + lid + "'", last_activity_id, "id");
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
    private boolean checkValidation() {
        boolean ret = true;
        if (sp_activity_reporting.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(sp_activity_reporting.getSelectedItem());
        } else {
            TextView errorTextview = (TextView) sp_activity_reporting.getSelectedView();
            errorTextview.setError("Error");
            errorTextview.requestFocus();
            return false;
        }
        if (base64.equals("")){

            Toast.makeText(getApplicationContext(),"Please Capture Image",Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (et_remark.getText().toString().trim().equalsIgnoreCase("")) {
//            EditText flagEditfield = et_remark;
//            String msg = getString(R.string.please_enter_brief_description);
//            et_remark.setError(msg);
//            et_remark.requestFocus();
//            return false;
//        }

        return ret;
    }

}