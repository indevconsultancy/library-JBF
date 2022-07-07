package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.indev.library.Model.SubscriberPojo;
import com.indev.library.SqliteHelper.SqliteDatabase;

public class DeleteSubscriberActivity extends AppCompatActivity {
    Button btn_submit;
    EditText et_remark;
    String subscriber_id="";
    SubscriberPojo subscriberPojo;
    SqliteDatabase sqliteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_subscriber);
        setTitle("Delete Subscriber");

        InitilizeAll();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            subscriber_id = bundle.getString("subscriber_id", "");
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscriberPojo=new SubscriberPojo();

            }
        });




    }

    private void InitilizeAll() {
        btn_submit=findViewById(R.id.btn_submit);
        et_remark=findViewById(R.id.et_remark);
        btn_submit=findViewById(R.id.btn_submit);
        sqliteDatabase = new SqliteDatabase(getApplicationContext());
    }
}