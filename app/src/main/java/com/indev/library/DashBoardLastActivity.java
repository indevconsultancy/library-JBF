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
import com.indev.library.SqliteHelper.SqliteDatabase;

import java.util.ArrayList;

public class DashBoardLastActivity extends AppCompatActivity {
    private BarChart mChart;
    Button next;
    SqliteDatabase sqliteDatabase;
    int english_count=0;
    int hindi_count=0;
    int bengali_count=0;
    int odia_count=0;
    int punjabi_count=0;
    int marathi_count=0;
    int tamil_count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_last);
        mChart=(BarChart) findViewById(R.id.chart1);
        getSupportActionBar().setTitle("Language Wise Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sqliteDatabase=new SqliteDatabase(this);
        mChart.setMaxVisibleValueCount(40);
        next=findViewById(R.id.next);
        setData2(1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashBoardLastActivity.this,BarChartLast1Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        english_count= Integer.parseInt(sqliteDatabase.getEnglishCount());
        hindi_count= Integer.parseInt(sqliteDatabase.getHindiCount());
        bengali_count= Integer.parseInt(sqliteDatabase.getBangaliCount());
        odia_count= Integer.parseInt(sqliteDatabase.getOdiaCount());
        punjabi_count= Integer.parseInt(sqliteDatabase.getPunjabiCount());
        marathi_count= Integer.parseInt(sqliteDatabase.getMarathiCount());
        tamil_count= Integer.parseInt(sqliteDatabase.getTamilCount());



    }
    public  void setData2(int count){
        ArrayList<BarEntry> yValues=new ArrayList<>();

        for(int i=0;i<count;i++)
        {
            float val1=(float) (Math.random() * count) +english_count;
            float val2=(float) (Math.random() * count) +hindi_count;
            float val3=(float) (Math.random() * count) +bengali_count;
            float val4=(float) (Math.random() * count) +odia_count;
            float val5=(float) (Math.random() * count) +punjabi_count;
            float val6=(float) (Math.random() * count) +marathi_count;
            float val7=(float) (Math.random() * count) +tamil_count;

            yValues.add(new BarEntry(i,new float[]{val1,val2,val3,val4,val5,val6,val7}));

        }
        BarDataSet set1;

        set1=new BarDataSet(yValues,"Language");
        set1.setDrawIcons(false);
        set1.setStackLabels(new String[]{"English","Hindi","Bengali","Odia","Punjabi","Marathi","Tamil"});
        set1.setColors(ColorTemplate.JOYFUL_COLORS);
        BarData data=new BarData(set1);
        data.setValueFormatter(new MyValueFormatter());

        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.invalidate();
        mChart.getDescription().setEnabled(false);
    }
}