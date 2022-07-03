package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.indev.library.BarrChart.MyValueFormatter;
import com.indev.library.SqliteHelper.SqliteDatabase;

import java.util.ArrayList;

public class BarChartLast1Activity extends AppCompatActivity {
    private BarChart mChart1;
    SqliteDatabase sqliteDatabase;
    int comics_count=0;
    int Novel_count=0;
    int Story_count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart_last1);
        getSupportActionBar().setTitle("Category wise Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mChart1=(BarChart) findViewById(R.id.chart1);
        sqliteDatabase=new SqliteDatabase(this);
        mChart1.setMaxVisibleValueCount(40);
        setData1(1);
        Story_count= Integer.parseInt(sqliteDatabase.getStoryCount());
        Novel_count= Integer.parseInt(sqliteDatabase.getNovelCount());
        comics_count= Integer.parseInt(sqliteDatabase.getComicsCount());


    }
    public  void setData1(int count){
        ArrayList<BarEntry> xValues=new ArrayList<>();

        for(int i=0;i<count;i++)
        {
            float val1=(float) (Math.random() * count) +Story_count;
            float val2=(float) (Math.random() * count) +Novel_count;
            float val3=(float) (Math.random() * count) +comics_count;

            xValues.add(new BarEntry(i,new float[]{val1,val2,val3}));

        }
        BarDataSet set1;

        set1=new BarDataSet(xValues,"Category wise Book");
        set1.setDrawIcons(true);
        set1.setStackLabels(new String[]{"Story","Novel","Comics"});
        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData data=new BarData(set1);
        data.setValueFormatter(new MyValueFormatter());

        mChart1.setData(data);
        mChart1.setFitBars(true);
        mChart1.invalidate();
        mChart1.getDescription().setEnabled(false);
    }
}