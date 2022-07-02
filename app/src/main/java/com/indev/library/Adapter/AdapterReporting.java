package com.indev.library.Adapter;

import static com.indev.library.RestAPI.ClientAPI.IMAGE_BASE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indev.library.IssueButtonActivity;
import com.indev.library.Model.ActivityReportingPojo;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.R;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AdapterReporting extends RecyclerView.Adapter<AdapterReporting.ViewHolder>
{
    Context context;
    ArrayList<ActivityReportingPojo> arrayList;
    SharedPrefHelper sharedPrefHelper;
    SqliteDatabase sqliteDatabase;

    public AdapterReporting(Context context, ArrayList<ActivityReportingPojo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sqliteDatabase=new SqliteDatabase(context);
        sharedPrefHelper=new SharedPrefHelper(context);
    }

    @NonNull
    @Override
    public AdapterReporting.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_reporting, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReporting.ViewHolder holder, @SuppressLint("RecyclerView") int position) {



        holder.txt_reporting.setText(arrayList.get(position).getReporting());

        if (arrayList.get(position).getPhoto() != null && arrayList.get(position).getPhoto().length() > 200) {
            byte[] decodedString = Base64.decode(arrayList.get(position).getPhoto(), Base64.NO_WRAP);
            InputStream inputStream = new ByteArrayInputStream(decodedString);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.img_reporting.setImageBitmap(bitmap);
        } else {
            try {
                Picasso.get()
                        .load(IMAGE_BASE_URL+arrayList.get(position).getPhoto())
                        .placeholder(R.drawable.camera)
                        .into(holder.img_reporting);
            }catch (Exception e){
                Log.d("Exception",""+e);
            }

        }
////

    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_reporting;
        ImageView img_reporting;

        public ViewHolder(View view) {
            super(view);
            img_reporting= itemView.findViewById(R.id.img_reporting);

            txt_reporting= itemView.findViewById(R.id.txt_reporting);

        }
    }
}
