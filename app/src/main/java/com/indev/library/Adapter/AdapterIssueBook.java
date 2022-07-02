package com.indev.library.Adapter;

import static com.indev.library.RestAPI.ClientAPI.IMAGE_BASE_URL1;

import android.annotation.SuppressLint;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.indev.library.AddSubscribeActivity;
import com.indev.library.BookReceiverListActivity;
import com.indev.library.IssueButtonActivity;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.Model.BookIssuePojo;
import com.indev.library.R;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AdapterIssueBook extends RecyclerView.Adapter<AdapterIssueBook.ViewHolder>

{
    Context context;
    ArrayList<BookIssuePojo> arrayList;

    public static android.app.Dialog submit_alert;
    SharedPrefHelper sharedPrefHelper;
    SqliteDatabase sqliteDatabase;
    Click_Listener click_listener;
    BookIssuePojo bookIssuePojo;
    int mYear,mMonth,mDay,year,month,day;
    DatePickerDialog datePickerDialog;
    String currentDateTimeString="";
    String book_name="";
    String uuid="";
    String s_uuid="";
    String resource_image="";
    public AdapterIssueBook(Context context, ArrayList<BookIssuePojo> arrayList, Click_Listener click_listener) {
        this.context = context;
        this.arrayList = arrayList;

        this.click_listener = click_listener;
        sqliteDatabase=new SqliteDatabase(context);
        sharedPrefHelper=new SharedPrefHelper(context);
        bookIssuePojo=new BookIssuePojo();
    }

    @NonNull
    @Override
    public AdapterIssueBook.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_book_receiver, parent, false);
        return new AdapterIssueBook.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIssueBook.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String returnStatus= sqliteDatabase.getCloumnName("issue_recieve_type","transaction_book","where local_id='" +arrayList.get(position).getLocal_id()+"'");


        String check= sharedPrefHelper.getString("list_type","");
        if (check.equalsIgnoreCase("issue")){
            if(!returnStatus.equals("")) {
                if (returnStatus.equals("0")) {
                 holder.llMain.setVisibility(View.GONE);
//                    arrayList.remove(0);
                    }else{
                    holder.llMain.setVisibility(View.VISIBLE);
                }
                }else{
                holder.llMain.setVisibility(View.VISIBLE);

            }
                    holder.btn_return.setVisibility(View.GONE);
                    holder.return_ok.setVisibility(View.GONE);
                } else {

                    if (!returnStatus.equals("")) {
                        if (returnStatus.equals("0")) {
                            holder.btn_return.setVisibility(View.GONE);
                            holder.return_ok.setVisibility(View.VISIBLE);
                        } else {
                            holder.btn_return.setVisibility(View.VISIBLE);
                            holder.return_ok.setVisibility(View.GONE);
                        }

                    } else {
                        holder.btn_return.setVisibility(View.VISIBLE);
                        holder.return_ok.setVisibility(View.GONE);
                    }
                }

        sharedPrefHelper.getString("returnStatus","");

//        holder.txt_book_name.setText(arrayList.get(position).getResource_id());
//        holder.txt_issued_by.setText(arrayList.get(position).getSubscriber_id());

        uuid=sqliteDatabase.getuuid(arrayList.get(position).getResource_id());
        s_uuid=sqliteDatabase.getSuuid(arrayList.get(position).getSubscriber_id());

        resource_image=sqliteDatabase.getresourseImage(arrayList.get(position).getResource_id());

//        holder.txt_book_name.setText(uuid);

        book_name=sqliteDatabase.getbookName(arrayList.get(position).getResource_id());
        holder.txt_book_name.setText(book_name + "["+uuid+"]");
        String subscriber_name="";

        subscriber_name=sqliteDatabase.getSubscriberName(arrayList.get(position).getSubscriber_id());
        holder.txt_issued_by.setText(subscriber_name + "["+s_uuid+"]");
        String issuedate="";
        issuedate=arrayList.get(position).getIssue_date();

         holder.txt_date.setText(issuedate);

        if (resource_image!= null && resource_image.length() > 200) {
            byte[] decodedString = Base64.decode(resource_image, Base64.NO_WRAP);
            InputStream inputStream = new ByteArrayInputStream(decodedString);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.img_book.setImageBitmap(bitmap);
        } else {
            try {
                Picasso.get()
                        .load(IMAGE_BASE_URL1+resource_image)
                        .placeholder(R.drawable.cover2)
                        .into(holder.img_book);
            }catch (Exception e){
                Log.d("Exception",""+e);
            }

        }
