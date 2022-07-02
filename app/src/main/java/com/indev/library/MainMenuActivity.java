package com.indev.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.Model.BookIssuePojo;
import com.indev.library.Model.LoginPojo;
import com.indev.library.Model.SubscriberPojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;

import org.json.JSONObject;

import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenuActivity extends AppCompatActivity {
    CardView cv_subscriber,cv_book,cv_issue,cv_receiver,cv_activity_reporting,cv_exit,cv_teacher_dashboard,cv_syncronize;
    private ProgressDialog dialog;
    public DrawerLayout drawer_layout;
    private Context context = this;

    public ActionBarDrawerToggle actionBarDrawerToggle;
    SharedPrefHelper sharedPrefHelper;
    SqliteDatabase sqliteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().setTitle("Main Menu");
        intializeAll();


        sharedPrefHelper=new SharedPrefHelper(this);

        drawer_layout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer_layout, R.string.nav_open, R.string.nav_close);
        drawer_layout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        sqliteDatabase = new SqliteDatabase(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.logout)

                {
                    sharedPrefHelper.setString("isLogin", "");
                    Intent intent=new Intent(MainMenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
                else if(item.getItemId()==R.id.changePassword)
                {
                    Intent intent=new Intent(MainMenuActivity.this, ForgetPasswordActivity.class);
                    startActivity(intent);
                }
                DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        cv_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenuActivity.this,BookListActivity.class);
                startActivity(intent);
//                dialog = ProgressDialog.show(context, "", getString(R.string.plase), true);

            }
        });

        cv_subscriber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialog = ProgressDialog.show(context, "", getString(R.string.plase), true);
                Intent intent=new Intent(MainMenuActivity.this,SubscriberListActivity.class);
                startActivity(intent);

            }
        });
        cv_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefHelper.setString("list_type","issue");
//                dialog = ProgressDialog.show(context, "", getString(R.string.plase), true);
                Intent intent=new Intent(MainMenuActivity.this,BookReceiverListActivity.class);
                startActivity(intent);
            }
        });
        cv_receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefHelper.setString("list_type","recevier");
                Intent intent=new Intent(MainMenuActivity.this,BookReceiverListActivity.class);
                startActivity(intent);
            }
        });
        cv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        cv_activity_reporting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenuActivity.this,ReportingListActivity.class);
                startActivity(intent);
            }
        });
        cv_syncronize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenuActivity.this,SyncronizeActivity.class);
                startActivity(intent);
            }
        });
        cv_teacher_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainMenuActivity.this,DashBoardActivity.class);
                startActivity(intent);
            }
        });
    }
    private void intializeAll()
    {
        cv_teacher_dashboard=findViewById(R.id.cv_teacher_dashboard);
        cv_syncronize=findViewById(R.id.cv_syncronize);
        cv_subscriber=findViewById(R.id.cv_subscriber);
        cv_book =findViewById(R.id.cv_book);
        cv_issue=findViewById(R.id.cv_issue);
        cv_receiver=findViewById(R.id.cv_receiver);
        cv_activity_reporting=findViewById(R.id.cv_activity_reporting);
        cv_exit=findViewById(R.id.cv_exit);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.alert);
        builder.setTitle("Alert!");
        builder.setMessage(R.string.are_you_sure_to_want_to_exit_application);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finishAffinity();
//                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
//                startActivity(intent);
            }
        });


        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}