package com.indev.library;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.indev.library.SqliteHelper.SqliteDatabase;

import java.util.ArrayList;
import java.util.Calendar;


public class DashBoardActivity extends AppCompatActivity {
    Button next;
    private PieChart pieChart;
    private PieChart pieChart1;
    private PieChart pieChart2;
    SqliteDatabase sqliteDatabase;
   int total_book_count=0;
   int total_issue_book_count=0;
   int receive_book_count=0;
   float issue=0;
   float receve=0;
   float gender=0;
   float male_count=0;
   int female_count=0;
   float male=0;
   float female=0;
   int age_5_20=0;
   int age_20_35=0;
   int age_35_55=0;
    int age_20_25=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        getSupportActionBar().setTitle("DashBoard");
        intializeAll_And_CallAllMethod();
        sqliteDatabase=new SqliteDatabase(this);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashBoardActivity.this,DashBoardBaarChartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        total_book_count= Integer.parseInt(sqliteDatabase.getBookCountPieChart());
        total_issue_book_count= Integer.parseInt(sqliteDatabase.getIssueBookCount());
        receive_book_count= Integer.parseInt(sqliteDatabase.getReceiveBookCount());
        issue= ((float) total_issue_book_count / (float) total_book_count)*100;
        receve= ((float) receive_book_count / (float) total_book_count)*100;

        gender= Integer.parseInt(sqliteDatabase.getGenderCount());
        male_count= Integer.parseInt(sqliteDatabase.getGenderMaleCount());
        female_count= Integer.parseInt(sqliteDatabase.getGenderFemaleCount());

        male= (male_count / gender)*100;
        female= (female_count / gender)*100;
         //Age

        age_5_20= Integer.parseInt(sqliteDatabase.getAge5_20Count());
        age_20_35= Integer.parseInt(sqliteDatabase.getAge20_35Count());
        age_35_55= Integer.parseInt(sqliteDatabase.getAge35_55Count());
        age_20_25=Integer.parseInt(sqliteDatabase.getAge20_25Count());



//        issue=issue*100;
        Log.e(TAG, "aged: "+age_5_20 );

        setupPieChart();
        loadPieChartData();
        setupPieChart1();
        loadPieChartData1();
        setupPieChart2();
        loadPieChartData2();

    }
    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(9);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Age Wise Subscriber");
        pieChart.setCenterTextSize(13);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);



    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(age_5_20, " 5-10 Years" + " [ " +age_5_20+"]"));
        entries.add(new PieEntry(age_20_35, " 10-15 Years" + " ["+age_20_35+"]"));
        entries.add(new PieEntry(age_35_55, " 15-20 Years" + " ["+age_35_55+"]"));
        entries.add(new PieEntry(age_20_25, " 20-25 Years" + " ["+age_20_25+"]"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Age Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
    private void setupPieChart1() {
        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setUsePercentValues(true);
        pieChart1.setEntryLabelTextSize(10);
        pieChart1.setEntryLabelColor(Color.BLACK);
        pieChart1.setCenterText("Gender Wise Subscriber");
        pieChart1.setCenterTextSize(13);
        pieChart1.getDescription().setEnabled(false);

        Legend l = pieChart1.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData1() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(male, "Male"+ " ["+ male_count+"]"));
        entries.add(new PieEntry(female, "Female"+ " ["+ female_count+"]"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Gender Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart1));
        data.setValueTextSize(12);
        data.setValueTextColor(Color.BLACK);

        pieChart1.setData(data);
        pieChart1.invalidate();

        pieChart1.animateY(1400, Easing.EaseInOutQuad);
    }
    private void setupPieChart2() {
        pieChart2.setDrawHoleEnabled(true);
        pieChart2.setUsePercentValues(true);
        pieChart2.setEntryLabelTextSize(10);
        pieChart2.setEntryLabelColor(Color.BLACK);
        pieChart2.setCenterText("Book " + "Issue/Received");
        pieChart2.setCenterTextSize(13);
        pieChart2.getDescription().setEnabled(false);

        Legend l = pieChart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData2() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(issue, "Book Issue" + " ["+ total_issue_book_count+"]"));
        entries.add(new PieEntry(receve, "Book Received" + " [" + receive_book_count+"]"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Book Issue/Received");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart2));
        data.setValueTextSize(12);
        data.setValueTextColor(Color.BLACK);

        pieChart2.setData(data);
        pieChart2.invalidate();

        pieChart2.animateY(1400, Easing.EaseInOutQuad);
    }

    private void intializeAll_And_CallAllMethod() {
        pieChart=findViewById(R.id.activity_main_piechart);
        pieChart1=findViewById(R.id.activity_main_piechart1);
        pieChart2=findViewById(R.id.activity_main_piechart2);
        next=findViewById(R.id.next);
        //All Method

    }

}