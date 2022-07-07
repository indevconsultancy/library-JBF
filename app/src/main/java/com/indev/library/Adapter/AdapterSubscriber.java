package com.indev.library.Adapter;

import static com.indev.library.RestAPI.ClientAPI.IMAGE_BASE_URL;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.BookListActivity;
import com.indev.library.DeleteBookActivity;
import com.indev.library.DeleteSubscriberActivity;
import com.indev.library.Model.SubscriberPojo;
import com.indev.library.R;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.indev.library.utils.CommonClass;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterSubscriber extends RecyclerView.Adapter<AdapterSubscriber.ViewHolder>
{

    Context context;
    ArrayList<SubscriberPojo> arrayList;
    SqliteDatabase sqliteDatabase;
    SharedPrefHelper sharedPrefHelper;
    String uuid="";
    SubscriberPojo subscriberPojo;
    ProgressDialog dialog;

    public AdapterSubscriber(Context context, ArrayList<SubscriberPojo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        subscriberPojo=new SubscriberPojo();
        sqliteDatabase=new SqliteDatabase(context);
        sharedPrefHelper=new SharedPrefHelper(context);

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
        String  local_idd=arrayList.get(position).getLocal_id();
        sharedPrefHelper.setString("local_id",local_idd);

        String category_name="";
        category_name=sqliteDatabase.getCategoryName(arrayList.get(position).getCategory_id());
        holder.txt_category.setText(category_name);
      holder.btn_delete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
//              Intent intent=new Intent(context, DeleteSubscriberActivity.class);
//              intent.putExtra("subscriber_id",arrayList.get(position).getSubscriber_id());
//              context.startActivity(intent);
              onBackPressed(arrayList.get(position).getSubscriber_id());
          }
      });


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

    public void onBackPressed(String subscriber_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.alert);
        builder.setTitle("Alert!");
        builder.setMessage(R.string.are_you_sure_to_want_to_delete_subscriber);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
//                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
//                startActivity(intent);
                   sqliteDatabase.updateeSubscriberDelete(subscriberPojo,subscriber_id);
                   if (CommonClass.isInternetOn(context)) {
                       DeleteSubscriber(subscriber_id);
                   }
                   else{
                       Toast.makeText(context,"Data Delete Succesfully",Toast.LENGTH_LONG).show();
                   }

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
    public void DeleteSubscriber(String subscriber_id){

        subscriberPojo.setSubscriber_id(subscriber_id);
        subscriberPojo.setStatus("0");
        Gson gson = new Gson();
        String data = gson.toJson(subscriberPojo);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        DeleteSubscriber(body, String.valueOf(subscriber_id));
        Log.e("resource", "registration: " + data);
    }

    private void DeleteSubscriber(RequestBody body, String subscriber_id) {
        dialog = ProgressDialog.show(context, "", "Please wait...", true);
        ClientAPI.getClient().create(Library_API.class).DeleteSubscriber(body).enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("TAG", "onResponse: " + jsonObject.toString() );
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    if(status.equals("1"))
                    {
                        sqliteDatabase.DeleteSubscriber("subscriber", "subscriber_id='" + subscriber_id + "'");
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(context, "Book Not Delete", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Book Registration", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });

    }
    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_student_name,txt_student_location,txt_address,txt_date_of_birth,txt_mobile_no,txt_category,btn_delete;
        ImageView img_student;

        public ViewHolder(View view) {
            super(view);
            txt_date_of_birth= itemView.findViewById(R.id.txt_date_of_birth);
            txt_mobile_no= itemView.findViewById(R.id.txt_mobile_no);
            txt_category= itemView.findViewById(R.id.txt_category);
            btn_delete= itemView.findViewById(R.id.btn_delete);
            txt_student_name= itemView.findViewById(R.id.txt_student_name);
            txt_student_location= itemView.findViewById(R.id.txt_student_location);
            img_student= itemView.findViewById(R.id.img_student);



        }
    }
}
