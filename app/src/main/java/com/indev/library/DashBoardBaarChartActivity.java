package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.indev.library.BarrChart.MyValueFormatter;

import java.util.ArrayList;

public class DashBoardBaarChartActivity extends AppCompatActivity {
    private BarChart mChart;
    private BarChart mChart1;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_baar_chart);
        getSupportActionBar().setTitle("DashBoard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mChart=(BarChart) findViewById(R.id.chart1);
        mChart1=(BarChart) findViewById(R.id.chart2);
        next=findViewById(R.id.next);

        mChart.setMaxVisibleValueCount(40);
        mChart1.setMaxVisibleValueCount(40);

        setData(1);
        setData1(1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashBoardBaarChartActivity.this,DashBoardLastActivity.class);
                startActivity(intent);
            }
        });

    }
    public  void setData(int count){
        ArrayList<BarEntry> yValues=new ArrayList<>();

        for(int i=0;i<count;i++)
        {
            float val1=(float) (Math.random() * count) +20;
            float val2=(float) (Math.random() * count) +20;
            float val3=(float) (Math.random() * count) +20;

            yValues.add(new BarEntry(i,new float[]{val1,val2,val3}));

        }
        BarDataSet set1;

        set1=new BarDataSet(yValues,"Subscriber");
        set1.setDrawIcons(false);
        set1.setStackLabels(new String[]{"Student Class < 5","Student Class 5-10","Student Class > 10"});
        set1.setColors(ColorTemplate.JOYFUL_COLORS);
        BarData data=new BarData(set1);
        data.setValueFormatter(new MyValueFormatter());

        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.invalidate();
        mChart.getDescription().setEnabled(false);
    }
    public  void setData1(int count){
        ArrayList<BarEntry> xValues=new ArrayList<>();

        for(int i=0;i<count;i++)
        {
            float val1=(float) (Math.random() * count) +20;
            float val2=(float) (Math.random() * count) +20;
            float val3=(float) (Math.random() * count) +20;

            xValues.add(new BarEntry(i,new float[]{val1,val2,val3}));

        }
        BarDataSet set1;

        set1=new BarDataSet(xValues,"Category wise Book");
        set1.setDrawIcons(false);
        set1.setStackLabels(new String[]{"Comics","Novel","Story"});
        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData data=new BarData(set1);
        data.setValueFormatter(new MyValueFormatter());

        mChart1.setData(data);
        mChart1.setFitBars(true);
        mChart1.invalidate();
        mChart1.getDescription().setEnabled(false);
    }

}