package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.indev.library.SqliteHelper.SqliteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddIssueActivity extends AppCompatActivity {
    EditText et_issue_date;
    int mYear,mMonth,mDay,year,month,day;
    DatePickerDialog datePickerDialog;
    Spinner sp_state,sp_district,sp_block,sp_village;
    HashMap<String, Integer> stateNameHM, districtNameHM, blockNameHM, villageNameHM;
    ArrayList<String> stateArrayList, distrcitArrayList, blockArrayList, villageArrayList;
    private String st_state = "",st_block="",st_district="", st_village = "",st_age="";
    int state_id = 0,district_id = 0, village_id = 0 ,block_id= 0;
    SqliteDatabase sqliteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);
        getSupportActionBar().setTitle("Add Issue Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_issue_date=findViewById(R.id.et_issue_date);
        intializeAll();
        sqliteDatabase = new SqliteDatabase(getApplicationContext());

        //Date Picker
        et_issue_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_issue_date.setError(null);
                et_issue_date.clearFocus();
                mYear=year;
                mMonth=month;
                mDay=day;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR); // current year
                mMonth = c.get(Calendar.MONTH); // current month
                mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AddIssueActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                et_issue_date.setText("" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff173e6d"));
            }
        });


    }
    private void intializeAll()
    {

        sp_state =findViewById(R.id.sp_state);
        sp_block =findViewById(R.id.sp_block);
        sp_village =findViewById(R.id.sp_village);
        sp_district =findViewById(R.id.sp_district);
//        alldataSubmit=findViewById(R.id.alldataSubmit);
//        et_mobile_no=findViewById(R.id.et_mobile_no);
        stateArrayList=new ArrayList<>();
        blockArrayList=new ArrayList<>();
        distrcitArrayList=new ArrayList<>();
        villageArrayList=new ArrayList<>();



    }

}