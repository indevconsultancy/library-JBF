package com.indev.library.Adapter;

import static com.indev.library.RestAPI.ClientAPI.IMAGE_BASE_URL;
import static com.indev.library.RestAPI.ClientAPI.IMAGE_BASE_URL1;

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
import com.indev.library.Model.AddBookPojo;
import com.indev.library.Model.SubscriberPojo;
import com.indev.library.R;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AdapterAddBook extends RecyclerView.Adapter<AdapterAddBook.ViewHolder>
{
    Context context;
    ArrayList<AddBookPojo> arrayList;
    SharedPrefHelper sharedPrefHelper;
    SqliteDatabase sqliteDatabase;
    ArrayList<AddBookPojo> bookarrayList;

    public AdapterAddBook(Context context, ArrayList<AddBookPojo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sqliteDatabase=new SqliteDatabase(context);
        sharedPrefHelper=new SharedPrefHelper(context);
    }

    @NonNull
    @Override
    public AdapterAddBook.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_add_book, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddBook.ViewHolder holder, @SuppressLint("RecyclerView") int position) {



        holder.txt_title_book_name.setText(arrayList.get(position).getResource_name());
        holder.txt_author_name.setText(arrayList.get(position).getAuthor_name());
//        holder.txt_pieces_no.setText(arrayList.get(position).getAvailable_count());
//        holder.txt_total_book.setText(arrayList.get(position).getTotal_quantity());
//        holder.uuid.setText("["+arrayList.get(position).getResource_unique_id()+"]");
        String book_count="";
        book_count=sqliteDatabase.getBookCount(arrayList.get(position).getResource_name());
        holder.txt_total_book.setText(book_count);

        bookarrayList = sqliteDatabase.getBookId(arrayList.get(position).getResource_name());
//        holder.txt_pieces_no.setText(arrayList.get(position).getAvailable_count());
        holder.txt_pieces_no.setText(String.valueOf(bookarrayList.size()));

//        String available_count="";
//        sharedPrefHelper.setString("available_count",available_count);
//        ArrayList<AddBookPojo> arrayList1=sqliteDatabase.Count(arrayList.get(position).getResource_name());
//        holder.txt_pieces_no.setText(String.valueOf(arrayList1.size()));

        if (arrayList.get(position).getResource_image() != null && arrayList.get(position).getResource_image().length() > 200) {
            byte[] decodedString = Base64.decode(arrayList.get(position).getResource_image(), Base64.NO_WRAP);
            InputStream inputStream = new ByteArrayInputStream(decodedString);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.img_book.setImageBitmap(bitmap);
        } else {
            try {
                Picasso.get()
                        .load(IMAGE_BASE_URL1+arrayList.get(position).getResource_image())
                        .placeholder(R.drawable.cover2)
                        .into(holder.img_book);
            }catch (Exception e){
                Log.d("Exception",""+e);
            }

        }
////        holder.lmpdate.setText(arrayList.get(position).getLmp_date());
//        holder.tv_edddate.setText(arrayList.get(position).getEdd_date());

        if (holder.txt_pieces_no.getText().toString().equals("0")){
            holder.btn_issue.setVisibility(View.GONE);
                holder.out_of_stock.setVisibility(View.VISIBLE);
        }else{
            holder.btn_issue.setVisibility(View.VISIBLE);
                holder.out_of_stock.setVisibility(View.GONE);
        }


        String returnStatuss= sqliteDatabase.getCloumnNameissue("available_count","resource","where local_id='" +arrayList.get(position).getLocal_id()+"'");
//        if(!returnStatuss.equals("")) {
//            if (returnStatuss.equals("0")) {
//                holder.btn_issue.setVisibility(View.GONE);
//                holder.out_of_stock.setVisibility(View.VISIBLE);
//            }else{
//                holder.btn_issue.setVisibility(View.VISIBLE);
//                holder.out_of_stock.setVisibility(View.GONE);
//            }
//        }else{
//            holder.btn_issue.setVisibility(View.VISIBLE);
//            holder.out_of_stock.setVisibility(View.GONE);
//        }
      holder.btn_issue.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(context, IssueButtonActivity.class);

              sharedPrefHelper.setString("spinner_type","spinner_issue");

              intent.putExtra("resourse_name",arrayList.get(position).getResource_name());
              intent.putExtra("local_id",arrayList.get(position).getLocal_id());
              intent.putExtra("book_qty",arrayList.get(position).getAvailable_count());
              context.startActivity(intent);
          }
      });

    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_title_book_name,txt_author_name,txt_pieces_no,btn_issue,out_of_stock,txt_total_book,uuid;
        ImageView img_book;

        public ViewHolder(View view) {
            super(view);
            img_book= itemView.findViewById(R.id.img_book);

            txt_total_book= itemView.findViewById(R.id.txt_total_book);
            out_of_stock= itemView.findViewById(R.id.out_of_stock);
            txt_title_book_name= itemView.findViewById(R.id.txt_title_book_name);
            txt_author_name= itemView.findViewById(R.id.txt_author_name);
            txt_pieces_no= itemView.findViewById(R.id.txt_pieces_no);
            btn_issue= itemView.findViewById(R.id.btn_issue);

        }
    }
}
