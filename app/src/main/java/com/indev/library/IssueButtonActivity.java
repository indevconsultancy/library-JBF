package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.Model.BookIssuePojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.indev.library.utils.CommonClass;

import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueButtonActivity extends AppCompatActivity {
//    String [] book_name ={"The Great Gatsby","Lolita","Brave New World","To The Lighthouse"};
//    String [] issue_to_name ={"Manish Kumar","Deepak Gupta","Ajay Yadav","Dinesh Kumar","Sunil Kumar","Mohan Kumar"};

    Spinner sp_book,sp_issue_to_name;
    public static EditText et_issue_date;
    SqliteDatabase sqliteDatabase;
    Button alldataSubmit;
    BookIssuePojo bookIssuePojo;
    AddBookPojo bookIssuePojo1;
    AddBookPojo addBookPojo;
    int mYear,mMonth,mDay,year,month,day;
    DatePickerDialog datePickerDialog;
    HashMap<Integer, String> resourceNameHM;
    HashMap<Integer, String> subscriberNameHM;
    ArrayList<String> resourceArrayList, subscriberArrayList;
    ArrayList<AddBookPojo> bookIdList;
    private String st_resource = "",st_subscriber="";
   String resource_id ="";
    String subscriber_idd = "";
    String local_id="";
    ProgressDialog dialog;

    String st_book_name = "", book_qty="";
    private String type="",screen_type="";
    boolean isEditable = false;
    SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_button);
        getSupportActionBar().setTitle("Book Issue");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intializeAll();
        sqliteDatabase = new SqliteDatabase(getApplicationContext());
         sharedPrefHelper=new SharedPrefHelper(this);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            st_book_name = bundle.getString("resourse_name", "");

            local_id = bundle.getString("local_id", "");
            sharedPrefHelper.setString("local_id","");
            book_qty = bundle.getString("book_qty", "");
//            bookIdList = sqliteDatabase.getBookId(st_book_name);

        }

         getResourceSpinner();
         getSubscriberSpinner();

