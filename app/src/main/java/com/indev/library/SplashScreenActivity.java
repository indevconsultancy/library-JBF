package com.indev.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.SplashScreen;

import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;

import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    SqliteDatabase sqliteHelper;
    SharedPrefHelper sharedPrefHelper;
    private static String splashLoaded = "No";
    ImageView imageView;
    TextView textView2,textView3,textView4;

    public static final String MainPP_SP = "MainPP_data";
    public static final int R_PERM = 2822;
    private static final int REQUEST = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        imageView=findViewById(R.id.imageView);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(SplashScreenActivity.this,LoginActivity.class);
//                startActivity(intent);
//
//
//            }
//        },3000);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);
        sqliteHelper = new SqliteDatabase(this);
        sharedPrefHelper = new SharedPrefHelper(this);
        //  mProgressDialog=new ProgressDialog(this);
        sqliteHelper.openDataBase();
        SharedPreferences settings = getSharedPreferences(MainPP_SP, 0);
        HashMap<String, String> map = (HashMap<String, String>) settings.getAll();

        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("TAG","@@@ IN IF Build.VERSION.SDK_INT >= 23");
            //if (uiHelper.checkSelfPermissions(this)); //make pic image permission on splash
            String[] PERMISSIONS = {
//
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            if (!hasPermissions(this, PERMISSIONS)) {
                Log.d("TAG","@@@ IN IF hasPermissions");
                ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, REQUEST );
            } else {
                Log.d("TAG","@@@ IN ELSE hasPermissions");
                callNextActivity();
            }
        } else {
            Log.d("TAG","@@@ IN ELSE  Build.VERSION.SDK_INT >= 23");
            callNextActivity();
        }
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.side_slide);
////        imageView.startAnimation(animation);
//        textView2.startAnimation(animation);
//        textView3.startAnimation(animation);
//        textView4.startAnimation(animation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "@@@ PERMISSIONS grant");
                    callNextActivity();
                } else {
                    Log.d("TAG", "@@@ PERMISSIONS Denied");
                    Toast.makeText(this, "PERMISSIONS Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void callNextActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // String hh=signingkey("4666b834096f2e0f7247b07fc6791de0","c//f4cO+KzRcYgWJUGfTaO4ubxTyfh2YaRadph9GIMJX7IoJsnQmo1GWlTuM7BDf","POST","50.62.6.159","{\"source_identifier\":\"23002\",\"patient_identifier_type\":\"uhid/ipd\",\"patient_identifier\":\"2134390\"}");
                // Log.e("jj",hh);
//                lngTypt = sharedPrefHelper.getString("languageID","en");
//
//                if (lngTypt.equals("en")){
//
//                }

                splashLoaded = sharedPrefHelper.getString("isSplashLoaded", "No");
                String islogin= sharedPrefHelper.getString("isLogin", "");

                if (splashLoaded.equals("No")) {
                    DataDownload dataDownload = new DataDownload();
                    dataDownload.getMasterTables(SplashScreenActivity.this);
                } else if(islogin.equals("yes")) {
                    Intent intent = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}