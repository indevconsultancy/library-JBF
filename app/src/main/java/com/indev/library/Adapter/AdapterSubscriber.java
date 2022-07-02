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

import com.indev.library.Model.SubscriberPojo;
import com.indev.library.R;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AdapterSubscriber extends RecyclerView.Adapter<AdapterSubscriber.ViewHolder>
{

    Context context;
    ArrayList<SubscriberPojo> arrayList;
    SqliteDatabase sqliteDatabase;
    String uuid="";
    public AdapterSubscriber(Context context, ArrayList<SubscriberPojo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sqliteDatabase=new SqliteDatabase(context);

    }

    @NonNull
    @Override
    public AdapterSubscriber.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_subscriber, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubscriber.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        uuid=sqliteDatabase.getuuidS(arrayList.get(position).getSubscriber_id());
        holder.txt_student_name.setText(arrayList.get(position).getSubscriber_name() + "["+uuid+"]");
        holder.txt_student_location.setText(arrayList.get(position).getAddress());
        holder.txt_date_of_birth.setText(arrayList.get(position).getDate_of_birth());
        holder.txt_mobile_no.setText(arrayList.get(position).getMobile_number());
        String category_name="";
        category_name=sqliteDatabase.getCategoryName(arrayList.get(position).getCategory_id());
        holder.txt_category.setText(category_name);



//        holder.txt_category.setText(arrayList.get(position).getCategory_id());

        if (arrayList.get(position).getSubscriber_image() != null && arrayList.get(position).getSubscriber_image().length() > 200) {
            byte[] decodedString = Base64.decode(arrayList.get(position).getSubscriber_image(), Base64.NO_WRAP);
            InputStream inputStream = new ByteArrayInputStream(decodedString);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.img_student.setImageBitmap(bitmap);
        } else {
            try {
                Picasso.get()
                        .load(IMAGE_BASE_URL+arrayList.get(position).getSubscriber_image())
                    .placeholder(R.drawable.camera)
                        .into(holder.img_student);
            }catch (Exception e){
                Log.d("Exception",""+e);
            }

        }

//        String village_name="";
//        village_name=sqliteDatabase.getVillageName(arrayList.get(position).getVillage_id());
//        String state_name="";
//        state_name=sqliteDatabase.getStateName(arrayList.get(position).getState_id());
//        String district_name="";
//        district_name=sqliteDatabase.getDistrictName(arrayList.get(position).getDistrict_id());
//        String block_name="";
//        block_name=sqliteDatabase.getBlockName(arrayList.get(position).getBlock_id());

//        holder.txt_student_location.setText(arrayList.get(position).getAddress()+","+ arrayList.get(position).getSubscriber_name());
//        holder.txt_student_location.setText(state_name+"("+district_name+","+block_name+","+ village_name+")");
//        holder.txt_address.setText(arrayList.get(position).getAddress());




    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_student_name,txt_student_location,txt_address,txt_date_of_birth,txt_mobile_no,txt_category;
        ImageView img_student;

        public ViewHolder(View view) {
            super(view);
            txt_date_of_birth= itemView.findViewById(R.id.txt_date_of_birth);
            txt_mobile_no= itemView.findViewById(R.id.txt_mobile_no);
            txt_category= itemView.findViewById(R.id.txt_category);

            txt_student_name= itemView.findViewById(R.id.txt_student_name);
            txt_student_location= itemView.findViewById(R.id.txt_student_location);
            img_student= itemView.findViewById(R.id.img_student);



        }
    }
}
