package com.indev.library;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.RestAPI.ClientAPI;
import com.indev.library.RestAPI.Library_API;
import com.indev.library.SqliteHelper.SharedPrefHelper;
import com.indev.library.SqliteHelper.SqliteDatabase;
import com.indev.library.utils.CommonClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookNameActivity extends AppCompatActivity {
//    String [] category ={"Select Book Category","Story","Novel","Comics","Classics","Fantasy","Detective and Mystery","Horror","Other"};

    SqliteDatabase sqliteDatabase;
    EditText et_title_of_the_book,et_authour_name,et_donor_name,et_remark;
    ImageView imageView_profile;
    LinearLayout ll_donorr,ll_et_donor;
    AutoCompleteTextView autoCompleteTextView;
    Spinner sp_category,sp_library_id,sp_language_id,sp_sources;
    Button alldataSubmit;
    AddBookPojo addBookPojo;
    ProgressDialog dialog;
    SharedPrefHelper sharedPrefHelper;
    private static final int CAMERA_REQUEST=1888;
    String base64;
    HashMap<String, Integer> bookCategoryNameHM, libraryNameHM,resourcesNameHM,languageNameHM;
    ArrayList<String> bookCategoryArrayList,libraryArrayList,resourcesArrayList,languageArrayList;
    private String st_book_category = "";
    int category_id = 0,library_id= 0,language_id=0,resource_id=0;
    int count =0;
    String[] book = new String[0];
    ArrayList<AddBookPojo> bookArrayList;
    ArrayList<AddBookPojo> bookArrayListSelect;
    int donor = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_name);
        getSupportActionBar().setTitle("Add Book Name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intializeAll();
        sqliteDatabase = new SqliteDatabase(getApplicationContext());
        getBookCategorySpinner();
        getLibrarySpinner();
        sp_library_id.setSelection(1);
        getLanguageSpinner();
        getSourceBookSpinner();
//        ArrayAdapter adapter=new ArrayAdapter(AddBookNameActivity.this, R.layout.spinner_lists,category);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_category.setAdapter(adapter);
        bookArrayList=sqliteDatabase.getAllBooKs();
        book=new String[bookArrayList.size()];
        for (int i=0;i<bookArrayList.size();i++){
            book[i]=bookArrayList.get(i).getResource_name();
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,book);
          autoCompleteTextView.setAdapter(adapter);

          autoCompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
              @Override
              public void onDismiss() {
                  bookArrayListSelect=sqliteDatabase.getAllBooKselective(autoCompleteTextView.getText().toString());
                  et_authour_name.setText(bookArrayListSelect.get(0).getAuthor_name());
                  sp_category.setSelection(Integer.parseInt(bookArrayListSelect.get(0).getCategory_id()));
                  et_remark.setText(bookArrayListSelect.get(0).getDescription());
                  sp_language_id.setSelection(Integer.parseInt(bookArrayListSelect.get(0).getLanguage_id()));
//                  imageView_profile.setImageResource(Integer.parseInt(bookArrayListSelect.get(0).getResource_image()));

              }
          });

        imageView_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt, CAMERA_REQUEST);

            }
        });
        alldataSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    String uuid=sharedPrefHelper.getString("local_id","");

//                    String a="B";
                    addBookPojo = new AddBookPojo();
//                    StringBuilder intialize=new StringBuilder();
//                    for (String s :a.split(" ")){
//                        count++;
//                        if (count<=2) {
//                            intialize.append(s.charAt(0));
//                        }
//                    }
                    String book_uniue_id="B"+ CommonClass.getUUID();
                   sharedPrefHelper.setString("uu_id",book_uniue_id);

                    addBookPojo.setResource_unique_id(book_uniue_id);
                    addBookPojo.setResource_name(autoCompleteTextView.getText().toString().trim());
                    addBookPojo.setResource_image(base64);
                    addBookPojo.setLanguage_id(String.valueOf(language_id));
                    addBookPojo.setSource_id(String.valueOf(resource_id));
                    addBookPojo.setLibrary_id(String.valueOf(library_id));
                    addBookPojo.setLibrarain_id(sharedPrefHelper.getString("librarain_id", ""));
                    addBookPojo.setCategory_id(String.valueOf(category_id));
                    addBookPojo.setAuthor_name(et_authour_name.getText().toString().trim());
                    addBookPojo.setDonor_name(et_donor_name.getText().toString().trim());