//        getStateSpinner();
//        ArrayAdapter adapter=new ArrayAdapter(IssueButtonActivity.this, R.layout.spinner_lists,book_name);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_book.setAdapter(adapter);
//        ArrayAdapter adapter1=new ArrayAdapter(IssueButtonActivity.this, R.layout.spinner_lists,issue_to_name);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_issue_to_name.setAdapter(adapter1);
        //Date Picker



        alldataSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {


                    bookIssuePojo = new BookIssuePojo();
                    bookIssuePojo.setLibrarain_id(sharedPrefHelper.getString("librarain_id", ""));
                    bookIssuePojo.setIssue_recieve_type("1");
                    bookIssuePojo.setResource_id(String.valueOf(resource_id));
                    bookIssuePojo.setSubscriber_id(String.valueOf(subscriber_idd));
                    bookIssuePojo.setIssue_date(et_issue_date.getText().toString().trim());

                    long local_id = sqliteDatabase.IssueBook(bookIssuePojo);
                    if (CommonClass.isInternetOn(getApplicationContext())) {
                        Gson gson = new Gson();
                        String data = gson.toJson(bookIssuePojo);
                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                        RequestBody body = RequestBody.create(JSON, data);
                        addBookIssueRegistration(body, String.valueOf(local_id));
                        Log.e("transaction_book", "registration: " + data);
                    }else
                    {
                        Intent intent  = new Intent(IssueButtonActivity.this, BookReceiverListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
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
                        String book_count="";
                        book_count=sqliteDatabase.getBookCount(st_book_name);
//                       String  book_count=sharedPrefHelper.getString("available_count","");
                        int qty = Integer.parseInt(book_count)-1;
                        sqliteDatabase.updateBookQty("resource", "resource_id='" + resource_id + "'",qty,"available_count" );
                        sqliteDatabase.updateBookQty("resource", "resource_id='" + resource_id + "'",1,"resource_status" );

//                        sqliteDatabase.updateBookQty("resource", "local_id='" + local_id + "'",0,"available_count" );
                        Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(IssueButtonActivity.this, BookReceiverListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(IssueButtonActivity.this, "Not Issue", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(IssueButtonActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Issue Success", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });

    }
    private void intializeAll()
    {

        sp_book =findViewById(R.id.sp_book);
        sp_issue_to_name =findViewById(R.id.sp_issue_to_name);
        et_issue_date =findViewById(R.id.et_issue_date);
        alldataSubmit=findViewById(R.id.alldataSubmit);
        resourceArrayList=new ArrayList<>();
        subscriberArrayList=new ArrayList<>();
        resourceNameHM=new HashMap<>();
        subscriberNameHM=new HashMap<>();

    }
    //All Spinner
    private void getResourceSpinner() {

        resourceArrayList.clear();
        resourceNameHM = sqliteDatabase.getAllBooK(st_book_name);
         for (int i = 0; i < resourceNameHM.size(); i++) {
            resourceArrayList.add(resourceNameHM.values().toArray()[i].toString().trim());
        }
        resourceArrayList.add(0, "Select Book Name");

        ArrayAdapter book_adapter = new ArrayAdapter(this,R.layout.spinner_lists, resourceArrayList);
        book_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_book.setAdapter(book_adapter);

//        String check= sharedPrefHelper.getString("spinner_type","");
//        if (check.equalsIgnoreCase("spinner_issue")){
            int pos = book_adapter.getPosition(st_book_name);
            sp_book.setSelection(pos);
//             sp_book.setEnabled(false);
//             sp_book.requestFocus();
//
//        }else
//        {
//            sp_book.setEnabled(true);
//        }



        sp_book.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sp_book.getSelectedItem().toString().trim().equalsIgnoreCase("Select Book Name")) {
                    if (sp_book.getSelectedItem().toString().trim() != null) {
                        resource_id = resourceNameHM.keySet().toArray()[i-1].toString();
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getSubscriberSpinner() {

        subscriberArrayList.clear();
        String user_id=sharedPrefHelper.getString("librarain_id", "");

        subscriberNameHM = sqliteDatabase.getAllSubscriber(user_id);

        HashMap<Integer,String > sortedMapAsc = sortByComparator(subscriberNameHM);
        for (int i = 0; i < sortedMapAsc.size(); i++) {
            subscriberArrayList.add(sortedMapAsc.values().toArray()[i].toString().trim());
        }
        Collections.sort(subscriberArrayList, String.CASE_INSENSITIVE_ORDER);

        subscriberArrayList.add(0, "Select Your Name");

        ArrayAdapter book_adapter = new ArrayAdapter(this,R.layout.spinner_lists, subscriberArrayList);
        book_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_issue_to_name.setAdapter(book_adapter);
//        if (screen_type.equals("edit_profile")) {
//            st_state = sqliteDatabase.getPSStateSp(editpregnantWomenRegisterTable.getState_id());
//            int pos = state_adapter.getPosition(st_state);
//            sp_state.setSelection(pos);
//        }


        sp_issue_to_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sp_issue_to_name.getSelectedItem().toString().trim().equalsIgnoreCase("Select Your Name")) {
                    if (sp_issue_to_name.getSelectedItem().toString().trim() != null) {
                        String subscriber_name = sp_issue_to_name.getSelectedItem().toString().trim();
                        for (int j = 0; j < sortedMapAsc.size(); j++) {
                            if(sortedMapAsc.values().toArray()[j].toString().trim().equals(subscriber_name)){
                                subscriber_idd=sortedMapAsc.keySet().toArray()[j].toString().trim();
                            }
                        }
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private HashMap<Integer, String> sortByComparator(HashMap<Integer, String> subscriberNameHM) {
        List<Map.Entry<Integer, String>> list = new LinkedList<Map.Entry<Integer, String>>(subscriberNameHM.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() {
            public int compare(Map.Entry<Integer, String> o1,
                               Map.Entry<Integer, String> o2) {

                return o1.getValue().compareTo(o2.getValue());

            }
        });

        // Maintaining insertion order with the help of LinkedList
        HashMap<Integer, String> sortedMap = new LinkedHashMap<Integer, String>();
        for (Map.Entry<Integer, String> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    private boolean checkValidation() {
        boolean ret = true;

        if (sp_book.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(sp_book.getSelectedItem());
        } else {
            TextView errorTextview = (TextView) sp_book.getSelectedView();
            errorTextview.setError("Error");
            errorTextview.requestFocus();
            return false;
        }
//        if (sp_issue_to_name.getSelectedItemPosition() > 0) {
//            String itemvalue = String.valueOf(sp_issue_to_name.getSelectedItem());
//        } else {
//            TextView errorTextview = (TextView) sp_issue_to_name.getSelectedView();
//            errorTextview.setError("Error");
//            errorTextview.requestFocus();
//            return false;
//        }
        if (et_issue_date.getText().toString().trim().length() == 0) {
            EditText flagEditfield = et_issue_date;
            String msg = getString(R.string.please_enter_issue_date);
            et_issue_date.setError(msg);
            et_issue_date.requestFocus();
            return false;
        }

        return ret;
    }

    @SuppressLint("NewApi")
    public void show_callender(View vw) {
        DialogFragment newFragment = new DatePickerFragment1();
        newFragment.show(getFragmentManager(), "datePicker");
//        flag = 0;

    }
    @SuppressLint("NewApi")
    public static class DatePickerFragment1 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);

//            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

            c.add(Calendar.DAY_OF_MONTH, -7);
            Date result = c.getTime();
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.getDatePicker().setMinDate(result.getTime());

            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String dt = day + "-" + month + "-" + year;
            Calendar c = Calendar.getInstance();
            c.set(year, month, day, 0, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(c.getTime());
            et_issue_date.setText(formattedDate);
            et_issue_date.setError(null);
        }
    }

}