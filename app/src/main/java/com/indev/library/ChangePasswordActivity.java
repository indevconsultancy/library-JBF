package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.Model.ChangePasswordPojo;
import com.indev.library.Model.LoginPojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    Button change_pwd_button;
    EditText your_password,new_password,confirm_password;
    private ProgressDialog dialog;
    SqliteDatabase sqliteDatabase;
    SharedPrefHelper sharedPrefHelper;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intilizeAll();
        change_pwd_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    callChangePassword();
                }


            }
        });





    }
    private void intilizeAll() {
        change_pwd_button=findViewById(R.id.change_pwd_button);
        your_password=findViewById(R.id.your_password);
        new_password=findViewById(R.id.new_password);
        confirm_password=findViewById(R.id.confirm_password);
        sqliteDatabase=new SqliteDatabase(this);
        sharedPrefHelper=new SharedPrefHelper(this);

    }
    private boolean checkValidation() {
        if (your_password.getText().toString().trim().length() == 0) {
            Toast.makeText(context, R.string.please_enter_your_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (new_password.getText().toString().trim().length() == 0) {
            Toast.makeText(context, R.string.please_enter_new_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confirm_password.getText().toString().trim().length() == 0) {
            Toast.makeText(context, R.string.please_enter_confirm_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!confirm_password.getText().toString().trim().equals(new_password.getText().toString().trim())) {
            Toast.makeText(context, R.string.new_password_and_confirm_password_not_match, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void callChangePassword() {
        dialog = ProgressDialog.show(context, "", getString(R.string.plase), true);
        ChangePasswordPojo changePasswordPojo = new ChangePasswordPojo();

        changePasswordPojo.setPassword(your_password.getText().toString().trim());
        changePasswordPojo.setNew_password(new_password.getText().toString().trim());
        changePasswordPojo.setConfirm_password(confirm_password.getText().toString().trim());
        changePasswordPojo.setLibrarain_id(sharedPrefHelper.getString("librarain_id",""));


        Gson gson = new Gson();
        String data = gson.toJson(changePasswordPojo);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        ClientAPI.getClient().create(Library_API.class).ChangePasswordApi(body).enqueue(new Callback<JsonObject>() {
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

                        Intent intent=new Intent(ChangePasswordActivity.this,MainMenuActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
//
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "" + message, Toast.LENGTH_LONG).show();
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
}