////        holder.lmpdate.setText(arrayList.get(position).getLmp_date());
//        holder.tv_edddate.setText(arrayList.get(position).getEdd_date());
//        holder.btn_issue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, IssueButtonActivity.class);
//                intent.putExtra("screen_type", "edit_profile");
//
//                intent.putExtra("local_id",arrayList.get(position).getLocal_id());
//                context.startActivity(intent);
//            }
//        });

        String finalSubscriber_name = subscriber_name;

        String finalIssuedate = issuedate;
        String date= sharedPrefHelper.getString("list_type","");

        //Date Picker
        holder.btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.btn_return.setError(null);
                holder.btn_return.clearFocus();
                mYear=year;
                mMonth=month;
                mDay=day;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR); // current year
                mMonth = c.get(Calendar.MONTH); // current month
                mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(context,

                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {



                                currentDateTimeString = "" +  year + "-" + (monthOfYear + 1) + "-" +dayOfMonth;
                                String book_count="";
                                book_count=sqliteDatabase.getBookCount(book_name);
                                if (book_count.equals("")){
                                    book_count="0";
                                }
                                int qty = Integer.parseInt(book_count)+1;
                                sqliteDatabase.updateBookQty("resource", "resource_id='" + arrayList.get(position).getResource_id() + "'",qty,"available_count" );
                                sqliteDatabase.updateBookQty("resource", "resource_id='" + arrayList.get(position).getResource_id() + "'",0,"resource_status" );
                                sqliteDatabase.updateReturn("transaction_book", " local_id='" + arrayList.get(position).getLocal_id()+"'","recieved_date",currentDateTimeString,"issue_recieve_type","0");
                click_listener.itemClick(arrayList.get(position).getTransaction_id(),currentDateTimeString,0,position);
                showSubmitDialog(holder, context, ""+ finalSubscriber_name + " "+"returns the book on"+" "+ currentDateTimeString+" "+"which was issued on "+" "+finalIssuedate +".");

                            }
                        }, mYear, mMonth, mDay);
                c.add(Calendar.DAY_OF_MONTH, -7);
                Date result = c.getTime();
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setMinDate(result.getTime());
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff173e6d"));
            }
        });

//
//        holder.btn_return.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
////                sqliteDatabase.updateReturn("transaction_book", " local_id='" + arrayList.get(position).getLocal_id()+"'","recieved_date",currentDateTimeString,"issue_recieve_type","0");
////                click_listener.itemClick(arrayList.get(position).getTransaction_id(),finalIssuedate,0,position);
//
////
////                showSubmitDialog(holder, context, ""+ finalSubscriber_name + " "+"returns the book on"+" "+ currentDateTimeString+" "+"which was issued on "+" "+finalIssuedate +".");
//
//            }
//        });


//
//        String check1= sharedPrefHelper.getString("list_type","");
//        if (check1.equalsIgnoreCase("issue")){
//            holder.view1.setVisibility(View.GONE);
//        }else {
//            holder.view1.setVisibility(View.VISIBLE);
//        }

    }


    public static void showSubmitDialog(ViewHolder holder, Context context, String message) {
        submit_alert = new android.app.Dialog(context);

        submit_alert.setContentView(R.layout.submitdialog);
        submit_alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = submit_alert.getWindow().getAttributes();
        params.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;

        android.widget.TextView tvDescription = (TextView) submit_alert.findViewById(R.id.tv_description);
        Button btnOk = (Button) submit_alert.findViewById(R.id.btnOk);

        tvDescription.setText(message);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO DO
//
//                  String check= String.valueOf(btnOk);

                holder.btn_return.setVisibility(View.GONE);
                holder.return_ok.setVisibility(View.VISIBLE);

                submit_alert.dismiss();

            }
        });

        submit_alert.show();
        submit_alert.setCanceledOnTouchOutside(false);
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_book_name,txt_issued_by,txt_date,btn_return,btnOk,view1;
        CardView ll_card_vieww;
        LinearLayout llMain;
        ImageView img_book;
        TextView return_ok;

        public ViewHolder(View view) {
            super(view);
            img_book= itemView.findViewById(R.id.img_book);
            return_ok= itemView.findViewById(R.id.return_ok);
            txt_book_name= itemView.findViewById(R.id.txt_book_name);
            txt_issued_by= itemView.findViewById(R.id.txt_issued_by);
            txt_date= itemView.findViewById(R.id.txt_date);
            btn_return= itemView.findViewById(R.id.btn_return);
            llMain= itemView.findViewById(R.id.llMain);


        }
    }

}
