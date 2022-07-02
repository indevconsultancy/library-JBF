package com.indev.library;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.Model.SubscriberPojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.indev.library.utils.CommonClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSubscribeActivity extends AppCompatActivity {
//    String [] category ={"Select Category","Student(<Class 5)","Student(>Class 5 and <Class 10)","Other"};

    ImageView imageView_profile;
    Button alldataSubmit;
    RadioGroup rg_gender;
    RadioButton rb_male,rb_female;
   EditText et_name,et_date_of_birth,et_profession,et_address,et_mobile_no,et_email;
   Spinner sp_category,sp_district,sp_block,sp_village;
   SqliteDatabase sqliteDatabase;
   SubscriberPojo subscriberPojo;
   SharedPrefHelper sharedPrefHelper;
    int mYear,mMonth,mDay,year,month,day;
    DatePickerDialog datePickerDialog;
    HashMap<String, Integer> categoryNameHM;
    ArrayList<String> categoryArrayList;
    ProgressDialog dialog;
     String st_gender;
    int category_id = 0;
    String str="";
    private static final int CAMERA_REQUEST=1888;
    String base64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscribe);
        getSupportActionBar().setTitle("Add Subscriber");
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
//        actionBar.setHomeAsUpIndicator(R.drawable.mybutton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intializeAll();
        sharedPrefHelper=new SharedPrefHelper(this);
        sqliteDatabase = new SqliteDatabase(getApplicationContext());
        getCategorySpinner();
//        ArrayAdapter adapter=new ArrayAdapter(AddSubscribeActivity.this, R.layout.spinner_lists,category);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_category.setAdapter(adapter);


        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_male:
                        st_gender = "Male";
                        break;
                    case R.id.rb_female:
                        st_gender = "Female";
                        break;
                }

            }
        });

       //Date Picker
        et_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_date_of_birth.setError(null);
                et_date_of_birth.clearFocus();
                mYear=year;
                mMonth=month;
                mDay=day;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR); // current year
                mMonth = c.get(Calendar.MONTH); // current month
                mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AddSubscribeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                et_date_of_birth.setText("" +  year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
//                                getAge();
                            }
                        }, mYear, mMonth, mDay);
//
//                c.add(2017,0);
//                c.add(Calendar.YEAR, -5);
//                java.util.Date y = c.getTime();
//                Log.e("ghhgh",""+System.currentTimeMillis()+""+result);
//                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                datePickerDialog.getDatePicker().setMinDate(result.getTime());
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-31556926000L);

                Calendar max_date_c = Calendar.getInstance();
                max_date_c.set(Calendar.YEAR, mYear-5);
                datePickerDialog.getDatePicker().setMaxDate(max_date_c.getTimeInMillis());
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff173e6d"));
            }
        });

        imageView_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt, CAMERA_REQUEST);

            }
        });
        //All Data Submit
        alldataSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {
                    subscriberPojo = new SubscriberPojo();
                    String resource_uniue_id="S"+ CommonClass.getUUID();
                    subscriberPojo.setSubscriber_unique_id(resource_uniue_id);
                    subscriberPojo.setSubscriber_name(et_name.getText().toString().trim());
                    subscriberPojo.setSubscriber_image(base64);
                    subscriberPojo.setGender(st_gender);
                    subscriberPojo.setCategory_id(String.valueOf(category_id));
                    subscriberPojo.setLibrarain_id(sharedPrefHelper.getString("librarain_id", ""));

                    subscriberPojo.setEmail(et_email.getText().toString().trim());
                    subscriberPojo.setDate_of_birth(et_date_of_birth.getText().toString().trim());
                    subscriberPojo.setAddress(et_address.getText().toString().trim());
                    subscriberPojo.setMobile_number(et_mobile_no.getText().toString().trim());

                    long local_id = sqliteDatabase.Subscriber(subscriberPojo);
                    if (CommonClass.isInternetOn(getApplicationContext())) {
                        Gson gson = new Gson();
                        String data = gson.toJson(subscriberPojo);
                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                        RequestBody body = RequestBody.create(JSON, data);
                        addSubscribeRegistration(body, String.valueOf(local_id));
                        Log.e("subscriber", "registration: " + data);
                    }
                    else{
                        Intent intent  = new Intent(AddSubscribeActivity.this, SubscriberListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }

            }
        });


