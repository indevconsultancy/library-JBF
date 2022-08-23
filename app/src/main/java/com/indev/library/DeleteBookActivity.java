package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.indev.library.utils.CommonClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteBookActivity extends AppCompatActivity {
    Button btn_submit;
    EditText et_remark;
    Spinner sp_bookk;
    AddBookPojo addBookPojo;
    String resourse_namee="";
    SqliteDatabase sqliteDatabase;
    SharedPrefHelper sharedPrefHelper;
    HashMap<Integer, String> resourceNameHM;
    HashMap<Integer, String> subscriberNameHM;
    String st_book_name = "";
    String resource_id ="";
    ProgressDialog dialog;
    int statuss=0;
    String resourse_id="";
    ArrayList<String> resourceArrayList, subscriberArrayList;
    String book_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);
        setTitle("Delete a Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initilizeAll();

        sharedPrefHelper=new SharedPrefHelper(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            resourse_namee = bundle.getString("resourse_name", "");
        }
        getResourceSpinner(resourse_namee);



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {
                    addBookPojo = new AddBookPojo();
                    addBookPojo.setRemark(et_remark.getText().toString().trim());
                    addBookPojo.setStatus("0");
                    addBookPojo.setResource_id(resource_id);

                    sqliteDatabase.updateeDelete(addBookPojo, resource_id);
                    if (CommonClass.isInternetOn(getApplicationContext())) {

                        Gson gson = new Gson();
                        String data = gson.toJson(addBookPojo);
                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                        RequestBody body = RequestBody.create(JSON, data);
                        DeleteBookRegistration(body, String.valueOf(resource_id));
                        Log.e("resource", "registration: " + data);
                    } else {
                        Intent intent = new Intent(DeleteBookActivity.this, BookListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            }
        });



    }

    private void initilizeAll() {
        btn_submit=findViewById(R.id.btn_submit);
        et_remark=findViewById(R.id.et_remark);
        sp_bookk=findViewById(R.id.sp_bookk);

        resourceArrayList=new ArrayList<>();
        resourceNameHM=new HashMap<>();
        sqliteDatabase = new SqliteDatabase(getApplicationContext());

    }
    //All Spinner
    private void getResourceSpinner( String resourse_namee) {

        resourceArrayList.clear();
        resourceNameHM = sqliteDatabase.getAllBooK(resourse_namee);
        for (int i = 0; i < resourceNameHM.size(); i++) {
            resourceArrayList.add(resourceNameHM.values().toArray()[i].toString().trim());
        }
        resourceArrayList.add(0, "Select Book Name");

        ArrayAdapter book_adapter = new ArrayAdapter(this,R.layout.spinner_lists, resourceArrayList);
        book_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_bookk.setAdapter(book_adapter);

//        String check= sharedPrefHelper.getString("spinner_type","");
//        if (check.equalsIgnoreCase("spinner_issue")){
        int pos = book_adapter.getPosition(resourse_namee);
        sp_bookk.setSelection(pos);
//             sp_book.setEnabled(false);
//             sp_book.requestFocus();
//
//        }else
//        {
//            sp_book.setEnabled(true);
//        }



        sp_bookk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sp_bookk.getSelectedItem().toString().trim().equalsIgnoreCase("Select Book Name")) {
                    if (sp_bookk.getSelectedItem().toString().trim() != null) {
                        resource_id = resourceNameHM.keySet().toArray()[i-1].toString();
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void DeleteBookRegistration(RequestBody body, String resourse_id) {
        dialog = ProgressDialog.show(this, "", "Please wait...", true);
        ClientAPI.getClient().create(Library_API.class).DeleteBook(body).enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("TAG", "onResponse: " + jsonObject.toString() );
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    if(status.equals("1"))
                    {
                        sqliteDatabase.DeleteBookupdate("resource", "resource_id='" + resourse_id + "'");
                        Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(DeleteBookActivity.this, BookListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(DeleteBookActivity.this, "Book Not Delete", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DeleteBookActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Book Registration", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });

    }
    private boolean checkValidation() {
        boolean ret = true;
        if (sp_bookk.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(sp_bookk.getSelectedItem());
        } else {
            TextView errorTextview = (TextView) sp_bookk.getSelectedView();
            errorTextview.setError("Error");
            errorTextview.requestFocus();
            Toast.makeText(DeleteBookActivity.this, "Please Select Book Name", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (et_remark.getText().toString().trim().equalsIgnoreCase("")) {
            EditText flagEditfield = et_remark;
            String msg = getString(R.string.please_enter_remark);
            et_remark.setError(msg);
            et_remark.requestFocus();
            return false;
        }

        return ret;
    }

}