//                    addBookPojo.setAvailable_count(et_no_of_pieces.getText().toString().trim());

                    addBookPojo.setDescription(et_remark.getText().toString().trim());


                    long local_id = sqliteDatabase.AddBook(addBookPojo);
                  if (CommonClass.isInternetOn(getApplicationContext())) {
                      Gson gson = new Gson();
                      String data = gson.toJson(addBookPojo);
                      MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                      RequestBody body = RequestBody.create(JSON, data);
                      addBookRegistration(body, String.valueOf(local_id));
                      Log.e("resource", "registration: " + data);
                  }
                  else
                  {
                      Intent intent  = new Intent(AddBookNameActivity.this, BookListActivity.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                      startActivity(intent);
                  }
                }

            }
        });


    }
    private void addBookRegistration(RequestBody body, String lid) {
        dialog = ProgressDialog.show(this, "", "Please wait...", true);
        ClientAPI.getClient().create(Library_API.class).AddBookRegistration(body).enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    Log.e("TAG", "onResponse: " + jsonObject.toString() );
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    String last_book_id = jsonObject.optString("last_book_id");
                    if(status.equals("1"))
                    {
                        sqliteDatabase.update("resource", "local_id='" + lid + "'", last_book_id, "resource_id");
                        Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(AddBookNameActivity.this, BookListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(AddBookNameActivity.this, "Book Not Registered", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AddBookNameActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("Book Registration", "Failure" + t + "," + call);
                dialog.dismiss();
            }
        });

    }

    private void intializeAll()
    {
        sp_sources =findViewById(R.id.sp_sources);
        sp_language_id =findViewById(R.id.sp_language_id);
        autoCompleteTextView=findViewById(R.id.autoCompleteTextView);
        et_title_of_the_book =findViewById(R.id.et_title_of_the_book);
        et_authour_name =findViewById(R.id.et_authour_name);
        ll_donorr=findViewById(R.id.ll_donorr);
        ll_et_donor=findViewById(R.id.ll_et_donor);
        et_donor_name =findViewById(R.id.et_donor_name);
        et_remark =findViewById(R.id.et_remark);
        alldataSubmit=findViewById(R.id.alldataSubmit);
        imageView_profile=findViewById(R.id.imageView_profile);
        sp_library_id=findViewById(R.id.sp_library_id);
        sp_category=findViewById(R.id.sp_category);
        bookCategoryArrayList=new ArrayList<>();
        bookCategoryNameHM=new HashMap<>();
        libraryNameHM=new HashMap<>();
        libraryArrayList=new ArrayList<>();
        sharedPrefHelper=new SharedPrefHelper(this);
        resourcesNameHM=new HashMap<>();
        resourcesArrayList=new ArrayList<>();
        languageNameHM=new HashMap<>();
        languageArrayList=new ArrayList<>();

    }
    //All Spinner
    private void getBookCategorySpinner() {

        bookCategoryArrayList.clear();
        bookCategoryNameHM = sqliteDatabase.getAllBooKCategory();
        for (int i = 0; i < bookCategoryNameHM.size(); i++) {
            bookCategoryArrayList.add(bookCategoryNameHM.keySet().toArray()[i].toString().trim());
        }
        bookCategoryArrayList.add(0, "Select Book Category");

        ArrayAdapter state_adapter = new ArrayAdapter(this,R.layout.spinner_lists, bookCategoryArrayList);
        state_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_category.setAdapter(state_adapter);
        category_id=0;
//        if (screen_type.equals("edit_profile")) {
//            st_state = sqliteDatabase.getPSStateSp(editpregnantWomenRegisterTable.getState_id());
//            int pos = state_adapter.getPosition(st_state);
//            sp_state.setSelection(pos);
//        }


        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sp_category.getSelectedItem().toString().trim().equalsIgnoreCase("Select Book Category")) {
                    if (sp_category.getSelectedItem().toString().trim() != null) {
                        category_id = bookCategoryNameHM.get(sp_category.getSelectedItem().toString().trim());
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getLanguageSpinner() {

        languageArrayList.clear();
        languageNameHM = sqliteDatabase.getAllLanguage();
        for (int i = 0; i < languageNameHM.size(); i++) {
            languageArrayList.add(languageNameHM.keySet().toArray()[i].toString().trim());
        }
        languageArrayList.add(0, "Select Language Name");

        ArrayAdapter language_adapter = new ArrayAdapter(this,R.layout.spinner_lists, languageArrayList);
        language_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_language_id.setAdapter(language_adapter);
        language_id=0;
//        if (screen_type.equals("edit_profile")) {
//            st_state = sqliteDatabase.getPSStateSp(editpregnantWomenRegisterTable.getState_id());
//            int pos = state_adapter.getPosition(st_state);
//            sp_state.setSelection(pos);
//        }


        sp_language_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sp_language_id.getSelectedItem().toString().trim().equalsIgnoreCase("Select Language Name")) {
                    if (sp_language_id.getSelectedItem().toString().trim() != null) {
                        language_id = languageNameHM.get(sp_language_id.getSelectedItem().toString().trim());
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getSourceBookSpinner() {

        resourcesArrayList.clear();
        resourcesNameHM = sqliteDatabase.getAllSources();
        for (int i = 0; i < resourcesNameHM.size(); i++) {
            resourcesArrayList.add(resourcesNameHM.keySet().toArray()[i].toString().trim());
        }
        resourcesArrayList.add(0, "Select Sources of Book");

        ArrayAdapter language_adapter = new ArrayAdapter(this,R.layout.spinner_lists, resourcesArrayList);
        language_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_sources.setAdapter(language_adapter);
        resource_id=0;
//        if (screen_type.equals("edit_profile")) {
//            st_state = sqliteDatabase.getPSStateSp(editpregnantWomenRegisterTable.getState_id());
//            int pos = state_adapter.getPosition(st_state);
//            sp_state.setSelection(pos);
//        }


        sp_sources.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sp_sources.getSelectedItem().toString().trim().equalsIgnoreCase("Select Sources of Book")) {
                    if (sp_sources.getSelectedItem().toString().trim() != null) {
                        if(sp_sources.getSelectedItem().toString().trim().equalsIgnoreCase("Donor")) {
                            ll_donorr.setVisibility(View.VISIBLE);
                            ll_et_donor.setVisibility(View.VISIBLE);
                            donor = 0;
                        }
                        else{
                            ll_donorr.setVisibility(View.GONE);
                            ll_et_donor.setVisibility(View.GONE);
                            donor=1;
                        }

                        resource_id = resourcesNameHM.get(sp_sources.getSelectedItem().toString().trim());
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getLibrarySpinner() {

        libraryArrayList.clear();
        String user_id=sharedPrefHelper.getString("librarain_id", "");
        libraryNameHM = sqliteDatabase.getAllLibrary(user_id);
        for (int i = 0; i < libraryNameHM.size(); i++) {
            libraryArrayList.add(libraryNameHM.keySet().toArray()[i].toString().trim());
        }
        libraryArrayList.add(0, "Select Library Name");

        ArrayAdapter library_adapter = new ArrayAdapter(this,R.layout.spinner_lists, libraryArrayList);
        library_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_library_id.setAdapter(library_adapter);
             sp_library_id.setEnabled(false);
             sp_library_id.requestFocus();

//        if (screen_type.equals("edit_profile")) {
//            st_state = sqliteDatabase.getPSStateSp(editpregnantWomenRegisterTable.getState_id());
//            int pos = state_adapter.getPosition(st_state);
//            sp_state.setSelection(pos);
//        }


        sp_library_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sp_library_id.getSelectedItem().toString().trim().equalsIgnoreCase("Select Library Name")) {
                    if (sp_library_id.getSelectedItem().toString().trim() != null) {
                        library_id = libraryNameHM.get(sp_library_id.getSelectedItem().toString().trim());
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();
//
            base64 = encodeTobase64(photo);
            imageView_profile.setImageBitmap(photo);
        }
        }

    }
    private String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream byteArrayOS = null;
        try {
            System.gc();
            byteArrayOS = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOS);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOS);
        }
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
    }
    private boolean checkValidation() {
        boolean ret = true;
        if (sp_library_id.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(sp_library_id.getSelectedItem());
        } else {
            TextView errorTextview = (TextView) sp_library_id.getSelectedView();
            errorTextview.setError("Error");
            errorTextview.requestFocus();
            return false;
        }
        if (et_authour_name.getText().toString().trim().equalsIgnoreCase("")) {
            EditText flagEditfield = et_authour_name;
            String msg = getString(R.string.please_enter_title_of_the_book_name);
            et_authour_name.setError(msg);
            et_authour_name.requestFocus();
            return false;
        }
        if (sp_language_id.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(sp_language_id.getSelectedItem());
        } else {
            TextView errorTextview = (TextView) sp_language_id.getSelectedView();
            errorTextview.setError("Error");
            errorTextview.requestFocus();
            return false;
        }
        if (autoCompleteTextView.getText().toString().trim().equalsIgnoreCase("")) {
            EditText flagEditfield = autoCompleteTextView;
            String msg = getString(R.string.please_enter_title_of_the_book_name);
            autoCompleteTextView.setError(msg);
            autoCompleteTextView.requestFocus();
            return false;
        }


        if (sp_sources.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(sp_sources.getSelectedItem());
        } else {
            TextView errorTextview = (TextView) sp_sources.getSelectedView();
            errorTextview.setError("Error");
            errorTextview.requestFocus();
            return false;
        }
        if (donor==0) {
            if (et_donor_name.getText().toString().trim().equalsIgnoreCase("")) {
                EditText flagEditfield = et_donor_name;
                String msg = getString(R.string.please_enter_donor_name);
                et_donor_name.setError(msg);
                et_donor_name.requestFocus();
                return false;
            }
        }
        if (sp_category.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(sp_category.getSelectedItem());
        } else {
            TextView errorTextview = (TextView) sp_category.getSelectedView();
            errorTextview.setError("Error");
            errorTextview.requestFocus();
            return false;
        }

        if (et_remark.getText().toString().trim().equalsIgnoreCase("")) {
            EditText flagEditfield = et_remark;
            String msg = getString(R.string.please_enter_brief_description);
            et_remark.setError(msg);
            et_remark.requestFocus();
            return false;
        }

        return ret;
    }

}