//



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
                        sqliteDatabase.updatee("subscriber", "local_id='" + lid + "'", last_subscriber_id, "subscriber_id");
                        Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(AddSubscribeActivity.this, SubscriberListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(AddSubscribeActivity.this, "Subscriber Not Registered", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AddSubscribeActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Subscriber Registration", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });


    }
    private void intializeAll()
    {

        imageView_profile =findViewById(R.id.imageView_profile);
        et_name =findViewById(R.id.et_name);
        et_date_of_birth =findViewById(R.id.et_date_of_birth);
        sp_category =findViewById(R.id.sp_category);
        et_address =findViewById(R.id.et_address);
        et_email =findViewById(R.id.et_email);
        alldataSubmit=findViewById(R.id.alldataSubmit);
        et_mobile_no=findViewById(R.id.et_mobile_no);
        rg_gender=findViewById(R.id.rg_gender);
        rb_male=findViewById(R.id.rb_male);
        rb_female=findViewById(R.id.rb_female);
        categoryArrayList=new ArrayList<>();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();
//
            base64 = encodeTobase64(photo);
            imageView_profile.setImageBitmap(photo);
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
    //All Spinner
    private void getCategorySpinner() {

        categoryArrayList.clear();
        categoryNameHM = sqliteDatabase.getAllCategory();
        for (int i = 0; i < categoryNameHM.size(); i++) {
            categoryArrayList.add(categoryNameHM.keySet().toArray()[i].toString().trim());
        }
        categoryArrayList.add(0, "Select Qualification");

        ArrayAdapter state_adapter = new ArrayAdapter(this,R.layout.spinner_lists, categoryArrayList);
        state_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category.setAdapter(state_adapter);
       category_id=0;
//        if (screen_type.equals("edit_profile")) {
//            st_state = sqliteDatabase.getPSStateSp(editpregnantWomenRegisterTable.getState_id());
//            int pos = state_adapter.getPosition(st_state);
//            sp_state.setSelection(pos);
//        }


        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sp_category.getSelectedItem().toString().trim().equalsIgnoreCase("Select Qualification")) {
                    if (sp_category.getSelectedItem().toString().trim() != null) {
                        category_id = categoryNameHM.get(sp_category.getSelectedItem().toString().trim());
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.navigationBarBackground:
//                // app icon in action bar clicked; go home
////                Intent intent = new Intent(this, MainMenuActivity.class);
////                intent.putExtra("user_id",user_id);
////                intent.putExtra("mobile",mobile);
////                intent.putExtra("name",name);
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                startActivity(intent);
//                this.finish();
//                return true;
//        }
//                return super.onOptionsItemSelected(item);
//
//    }
private boolean checkValidation() {
    boolean ret = true;

    if (et_mobile_no.getText().toString().trim().length() < 10) {
        EditText flagEditfield = et_mobile_no;
        String msg = getString(R.string.please_enter_valid_contact_number);
        et_mobile_no.setError(msg);
        et_mobile_no.requestFocus();
        return false;
    }
//    //onClick of button perform this simplest code.
//    String email = et_email.getText().toString().trim();
//
//    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//    if (email.matches(emailPattern))
//    {
//        Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
//    }
//    else
//    {
//        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
//    }

    if (rb_male.isChecked() || rb_female.isChecked()) {
    } else {
        Toast.makeText(getApplicationContext(), getString(R.string.please_select_gender), Toast.LENGTH_SHORT).show();
        return false;
    }
    if (et_date_of_birth.getText().toString().trim().length() ==0) {
        EditText flagEditfield = et_date_of_birth;
        String msg = getString(R.string.please_enter_valid_date_of_birth);
        et_date_of_birth.setError(msg);
        et_date_of_birth.requestFocus();
        return false;
    }
    if (!et_name.getText().toString().trim().matches("[a-zA-Z ]+")) {
        EditText flagEditfield = et_name;
        String msg = getString(R.string.please_enter_name);
        et_name.setError(msg);
        et_name.requestFocus();
        return false;
    }
//    if (!et_email.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+")) {
    if (!et_email.getText().toString().trim().contains("@")) {
        EditText flagEditfield = et_email;
        String msg = getString(R.string.please_enter_valid_emailId);
        et_email.setError(msg);
        et_email.requestFocus();
        return false;
    }
    if (sp_category.getSelectedItemPosition() > 0) {
        String itemvalue = String.valueOf(sp_category.getSelectedItem());
    } else {
        TextView errorTextview = (TextView) sp_category.getSelectedView();
        errorTextview.setError("Error");
        errorTextview.requestFocus();
        return false;
    }
    if (et_address.getText().toString().trim().equalsIgnoreCase("")) {
        EditText flagEditfield = et_address;
        String msg = getString(R.string.please_enter_address);
        et_address.setError(msg);
        et_address.requestFocus();
        return false;
    }

    return ret;
}

    private void getAge(long selectedMilli) {
        Date dateOfBirth = new Date(selectedMilli);
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
            age--;
        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < dob
                .get(Calendar.DAY_OF_MONTH)) {
            age--;
        }

        if (age < 18) {
            //do something
        } else {

        }

        str = age + "";
        Log.d("", getClass().getSimpleName() + ": Age in year= " + age);
    }
//    public static boolean dobdateValidate(String date) {
//        boolean result = false;
//        @SuppressLint({"NewApi", "LocalSuppress"}) SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
//        try {
//            @SuppressLint({"NewApi", "LocalSuppress"}) Date parseddate = (Date) sdf.parse(date);
//            Calendar c2 = Calendar.getInstance();
//            c2.add(Calendar.DAY_OF_YEAR, -18);
//            Date dateObj2 = new Date(System.currentTimeMillis());
//            if (parseddate.before(c2.getTime())) {
//                result = true;
//            }
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return result;
//    }

}