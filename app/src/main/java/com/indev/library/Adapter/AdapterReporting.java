package com.indev.library.Adapter;

import static com.indev.library.RestAPI.ClientAPI.IMAGE_BASE_URL;
import static com.indev.library.RestAPI.ClientAPI.IMAGE_BASE_URL2;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.indev.library.Model.ActivityReportingPojo;
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



//        holder.txt_reporting.setText(arrayList.get(position).getActivity_id());
        String activity_name="";
        activity_name=sqliteDatabase.getActivityName(arrayList.get(position).getActivity_id());
        holder.txt_reporting.setText(activity_name);
        if (arrayList.get(position).getActivity_image2() != null && arrayList.get(position).getActivity_image2().length() > 200) {
            String[] img= arrayList.get(position).getActivity_image2().split(",");
//            byte[] decodedString = Base64.decode(arrayList.get(position).getActivity_image2(), Base64.NO_WRAP);
            byte[] decodedString = Base64.decode(img[0], Base64.NO_WRAP);
            InputStream inputStream = new ByteArrayInputStream(decodedString);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.img_reporting.setImageBitmap(bitmap);
        } else {
            try {
                Picasso.get()
                        .load(IMAGE_BASE_URL2+arrayList.get(position).getActivity_image2())
                        .placeholder(R.drawable.ic_baseline_photo_24)
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
