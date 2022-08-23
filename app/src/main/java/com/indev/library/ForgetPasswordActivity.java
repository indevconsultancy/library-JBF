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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.Model.ChangePasswordPojo;
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

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText et_enter_otp,et_user_email;
    LinearLayout ll_otpp,ll_user_email,ll_bt_resetpwd,ll_otp_submitt;
    TextView tv_forgot_password,tv_enter_otp;
    Button bt_reset_password,bt_otp_submit;
    SqliteDatabase sqliteDatabase;
    SharedPrefHelper sharedPrefHelper;
    private ProgressDialog dialog;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initlizeAll();
        bt_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    callForgetPassword();
                    ll_otp_submitt.setVisibility(View.VISIBLE);
                    ll_otpp.setVisibility(View.VISIBLE);
                    bt_otp_submit.setVisibility(View.VISIBLE);

                    ll_bt_resetpwd.setVisibility(View.GONE);
                    ll_user_email.setVisibility(View.GONE);
                    bt_reset_password.setVisibility(View.GONE);
                }
                else {
                    ll_otp_submitt.setVisibility(View.GONE);
                    ll_otpp.setVisibility(View.GONE);
                    bt_otp_submit.setVisibility(View.GONE);

                    ll_bt_resetpwd.setVisibility(View.VISIBLE);
                    ll_user_email.setVisibility(View.VISIBLE);
                    bt_reset_password.setVisibility(View.VISIBLE);
                }
            }
        });
        bt_otp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if(checkValidationotp()){
                 callConfirmOtp();
             }
            }
        });


    }

    private void initlizeAll() {
        et_enter_otp=findViewById(R.id.et_enter_otp);
        et_user_email=findViewById(R.id.et_user_email);
        ll_otpp=findViewById(R.id.ll_otpp);
        ll_user_email=findViewById(R.id.ll_user_email);
        ll_bt_resetpwd=findViewById(R.id.ll_bt_resetpwd);
        ll_otp_submitt=findViewById(R.id.ll_otp_submitt);
        tv_forgot_password=findViewById(R.id.tv_forgot_password);
        tv_enter_otp=findViewById(R.id.tv_enter_otp);
        bt_reset_password=findViewById(R.id.bt_reset_password);
        bt_otp_submit=findViewById(R.id.bt_otp_submit);
        sqliteDatabase=new SqliteDatabase(this);
        sharedPrefHelper=new SharedPrefHelper(this);

    }
    private boolean checkValidation() {
        if (!et_user_email.getText().toString().trim().contains("@gmail")) {
            Toast.makeText(context, R.string.please_enter_user_valid_email_id, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean checkValidationotp() {
        if (et_enter_otp.getText().toString().trim().length() == 0) {
            Toast.makeText(context, R.string.please_enter_valid_otp, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void callForgetPassword() {
        dialog = ProgressDialog.show(context, "", getString(R.string.plase), true);
        ChangePasswordPojo changePasswordPojo = new ChangePasswordPojo();

        changePasswordPojo.setEmail(et_user_email.getText().toString().trim());



        Gson gson = new Gson();
        String data = gson.toJson(changePasswordPojo);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        ClientAPI.getClient().create(Library_API.class).ForgetPasswordApi(body).enqueue(new Callback<JsonObject>() {
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

                        ll_otp_submitt.setVisibility(View.VISIBLE);
                        ll_otpp.setVisibility(View.VISIBLE);
                        bt_otp_submit.setVisibility(View.VISIBLE);

                        ll_bt_resetpwd.setVisibility(View.GONE);
                        ll_user_email.setVisibility(View.GONE);
                        bt_reset_password.setVisibility(View.GONE);
                        dialog.dismiss();
//                        finish();
//
                    } else {

                        Toast.makeText(ForgetPasswordActivity.this, "" + message, Toast.LENGTH_LONG).show();
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
    private void callConfirmOtp() {
        dialog = ProgressDialog.show(context, "", getString(R.string.plase), true);
        ChangePasswordPojo changePasswordPojo = new ChangePasswordPojo();

        changePasswordPojo.setEmail(et_user_email.getText().toString().trim());
        changePasswordPojo.setOtp(et_enter_otp.getText().toString().trim());



        Gson gson = new Gson();
        String data = gson.toJson(changePasswordPojo);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        ClientAPI.getClient().create(Library_API.class).ConfirmOtpApi(body).enqueue(new Callback<JsonObject>() {
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

                        Intent intent=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
//
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, "" + message, Toast.LENGTH_LONG).show();
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