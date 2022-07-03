package com.indev.library;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.indev.library.BarrChart.MyValueFormatter;
import com.indev.library.SqliteHelper.SqliteDatabase;

import java.util.ArrayList;

public class DashBoardBaarChartActivity extends AppCompatActivity {
    private BarChart mChart;
    private BarChart mChart1;
    Button next;
    SqliteDatabase sqliteDatabase;
    int total_qualification_count=0;
    float class_5=0;
    int class_6=0;
    int class_7=0;
    int class_8=0;
    int class_9=0;
    int class_10=0;
    int class_11=0;
    int class_12=0;
    int class_other=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_baar_chart);
        getSupportActionBar().setTitle("Standerd Wise Subscriber");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mChart=(BarChart) findViewById(R.id.chart2);
        next=findViewById(R.id.next);
        sqliteDatabase=new SqliteDatabase(this);
        mChart.setMaxVisibleValueCount(250);

        setData(1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashBoardBaarChartActivity.this,DashBoardLastActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        total_qualification_count= Integer.parseInt(sqliteDatabase.getQualificationPieChart());
        class_5= Integer.parseInt(sqliteDatabase.getQualification1PieChart());
        class_6= Integer.parseInt(sqliteDatabase.getQualification2PieChart());
        class_7= Integer.parseInt(sqliteDatabase.getQualification3PieChart());
        class_8= Integer.parseInt(sqliteDatabase.getQualification4PieChart());
        class_9= Integer.parseInt(sqliteDatabase.getQualification5PieChart());
        class_10= Integer.parseInt(sqliteDatabase.getQualification6PieChart());
        class_11= Integer.parseInt(sqliteDatabase.getQualification7PieChart());
        class_12= Integer.parseInt(sqliteDatabase.getQualification8PieChart());
        class_other= Integer.parseInt(sqliteDatabase.getQualification9PieChart());





    }
    public  void setData(int count){
        ArrayList<BarEntry> yValues=new ArrayList<>();

        for(int i=0;i<count;i++)
        {
            float val1=(float) (Math.random() * count) +class_5;
            float val2=(float) (Math.random() * count) +class_6;
            float val3=(float) (Math.random() * count) +class_7;
            float val4=(float) (Math.random() * count) +class_8;
            float val5=(float) (Math.random() * count) +class_9;
            float val6=(float) (Math.random() * count) +class_10;
            float val7=(float) (Math.random() * count) +class_11;
            float val8=(float) (Math.random() * count) +class_12;
            float val9=(float) (Math.random() * count) +class_other;



            yValues.add(new BarEntry(i,new float[]{val1,val2,val3,val4,val5,val6,val7,val8,val9}));

        }
        BarDataSet set1;

        set1=new BarDataSet(yValues,"S");
        set1.setDrawIcons(false);
        set1.setStackLabels(new String[]{"Class-5","Class-6","Class-7","Class-8","Class-9"
                ,"Class-10","Class-11","Class-12","Other"});
        set1.setColors(ColorTemplate.JOYFUL_COLORS);
        BarData data=new BarData(set1);
        data.setValueFormatter(new MyValueFormatter());

        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.invalidate();
        mChart.getDescription().setEnabled(false);
    }


}