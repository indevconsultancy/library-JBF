package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.indev.library.BarrChart.MyValueFormatter;

import java.util.ArrayList;

public class DashBoardLastActivity extends AppCompatActivity {
    private BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_last);
        mChart=(BarChart) findViewById(R.id.chart1);
        getSupportActionBar().setTitle("DashBoard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mChart.setMaxVisibleValueCount(40);
        setData2(1);

    }
    public  void setData2(int count){
        ArrayList<BarEntry> yValues=new ArrayList<>();

        for(int i=0;i<count;i++)
        {
            float val1=(float) (Math.random() * count) +10;
            float val2=(float) (Math.random() * count) +10;
            float val3=(float) (Math.random() * count) +10;
            float val4=(float) (Math.random() * count) +10;
            float val5=(float) (Math.random() * count) +10;
            float val6=(float) (Math.random() * count) +10;

            yValues.add(new BarEntry(i,new float[]{val1,val2,val3,val4,val5,val6}));

        }
        BarDataSet set1;

        set1=new BarDataSet(yValues,"Language");
        set1.setDrawIcons(false);
        set1.setStackLabels(new String[]{"Bengali","English","Hindi","Marathi","Odia","Punjabi"});
        set1.setColors(ColorTemplate.JOYFUL_COLORS);
        BarData data=new BarData(set1);
        data.setValueFormatter(new MyValueFormatter());

        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.invalidate();
        mChart.getDescription().setEnabled(false);
    }
}