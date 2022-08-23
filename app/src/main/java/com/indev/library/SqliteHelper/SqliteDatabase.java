package com.indev.library.SqliteHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.indev.library.Model.ActivityListPojo;
import com.indev.library.Model.ActivityReportingPojo;
import com.indev.library.Model.AddBookPojo;
import com.indev.library.Model.BookCategoryPojo;
import com.indev.library.Model.BookIssuePojo;
import com.indev.library.Model.Language_id_Pojo;
import com.indev.library.Model.LibraryPojo;
import com.indev.library.Model.QualificationPojo;
import com.indev.library.Model.ReportingImagePojo;
import com.indev.library.Model.SourcesPojo;
import com.indev.library.Model.SubscriberPojo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SqliteDatabase extends SQLiteOpenHelper
{
    static final String DATABASE_NAME = "library.db";
    static final int DATABASE_VERSION = 1;
    String DB_PATH_SUFFIX = "/databases/";
    int Version;
    Context ctx;

    public SqliteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
//        sharedPrefHelper = new SharedPrefHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase DB)
    {
        DB.execSQL(ReportingImagePojo.CREATE_TABLE);
        DB.execSQL(ActivityListPojo.CREATE_TABLE);
        DB.execSQL(SubscriberPojo.CREATE_TABLE);
        DB.execSQL(AddBookPojo.CREATE_TABLE);
        DB.execSQL(BookIssuePojo.CREATE_TABLE);
        DB.execSQL(Language_id_Pojo.CREATE_TABLE);
        DB.execSQL(SourcesPojo.CREATE_TABLE);
        DB.execSQL(QualificationPojo.CREATE_TABLE);
        DB.execSQL(BookCategoryPojo.CREATE_TABLE);
        DB.execSQL(LibraryPojo.CREATE_TABLE);
        DB.execSQL(ActivityReportingPojo.CREATE_TABLE);
//        DB.execSQL(VillagePojo.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion)
    {

    }

    public SQLiteDatabase openDataBase()throws SQLException
    {
        Log.e("version", "outside " + Version );
        File DBFile = ctx.getDatabasePath(DATABASE_NAME);
        return SQLiteDatabase.openDatabase(DBFile.getPath(),null,SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);

    }
    public void dropTable(String tableName)
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        try
        {
            DB.execSQL("DELETE FROM'" + tableName + "'");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            DB.close();
        }
    }
    public void saveMasterTable(ContentValues contentValues, String tableName)
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        long idsds =DB.insert(tableName, null,contentValues);
        Log.d("LOG", idsds + "id");
        DB.close();
    }
    public long Reporting_Image(ReportingImagePojo reportingImagePojo)
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        long ids =0;
        try
        {
            if(DB !=null && !DB.isReadOnly())
            {
                ContentValues values =new ContentValues();

                values.put("uuid",reportingImagePojo.getUuid());
                values.put("image",reportingImagePojo.getImage());

                ids =DB.insert("activity_reporting_image",null, values);
                DB.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            DB.close();
        }
        return ids;
    }
    public long Reporting(ActivityReportingPojo householdMasterModel)
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        long ids =0;
        try
        {
            if(DB !=null && !DB.isReadOnly())
            {
                ContentValues values =new ContentValues();

                values.put("id",householdMasterModel.getId());
                values.put("activity_id",householdMasterModel.getActivity_id());
                values.put("librarain_id",householdMasterModel.getLibrarain_id());
                values.put("activity_image",householdMasterModel.getActivity_image2());
                values.put("activity_reporting_id",householdMasterModel.getActivity_reporting_id());
                values.put("uuid",householdMasterModel.getUuid());

                values.put("status", "0");
                values.put("flag", "0");

                ids =DB.insert("activity_reporting",null, values);
                DB.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            DB.close();
        }
        return ids;
    }
    @SuppressLint("Range")
    public ArrayList<ActivityReportingPojo> getReportingData()
    {
        ArrayList<ActivityReportingPojo> activityReportingPojoArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if (db != null && db.isOpen() && !db.isReadOnly())
            {
                String query = "select * from activity_reporting order by local_id desc";

                @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {

                        ActivityReportingPojo list = new ActivityReportingPojo();
                        list.setId(cursor.getString(cursor.getColumnIndex("id")));
                        list.setActivity_id(cursor.getString(cursor.getColumnIndex("activity_id")));
                        list.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        list.setActivity_image2(cursor.getString(cursor.getColumnIndex("activity_image")));
                        list.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                        activityReportingPojoArrayList.add(list);
                        cursor.moveToNext();
                    }
                }
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return activityReportingPojoArrayList;

    }
    @SuppressLint("Range")
    public ArrayList<ActivityReportingPojo> getReportingDataArray(String local_id)
    {
        ArrayList<ActivityReportingPojo> activityReportingPojoArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if (db != null && db.isOpen() && !db.isReadOnly())
            {
                String query = "select * from activity_reporting where local_id='"+local_id+"'";

                @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {

                        ActivityReportingPojo list = new ActivityReportingPojo();
                        list.setId(cursor.getString(cursor.getColumnIndex("id")));
                        list.setActivity_id(cursor.getString(cursor.getColumnIndex("activity_id")));
                        list.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
//                        list.setActivity_image2(cursor.getString(cursor.getColumnIndex("activity_image")));
                        list.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                        activityReportingPojoArrayList.add(list);
                        cursor.moveToNext();
                    }
                }
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return activityReportingPojoArrayList;

    }
    @SuppressLint("Range")
    public ArrayList<ReportingImagePojo> getReportingImage(String uuid)
    {
        ArrayList<ReportingImagePojo> reportingImagePojoArrayList = new ArrayList<ReportingImagePojo>();
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if (db != null && db.isOpen() && !db.isReadOnly())
            {
                String query = "select * from activity_reporting_image where uuid='"+uuid+"'";

                @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {

                        ReportingImagePojo list = new ReportingImagePojo();
                        list.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        reportingImagePojoArrayList.add(list);
                        cursor.moveToNext();
                    }
                }
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return reportingImagePojoArrayList;

    }
    public long Subscriber(SubscriberPojo householdMasterModel)
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        long ids =0;
        try
        {
            if(DB !=null && !DB.isReadOnly())
            {
                ContentValues values =new ContentValues();
                values.put("local_id",householdMasterModel.getLocal_id());
                values.put("subscriber_id",householdMasterModel.getSubscriber_id());
                values.put("subscriber_name",householdMasterModel.getSubscriber_name());
                values.put("subscriber_age",householdMasterModel.getSubscriber_age());

                values.put("subscriber_unique_id",householdMasterModel.getSubscriber_unique_id());
                values.put("gender",householdMasterModel.getGender());
                values.put("librarain_id",householdMasterModel.getLibrarain_id());
                values.put("latitu",householdMasterModel.getLatitu());
                values.put("longitu",householdMasterModel.getLongitu());
                values.put("library_id",householdMasterModel.getLibrary_id());
                values.put("email",householdMasterModel.getEmail());
                values.put("category_id",householdMasterModel.getCategory_id());
                values.put("subscriber_image",householdMasterModel.getSubscriber_image());
                values.put("date_of_birth",householdMasterModel.getDate_of_birth());
                values.put("address",householdMasterModel.getAddress());
                values.put("mobile_number",householdMasterModel.getMobile_number());
                 values.put("status", "0");
                values.put("flag", "0");

                ids =DB.insert("subscriber",null, values);
                DB.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            DB.close();
        }
        return ids;
    }
    @SuppressLint("Range")
    public ArrayList<SubscriberPojo> getRegistrationData()
    {
        ArrayList<SubscriberPojo> subscriberPojoArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if (db != null && db.isOpen() && !db.isReadOnly())
            {
                String query = "select * from subscriber where status is not 0 order by local_id desc";

                @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {

                        SubscriberPojo list = new SubscriberPojo();
                        list.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));
                        list.setSubscriber_id(cursor.getString(cursor.getColumnIndex("subscriber_id")));
                        list.setSubscriber_age(cursor.getString(cursor.getColumnIndex("subscriber_age")));

                        list.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        list.setLatitu(cursor.getString(cursor.getColumnIndex("latitu")));
                        list.setLongitu(cursor.getString(cursor.getColumnIndex("longitu")));
                        list.setSubscriber_unique_id(cursor.getString(cursor.getColumnIndex("subscriber_unique_id")));
                        list.setSubscriber_name(cursor.getString(cursor.getColumnIndex("subscriber_name")));
                        list.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                        list.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                        list.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                        list.setSubscriber_image(cursor.getString(cursor.getColumnIndex("subscriber_image")));
                        list.setDate_of_birth(cursor.getString(cursor.getColumnIndex("date_of_birth")));
                        list.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                        list.setMobile_number(cursor.getString(cursor.getColumnIndex("mobile_number")));

                        subscriberPojoArrayList.add(list);
                        cursor.moveToNext();
                    }
                }
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return subscriberPojoArrayList;

    }
    @SuppressLint("Range")
    public ArrayList<SubscriberPojo> getSt_SubscriberSyn() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<SubscriberPojo> arrayList = new ArrayList<SubscriberPojo>();
        SubscriberPojo subscriberPojo;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "select * from subscriber WHERE flag=0";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        subscriberPojo = new SubscriberPojo();
//
                        subscriberPojo.setSubscriber_id(cursor.getString(cursor.getColumnIndex("subscriber_id")));
                        subscriberPojo.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        subscriberPojo.setLatitu(cursor.getString(cursor.getColumnIndex("latitu")));
                        subscriberPojo.setLongitu(cursor.getString(cursor.getColumnIndex("longitu")));

                        subscriberPojo.setSubscriber_unique_id(cursor.getString(cursor.getColumnIndex("subscriber_unique_id")));
                        subscriberPojo.setSubscriber_name(cursor.getString(cursor.getColumnIndex("subscriber_name")));
                        subscriberPojo.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        subscriberPojo.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                        subscriberPojo.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                        subscriberPojo.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                        subscriberPojo.setSubscriber_image(cursor.getString(cursor.getColumnIndex("subscriber_image")));
                        subscriberPojo.setDate_of_birth(cursor.getString(cursor.getColumnIndex("date_of_birth")));
                        subscriberPojo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                        subscriberPojo.setMobile_number(cursor.getString(cursor.getColumnIndex("mobile_number")));
                        cursor.moveToNext();
                        arrayList.add(subscriberPojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public HashMap<Integer, String> getAllSubscriber( String librarian_id) {
        HashMap<Integer, String> book = new HashMap<>();
        SubscriberPojo subscriberPojo;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
                String query = "select subscriber_id,subscriber_name from subscriber WHERE librarain_id='" + librarian_id+"'";
                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        subscriberPojo = new SubscriberPojo();
                        subscriberPojo.setSubscriber_name(cursor.getString(cursor.getColumnIndex("subscriber_name")));
                        subscriberPojo.setSubscriber_id(cursor.getString(cursor.getColumnIndex("subscriber_id")));

                        cursor.moveToNext();
                        //ArrayA
                        book.put(Integer.valueOf(subscriberPojo.getSubscriber_id()), subscriberPojo.getSubscriber_name());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        }
        return book;
    }

    @SuppressLint("Range")
    public String getCategoryName(String category_id) {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select qualification_category_name from qualification where local_id ='" + category_id + "' ", null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("qualification_category_name"));
        return sum;
    }
    @SuppressLint("Range")
    public String getActivityName(String category_id) {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select activity_name from activity_list where local_id ='" + category_id + "' ", null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("activity_name"));
        return sum;
    }
    @SuppressLint("Range")
    public String getbookName(String resource_id) {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select resource_name from resource where resource_id ='" + resource_id + "' ", null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("resource_name"));
        return sum;
    }
    @SuppressLint("Range")
    public String getuuid(String resource_id) {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select resource_unique_id from resource where resource_id ='" + resource_id + "' ", null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("resource_unique_id"));
        return sum;
    }
    @SuppressLint("Range")
    public String getBookCountPieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_book from resource",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_book"));
        return sum;
    }
    @SuppressLint("Range")
    public String getQualificationPieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_qualification from subscriber",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_qualification"));
        return sum;
    }
    @SuppressLint("Range")
    public String getQualification1PieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_qualification from subscriber where category_id is 1",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_qualification"));
        return sum;
    }
    @SuppressLint("Range")
    public String getQualification2PieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_qualification from subscriber where category_id is 2",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_qualification"));
        return sum;
    }
    @SuppressLint("Range")
    public String getQualification3PieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_qualification from subscriber where category_id is 3",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_qualification"));
        return sum;
    }
    @SuppressLint("Range")
    public String getQualification4PieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_qualification from subscriber where category_id is 4",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_qualification"));
        return sum;
    }
    @SuppressLint("Range")
    public String getQualification5PieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_qualification from subscriber where category_id is 5",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_qualification"));
        return sum;
    }
    @SuppressLint("Range")
    public String getQualification6PieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_qualification from subscriber where category_id is 6",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_qualification"));
        return sum;
    }
    @SuppressLint("Range")
    public String getQualification7PieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_qualification from subscriber where category_id is 7",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_qualification"));
        return sum;
    }
    @SuppressLint("Range")
    public String getQualification8PieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_qualification from subscriber where category_id is 8",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_qualification"));
        return sum;
    }
    @SuppressLint("Range")
    public String getQualification9PieChart() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_qualification from subscriber where category_id is 9",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_qualification"));
        return sum;
    }
    @SuppressLint("Range")
    public String getEnglishCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_language from resource where language_id is 1",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_language"));
        return sum;
    }
    @SuppressLint("Range")
    public String getHindiCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_language from resource where language_id is 2",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_language"));
        return sum;
    }
    @SuppressLint("Range")
    public String getBangaliCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_language from resource where language_id is 3",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_language"));
        return sum;
    }
    @SuppressLint("Range")
    public String getOdiaCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_language from resource where language_id is 4",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_language"));
        return sum;
    }
    @SuppressLint("Range")
    public String getPunjabiCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_language from resource where language_id is 5",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_language"));
        return sum;
    }
    @SuppressLint("Range")
    public String getMarathiCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_language from resource where language_id is 6",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_language"));
        return sum;
    }
    @SuppressLint("Range")
    public String getTamilCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_language from resource where language_id is 7",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_language"));
        return sum;
    }
    @SuppressLint("Range")
    public String getStoryCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_category from resource where category_id is 1",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_category"));
        return sum;
    }

    @SuppressLint("Range")
    public String getNovelCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_category from resource where category_id is 2",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_category"));
        return sum;
    }
    @SuppressLint("Range")
    public String getComicsCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_category from resource where category_id is 3",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_category"));
        return sum;
    }

    @SuppressLint("Range")
    public String getGenderCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_gender from subscriber",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_gender"));
        return sum;
    }
    @SuppressLint("Range")
    public String getGenderMaleCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_gender from subscriber where gender='male'",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_gender"));
        return sum;
    }
    @SuppressLint("Range")
    public String getGenderFemaleCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_gender from subscriber where gender='female'",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_gender"));
        return sum;
    }
    @SuppressLint("Range")
    public String getAge5_20Count() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_age from subscriber where subscriber_age in (5,6,7,8,9,10)",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_age"));
        return sum;
    }
    @SuppressLint("Range")
    public String getAge20_35Count() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_age from subscriber where subscriber_age in (10,11,12,13,14,15)"
                ,null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_age"));
        return sum;
    }
    @SuppressLint("Range")
    public String getAge35_55Count() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_age from subscriber where subscriber_age in (15,16,17,18,19,20)"
                ,null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_age"));
        return sum;
    }
    @SuppressLint("Range")
    public String getAge20_25Count() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_age from subscriber where subscriber_age in (20,21,22,23,24,25)"
                ,null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_age"));
        return sum;
    }
    @SuppressLint("Range")
    public String getIssueBookCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_book from resource where resource_status is 1",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_book"));
        return sum;
    }
    @SuppressLint("Range")
    public String getReceiveBookCount() {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(local_id) as total_book from resource where resource_status is 0",null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("total_book"));
        return sum;
    }
    @SuppressLint("Range")
    public String getSuuid(String subscriber_id) {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select subscriber_unique_id from subscriber where subscriber_id ='" + subscriber_id + "' ", null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("subscriber_unique_id"));
        return sum;
    }
    @SuppressLint("Range")
    public String getuuidS(String subscriber_id) {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select subscriber_unique_id from subscriber where subscriber_id ='" + subscriber_id + "' ", null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("subscriber_unique_id"));
        return sum;
    }
    @SuppressLint("Range")
    public String getresourseImage(String resource_id) {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select resource_image from resource where resource_id ='" + resource_id + "' ", null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("resource_image"));
        return sum;
    }
    @SuppressLint("Range")
    public String getSubscriberName(String subscriber_id) {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select subscriber_name from subscriber where subscriber_id ='" + subscriber_id + "' ", null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("subscriber_name"));
        return sum;
    }
    @SuppressLint("Range")
    public HashMap<String, Integer> getAllBooKCategory() {
        HashMap<String, Integer> state = new HashMap<>();
        BookCategoryPojo bookCategoryPojo;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
                String query = "select category_id,category_name from book_category";
                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        bookCategoryPojo = new BookCategoryPojo();
                        bookCategoryPojo.setCategory_name(cursor.getString(cursor.getColumnIndex("category_name")));
                        bookCategoryPojo.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                        cursor.moveToNext();
                        state.put(bookCategoryPojo.getCategory_name(), Integer.valueOf(bookCategoryPojo.getCategory_id()));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        }
        return state;
    }
    @SuppressLint("Range")
    public HashMap<String, Integer> getAllLanguage() {
        HashMap<String, Integer> state = new HashMap<>();
        Language_id_Pojo language_id_pojo;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
                String query = "select language_id,language_name from language";
                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        language_id_pojo = new Language_id_Pojo();
                        language_id_pojo.setLanguage_name(cursor.getString(cursor.getColumnIndex("language_name")));
                        language_id_pojo.setLanguage_id(cursor.getString(cursor.getColumnIndex("language_id")));
                        cursor.moveToNext();
                        state.put(language_id_pojo.getLanguage_name(), Integer.valueOf(language_id_pojo.getLanguage_id()));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        }
        return state;
    }
    @SuppressLint("Range")
    public HashMap<String, Integer> getAllActivity() {
        HashMap<String, Integer> activity = new HashMap<>();
        ActivityListPojo activityListPojo;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
                String query = "select activity_id,activity_name from activity_list";
                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        activityListPojo = new ActivityListPojo();
                        activityListPojo.setActivity_name(cursor.getString(cursor.getColumnIndex("activity_name")));
                        activityListPojo.setActivity_id(cursor.getString(cursor.getColumnIndex("activity_id")));
                        cursor.moveToNext();
                        activity.put(activityListPojo.getActivity_name(), Integer.valueOf(activityListPojo.getActivity_id()));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        }
        return activity;
    }
    @SuppressLint("Range")
    public HashMap<String, Integer> getAllSources() {
        HashMap<String, Integer> state = new HashMap<>();
        SourcesPojo sourcesPojo;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
                String query = "select source_id,source_name from sources_of_resource";
                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        sourcesPojo = new SourcesPojo();
                        sourcesPojo.setSource_name(cursor.getString(cursor.getColumnIndex("source_name")));
                        sourcesPojo.setSource_id(cursor.getString(cursor.getColumnIndex("source_id")));
                        cursor.moveToNext();
                        state.put(sourcesPojo.getSource_name(), Integer.valueOf(sourcesPojo.getSource_id()));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        }
        return state;
    }
    @SuppressLint("Range")
    public HashMap<String, Integer> getAllCategory() {
        HashMap<String, Integer> state = new HashMap<>();
        QualificationPojo bookCategoryPojo;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
                String query = "select id,qualification_category_name from qualification";
                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        bookCategoryPojo = new QualificationPojo();
                        bookCategoryPojo.setQualification_category_name(cursor.getString(cursor.getColumnIndex("qualification_category_name")));
                        bookCategoryPojo.setId(cursor.getString(cursor.getColumnIndex("id")));
                        cursor.moveToNext();
                        state.put(bookCategoryPojo.getQualification_category_name(), Integer.valueOf(bookCategoryPojo.getId()));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        }
        return state;
    }

    public long updatePSFlag(String table, int local_id, int flag, String whr) {
        long inserted_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                ContentValues values = new ContentValues();
                values.put("flag", flag);

                inserted_id = db.update(table, values, whr + " = " + local_id + "", null);

                db.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return inserted_id;
    }

    @SuppressLint("Range")
    public ArrayList<SubscriberPojo> getSubscriberList(String namee) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SubscriberPojo> arrayList = new ArrayList<>();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "";
                if (namee.equals("")) {

                    query = "select * from subscriber where subscriber_name LIKE '" + namee + "%' OR mobile_number like '" + namee + "%' OR address like '" + namee + "%' OR subscriber_unique_id like '" + namee + "%'";

                } else {

                    query = "select * from subscriber where subscriber_name LIKE '" + namee + "%' OR mobile_number like '" + namee + "%' OR address like '" + namee + "%' OR subscriber_unique_id like '" + namee + "%'";

                }
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        SubscriberPojo bookIssuePojo = new SubscriberPojo();
                        bookIssuePojo.setSubscriber_name(cursor.getString(cursor.getColumnIndex("subscriber_name")));
                        bookIssuePojo.setSubscriber_image(cursor.getString(cursor.getColumnIndex("subscriber_image")));
                        bookIssuePojo.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                        bookIssuePojo.setDate_of_birth(cursor.getString(cursor.getColumnIndex("date_of_birth")));
                        bookIssuePojo.setMobile_number(cursor.getString(cursor.getColumnIndex("mobile_number")));
                        bookIssuePojo.setSubscriber_id(cursor.getString(cursor.getColumnIndex("subscriber_id")));
                        bookIssuePojo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                        bookIssuePojo.setSubscriber_unique_id(cursor.getString(cursor.getColumnIndex("subscriber_unique_id")));

                        cursor.moveToNext();
                        arrayList.add(bookIssuePojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public ArrayList<ActivityReportingPojo> getSearchReportingList(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ActivityReportingPojo> arrayList = new ArrayList<ActivityReportingPojo>();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "";
                if (name.equals("")) {

                    query = "select *,(select activity_name from activity_list a where a.activity_id=b.activity_id) as activityname from activity_reporting b";

                } else {

                    query = "select *,(select activity_name from activity_list a where a.activity_id=b.activity_id) as activityname from activity_reporting b  where  activityname LIKE '" + name +"%'";

                }
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        ActivityReportingPojo activityReportingPojo = new ActivityReportingPojo();
                        activityReportingPojo.setActivity_id(cursor.getString(cursor.getColumnIndex("activity_id")));
                        activityReportingPojo.setActivity_image2(cursor.getString(cursor.getColumnIndex("activity_image")));

                        cursor.moveToNext();
                        arrayList.add(activityReportingPojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return arrayList;
    }


    @SuppressLint("Range")
    public String getCloumnName(String colName, String table, String whr) {
        String column = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select " + colName + " " + " from " + table + " " + whr, null);
            if (cursor.moveToFirst())
                column = cursor.getString(cursor.getColumnIndex(colName)).trim();
        }catch (Exception e){
            e.printStackTrace();
        }
        return column;
    }
    public String getCloumnNameDate_of_birth(String colName, String table, String whr) {
        String column = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select " + colName + " " + " from " + table + " " + whr, null);
            if (cursor.moveToFirst())
                column = cursor.getString(cursor.getColumnIndex(colName)).trim();
        }catch (Exception e){
            e.printStackTrace();
        }
        return column;
    }
    public String getCloumnNameLibraryName(String colName, String table, String whr) {
        String column = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select " + colName + " " + " from " + table + " " + whr, null);
            if (cursor.moveToFirst())
                column = cursor.getString(cursor.getColumnIndex(colName)).trim();
        }catch (Exception e){
            e.printStackTrace();
        }
        return column;
    }
    public String getCloumnNameLibraryId(String colName, String table, String whr) {
        String column = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select " + colName + " " + " from " + table + " " + whr, null);
            if (cursor.moveToFirst())
                column = cursor.getString(cursor.getColumnIndex(colName)).trim();
        }catch (Exception e){
            e.printStackTrace();
        }
        return column;
    }
    public String getCloumnNameissue(String colName, String table, String whr) {
        String column = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select " + colName + " " + " from " + table + " " + whr, null);
            if (cursor.moveToFirst())
                column = cursor.getString(cursor.getColumnIndex(colName)).trim();
        }catch (Exception e){
            e.printStackTrace();
        }
        return column;
    }
    public String getCloumnNameissuegone(String colName, String table, String whr) {
        String column = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select " + colName + " " + " from " + table + " " + whr, null);
            if (cursor.moveToFirst())
                column = cursor.getString(cursor.getColumnIndex(colName)).trim();
        }catch (Exception e){
            e.printStackTrace();
        }
        return column;
    }

    public long AddBook(AddBookPojo householdMasterModel)
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        long ids =0;
        try
        {
            if(DB !=null && !DB.isReadOnly())
            {
                ContentValues values =new ContentValues();

//                values.put("resource_id",householdMasterModel.getResource_id());
                values.put("librarain_id",householdMasterModel.getLibrarain_id());
                values.put("library_id",householdMasterModel.getLibrary_id());
                values.put("resource_status","");

                values.put("language_id",householdMasterModel.getLanguage_id());
                values.put("source_id",householdMasterModel.getSource_id());
                values.put("donor_name",householdMasterModel.getDonor_name());
                values.put("resource_unique_id",householdMasterModel.getResource_unique_id());
                values.put("remark",householdMasterModel.getRemark());
                values.put("resource_name",householdMasterModel.getResource_name());
                values.put("author_name",householdMasterModel.getAuthor_name());
                values.put("category_id",householdMasterModel.getCategory_id());
                values.put("description",householdMasterModel.getDescription());
                values.put("resource_image",householdMasterModel.getResource_image());
//                values.put("user_id",householdMasterModel.getUser_id());
                values.put("status", "0");
                values.put("flag", "0");


                ids =DB.insert("resource",null, values);
                DB.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            DB.close();
        }
        return ids;
    }
    public long updateeDelete( AddBookPojo addBookPojo,String resource_id) {
        long subscriber_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();
                values.put("remark", addBookPojo.getRemark());
                values.put("status", "0");
                subscriber_id = db.update("resource", values, "resource_id" + "=?",new String[]{resource_id});

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return subscriber_id;
    }
    public long updateeSubscriberDelete( SubscriberPojo subscriberPojo,String subscriber_id) {
        long subscriber_idd = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();
                values.put("status", "0");
                subscriber_idd = db.update("subscriber", values, "subscriber_id" + "=?",new String[]{subscriber_id});

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return subscriber_idd;
    }
    @SuppressLint("Range")
    public ArrayList<AddBookPojo> getRegistrationData2()
    {
        ArrayList<AddBookPojo> addBookPojoArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if (db != null && db.isOpen() && !db.isReadOnly())
            {
                String query = "select * from resource group by resource_name order by local_id desc";

                @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {

                        AddBookPojo list = new AddBookPojo();

                        list.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));
                        list.setResource_status(cursor.getString(cursor.getColumnIndex("resource_status")));
                        list.setNo_of_days_book_issued(cursor.getString(cursor.getColumnIndex("no_of_days_book_issued")));
                        list.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
                        list.setResource_unique_id(cursor.getString(cursor.getColumnIndex("resource_unique_id")));
                        list.setLanguage_id(cursor.getString(cursor.getColumnIndex("language_id")));
                        list.setSource_id(cursor.getString(cursor.getColumnIndex("source_id")));
                        list.setDonor_name(cursor.getString(cursor.getColumnIndex("donor_name")));
                        list.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));
                        list.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        list.setLibrary_id(cursor.getString(cursor.getColumnIndex("library_id")));
                        list.setTotal_quantity(cursor.getString(cursor.getColumnIndex("total_quantity")));

                        list.setResource_name(cursor.getString(cursor.getColumnIndex("resource_name")));
                        list.setAuthor_name(cursor.getString(cursor.getColumnIndex("author_name")));
                        list.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                        list.setDescription(cursor.getString(cursor.getColumnIndex("description")));

                        list.setAvailable_count(cursor.getString(cursor.getColumnIndex("available_count")));
                        list.setResource_image(cursor.getString(cursor.getColumnIndex("resource_image")));


                        addBookPojoArrayList.add(list);
                        cursor.moveToNext();
                    }
                }
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return addBookPojoArrayList;
    }
    @SuppressLint("Range")
    public ArrayList<AddBookPojo> getSt_AddBookSyn() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<AddBookPojo> arrayList = new ArrayList<AddBookPojo>();
        AddBookPojo addBookPojo;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "select * from resource WHERE flag=0";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        addBookPojo = new AddBookPojo();
//
                        addBookPojo.setResource_status(cursor.getString(cursor.getColumnIndex("resource_status")));
                        addBookPojo.setResource_unique_id(cursor.getString(cursor.getColumnIndex("resource_unique_id")));
                        addBookPojo.setLanguage_id(cursor.getString(cursor.getColumnIndex("language_id")));
                        addBookPojo.setSource_id(cursor.getString(cursor.getColumnIndex("source_id")));
                        addBookPojo.setDonor_name(cursor.getString(cursor.getColumnIndex("donor_name")));
                        addBookPojo.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));
                        addBookPojo.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        addBookPojo.setLibrary_id(cursor.getString(cursor.getColumnIndex("library_id")));
                        addBookPojo.setTotal_quantity(cursor.getString(cursor.getColumnIndex("total_quantity")));
                        addBookPojo.setResource_name(cursor.getString(cursor.getColumnIndex("resource_name")));
                        addBookPojo.setAuthor_name(cursor.getString(cursor.getColumnIndex("author_name")));
                        addBookPojo.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                        addBookPojo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        addBookPojo.setAvailable_count(cursor.getString(cursor.getColumnIndex("available_count")));
                        addBookPojo.setResource_image(cursor.getString(cursor.getColumnIndex("resource_image")));
                        cursor.moveToNext();
                        arrayList.add(addBookPojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public ArrayList<ActivityReportingPojo> getSt_ActivityReportingSyn() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ActivityReportingPojo> arrayList = new ArrayList<ActivityReportingPojo>();
        ActivityReportingPojo activityReportingPojo;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "select * from activity_reporting WHERE flag=0";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        activityReportingPojo = new ActivityReportingPojo();
                        activityReportingPojo.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));
                        activityReportingPojo.setActivity_id(cursor.getString(cursor.getColumnIndex("activity_id")));
                        activityReportingPojo.setActivity_image2(cursor.getString(cursor.getColumnIndex("activity_image")));

                        cursor.moveToNext();
                        arrayList.add(activityReportingPojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public ArrayList<AddBookPojo> getSt_DeleteAddBookSyn() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<AddBookPojo> arrayList = new ArrayList<AddBookPojo>();
        AddBookPojo addBookPojo;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "select * from resource WHERE status=0";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        addBookPojo = new AddBookPojo();
//
                        addBookPojo.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
                        addBookPojo.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));

                        cursor.moveToNext();
                        arrayList.add(addBookPojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public ArrayList<SubscriberPojo> getSt_DeleteSubscriberSyn() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<SubscriberPojo> arrayList = new ArrayList<SubscriberPojo>();
        SubscriberPojo subscriberPojo;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "select * from subscriber WHERE status=0";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        subscriberPojo = new SubscriberPojo();
//
                        subscriberPojo.setSubscriber_id(cursor.getString(cursor.getColumnIndex("subscriber_id")));

                        cursor.moveToNext();
                        arrayList.add(subscriberPojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public HashMap<String, Integer> getAllLibrary(String librarian_id) {
        HashMap<String, Integer> library = new HashMap<>();
        LibraryPojo libraryPojo;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
                String query = "select library_id,library_name from library WHERE librarain_id='" + librarian_id+"'";;
                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        libraryPojo = new LibraryPojo();
                        libraryPojo.setLibrary_name(cursor.getString(cursor.getColumnIndex("library_name")));
                        libraryPojo.setLibrary_id(cursor.getString(cursor.getColumnIndex("library_id")));
                        cursor.moveToNext();
                        library.put(libraryPojo.getLibrary_name(), Integer.valueOf(libraryPojo.getLibrary_id()));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        }
        return library;
    }
    @SuppressLint("Range")
    public BookIssuePojo getPregenentEdit(String local_id) {
        BookIssuePojo registrationPojo = new BookIssuePojo();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "select * from book_issue WHERE local_id=" + local_id;
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    registrationPojo.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));

                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return registrationPojo;
    }
    public long updateReporting(String table, String whr, String last_activity_id, String col) {
        long reporting_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();
                values.put("status", "1");
                values.put("flag", "1");
                values.put(col, last_activity_id);
                reporting_id = db.update(table, values, whr, null);

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return reporting_id;
    }

    public long update(String table, String whr, String last_activity_id, String col) {
        long book_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();
                values.put("status", "1");
                values.put("flag", "1");
                values.put(col, last_activity_id);
                book_id = db.update(table, values, whr, null);
                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return book_id;
    }
    public long updateActivityReporting(String table, String whr, String last_activity_id, String col) {
        long book_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();
                values.put("status", "1");
                values.put("flag", "1");
                values.put(col, last_activity_id);
                book_id = db.update(table, values, whr, null);

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return book_id;
    }
    public long DeleteBookupdate(String table, String whr) {
        long book_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {

                book_id = db.delete(table, whr, null);

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return book_id;
    }
    public long DeleteSubscriber(String table, String whr) {
        long book_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {

                book_id = db.delete(table, whr, null);

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return book_id;
    }

    public long updateee(String table, String whr, String last_transaction_id, String col) {
        long issue_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();
                values.put("status", "1");
                values.put("flag", "1");

                values.put(col, last_transaction_id);
                issue_id = db.update(table, values, whr, null);

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return issue_id;
    }
    public long updateeeReturnFlag(String table, String whr) {
        long issue_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();
                values.put("status", "1");
                values.put("flag", "1");

                issue_id = db.update(table, values, whr, null);
                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return issue_id;
    }
    public long ReturnBookFlagUpdate(String table, String whr,String col) {
        long issue_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();

                values.put("flag", col);

                issue_id = db.update(table, values, whr, null);

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return issue_id;
    }

    public long updateBookQty(String table, String whr, int qty,String col) {
        long issue_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();
                values.put(col, qty);
                issue_id = db.update(table, values, whr, null);

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return issue_id;
    }


    public long updateReturn(String table, String whr,String col1, String colValue1,String col2,String colValue2) {
        long issue_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();
                values.put(col1, colValue1);
                values.put(col2,colValue2);
                issue_id = db.update(table, values, whr, null);

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return issue_id;
    }
    public long updatee(String table, String whr, String last_subscriber_id, String col) {
        long subscriber_id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db != null && db.isOpen() && !db.isReadOnly())
            {
                ContentValues values = new ContentValues();
                values.put("status", "1");
                values.put("flag", "1");
                values.put(col, last_subscriber_id);
                subscriber_id = db.update(table, values, whr, null);

                db.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            db.close();
        }
        return subscriber_id;
    }
    @SuppressLint("Range")
    public ArrayList<AddBookPojo> getAddBookList(String namee) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<AddBookPojo> arrayList = new ArrayList<>();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "";
                if (namee.equals("")) {

                    query = "select * from resource where resource_name LIKE '" + namee + "%' OR author_name like '" + namee + "%'";

                } else {

                    query = "select * from resource where resource_name LIKE '" + namee + "%' OR author_name like '" + namee + "%'";

                }
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        AddBookPojo bookIssuePojo = new AddBookPojo();
                        // pregnantWomenPojo.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));
                        bookIssuePojo.setResource_name(cursor.getString(cursor.getColumnIndex("resource_name")));
                        bookIssuePojo.setAuthor_name(cursor.getString(cursor.getColumnIndex("author_name")));
                        bookIssuePojo.setResource_image(cursor.getString(cursor.getColumnIndex("resource_image")));

                        cursor.moveToNext();
                        arrayList.add(bookIssuePojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return arrayList;
    }
    public long IssueBook(BookIssuePojo householdMasterModel)
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        long ids =0;
        try
        {
            if(DB !=null && !DB.isReadOnly())
            {
                ContentValues values =new ContentValues();
                values.put("local_id",householdMasterModel.getLocal_id());
                values.put("librarain_id",householdMasterModel.getLibrarain_id());
                values.put("issue_recieve_type",householdMasterModel.getIssue_recieve_type());

                values.put("transaction_id",householdMasterModel.getTransaction_id());
                values.put("resource_id",householdMasterModel.getResource_id());
                values.put("subscriber_id",householdMasterModel.getSubscriber_id());
                values.put("issue_date",householdMasterModel.getIssue_date());
                values.put("status", "0");
                values.put("flag", "0");


                ids =DB.insert("transaction_book",null, values);
                DB.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            DB.close();
        }
        return ids;
    }
    @SuppressLint("Range")
    public ArrayList<BookIssuePojo> getRegistrationData3()
    {
        ArrayList<BookIssuePojo> addBookPojoArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if (db != null && db.isOpen() && !db.isReadOnly())
            {
                String query = "select * from transaction_book";

                @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {

                        BookIssuePojo list = new BookIssuePojo();
                        list.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));

                        list.setIssue_recieve_type(cursor.getString(cursor.getColumnIndex("issue_recieve_type")));

                        list.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        list.setTransaction_id(cursor.getString(cursor.getColumnIndex("transaction_id")));
                        list.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));
                        list.setSubscriber_id(cursor.getString(cursor.getColumnIndex("subscriber_id")));
                        list.setIssue_date(cursor.getString(cursor.getColumnIndex("issue_date")));

                        addBookPojoArrayList.add(list);
                        cursor.moveToNext();
                    }
                }
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return addBookPojoArrayList;

    }
    @SuppressLint("Range")
    public ArrayList<BookIssuePojo> getSt_BookIssueSyn() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<BookIssuePojo> arrayList = new ArrayList<BookIssuePojo>();
        BookIssuePojo bookIssuePojo;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "select * from transaction_book WHERE issue_recieve_type=1 and flag=0";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        bookIssuePojo = new BookIssuePojo();
//
                        bookIssuePojo.setSubscriber_id(cursor.getString(cursor.getColumnIndex("subscriber_id")));
                        bookIssuePojo.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        bookIssuePojo.setRecieved_date(cursor.getString(cursor.getColumnIndex("recieved_date")));
                        bookIssuePojo.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));
                        bookIssuePojo.setIssue_recieve_type(cursor.getString(cursor.getColumnIndex("issue_recieve_type")));
                        bookIssuePojo.setTransaction_id(cursor.getString(cursor.getColumnIndex("transaction_id")));
                        bookIssuePojo.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));
                        bookIssuePojo.setIssue_date(cursor.getString(cursor.getColumnIndex("issue_date")));

                        cursor.moveToNext();
                        arrayList.add(bookIssuePojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public ArrayList<BookIssuePojo> getSt_BookResiverSyn() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<BookIssuePojo> arrayList = new ArrayList<BookIssuePojo>();
        BookIssuePojo bookIssuePojo;
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "select * from transaction_book WHERE issue_recieve_type=0 and flag=0";
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        bookIssuePojo = new BookIssuePojo();
//
                        bookIssuePojo.setSubscriber_id(cursor.getString(cursor.getColumnIndex("subscriber_id")));
                        bookIssuePojo.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        bookIssuePojo.setRecieved_date(cursor.getString(cursor.getColumnIndex("recieved_date")));
                        bookIssuePojo.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));
                        bookIssuePojo.setIssue_recieve_type(cursor.getString(cursor.getColumnIndex("issue_recieve_type")));
                        bookIssuePojo.setTransaction_id(cursor.getString(cursor.getColumnIndex("transaction_id")));
                        bookIssuePojo.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));
                        bookIssuePojo.setIssue_date(cursor.getString(cursor.getColumnIndex("issue_date")));

                        cursor.moveToNext();
                        arrayList.add(bookIssuePojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public HashMap<Integer, String> getAllBooK(String book_name) {
        HashMap<Integer, String> book = new HashMap<>();
        AddBookPojo bookCategoryPojo;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
                String query = "select resource_id,resource_name,resource_unique_id from resource where resource_name='"+book_name+"' and  resource_status is not 1 and status is not 0  ";
                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        bookCategoryPojo = new AddBookPojo();
                        bookCategoryPojo.setResource_name(cursor.getString(cursor.getColumnIndex("resource_name")));
                        bookCategoryPojo.setResource_unique_id(cursor.getString(cursor.getColumnIndex("resource_unique_id")));

                        bookCategoryPojo.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));
                        cursor.moveToNext();
                        book.put(Integer.valueOf(bookCategoryPojo.getResource_id()),bookCategoryPojo.getResource_name()+" "+bookCategoryPojo.getResource_unique_id());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        }
        return book;
    }
    @SuppressLint("Range")
    public HashMap<Integer, String> getDeleteAllBooK(String book_name) {
        HashMap<Integer, String> book = new HashMap<>();
        AddBookPojo bookCategoryPojo;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen() && !sqLiteDatabase.isReadOnly()) {
                String query = "select resource_id,resource_name,resource_unique_id from resource where resource_name='"+book_name+"' and  resource_status is not 1 ";
                Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        bookCategoryPojo = new AddBookPojo();
                        bookCategoryPojo.setResource_name(cursor.getString(cursor.getColumnIndex("resource_name")));
                        bookCategoryPojo.setResource_unique_id(cursor.getString(cursor.getColumnIndex("resource_unique_id")));

                        bookCategoryPojo.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));
                        cursor.moveToNext();
                        book.put(Integer.valueOf(bookCategoryPojo.getResource_id()),bookCategoryPojo.getResource_name()+" "+bookCategoryPojo.getResource_unique_id());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sqLiteDatabase.close();
        }
        return book;
    }
    @SuppressLint("Range")
    public String getBookCount(String resource_name) {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(resource_name),resource_name from resource where resource_name='"+resource_name+"' group by resource_name", null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("count(resource_name)"));
        return sum;
    }

        @SuppressLint("Range")
    public String getPSStateSp(String id) {
        String sum = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select resource_id from book_issue where local_id ='" + id + "'", null);
        if (cursor.moveToFirst())
            sum = cursor.getString(cursor.getColumnIndex("resource_id"));
        return sum;
    }

    @SuppressLint("Range")
    public ArrayList<BookIssuePojo> getBookIssueList(String namee) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<BookIssuePojo> arrayList = new ArrayList<>();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "";
                if (namee.equals("")) {

                    query = "select * from transaction_book where issue_recieve_type is not 0 order by local_id desc";

                } else {

//                    query = "select * from book_issue where issue_to_name LIKE '" + namee + "%' OR book_name like '" + namee + "%' OR issue_date like '" + namee + "%'";
                    query = " select *,(select resource_name from resource a where a.resource_id=b.resource_id) as resourcename,(select subscriber_name from subscriber a where a.subscriber_id=b.subscriber_id) as subcribername  from transaction_book b where  where issue_recieve_type is not 0 and resourcename like '"+ namee+"%' or subcribername like '"+ namee+"%' or issue_date like '"+ namee+"%'";
                }
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        BookIssuePojo bookIssuePojo = new BookIssuePojo();
                        // pregnantWomenPojo.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));
                        bookIssuePojo.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));

                        bookIssuePojo.setIssue_recieve_type(cursor.getString(cursor.getColumnIndex("issue_recieve_type")));

                        bookIssuePojo.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        bookIssuePojo.setTransaction_id(cursor.getString(cursor.getColumnIndex("transaction_id")));
                        bookIssuePojo.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));
                        bookIssuePojo.setSubscriber_id(cursor.getString(cursor.getColumnIndex("subscriber_id")));
                        bookIssuePojo.setIssue_date(cursor.getString(cursor.getColumnIndex("issue_date")));
                        cursor.moveToNext();
                        arrayList.add(bookIssuePojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public ArrayList<BookIssuePojo> getBookIssueList1(String namee) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<BookIssuePojo> arrayList = new ArrayList<>();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "";
                if (namee.equals("")) {

                    query = " select *,(select resource_name from resource a where a.resource_id=b.resource_id) as resourcename,(select subscriber_name from subscriber a where a.subscriber_id=b.subscriber_id) as subcribername  from transaction_book b where issue_recieve_type is not 0 and resourcename like '"+ namee+"%' or subcribername like '"+ namee+"%' or issue_date like '"+ namee+"%'";

                } else {

//                    query = "select * from book_issue where issue_to_name LIKE '" + namee + "%' OR book_name like '" + namee + "%' OR issue_date like '" + namee + "%'";
                    query = " select *,(select resource_name from resource a where a.resource_id=b.resource_id) as resourcename,(select subscriber_name from subscriber a where a.subscriber_id=b.subscriber_id) as subcribername  from transaction_book b where issue_recieve_type is not 0 and resourcename like '"+ namee+"%' or subcribername like '"+ namee+"%' or issue_date like '"+ namee+"%'";
                }
                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        BookIssuePojo bookIssuePojo = new BookIssuePojo();
                        // pregnantWomenPojo.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));
                        bookIssuePojo.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));

                        bookIssuePojo.setIssue_recieve_type(cursor.getString(cursor.getColumnIndex("issue_recieve_type")));

                        bookIssuePojo.setLibrarain_id(cursor.getString(cursor.getColumnIndex("librarain_id")));
                        bookIssuePojo.setTransaction_id(cursor.getString(cursor.getColumnIndex("transaction_id")));
                        bookIssuePojo.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));
                        bookIssuePojo.setSubscriber_id(cursor.getString(cursor.getColumnIndex("subscriber_id")));
                        bookIssuePojo.setIssue_date(cursor.getString(cursor.getColumnIndex("issue_date")));

                        cursor.moveToNext();
                        arrayList.add(bookIssuePojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return arrayList;
    }

    @SuppressLint("Range")
    public ArrayList<AddBookPojo> Count(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<AddBookPojo> arrayList = new ArrayList<>();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "";

                    query = "select * from resource where resource_name='"+name+"' and available_count is not 0";

                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        AddBookPojo bookIssuePojo = new AddBookPojo();
                        // pregnantWomenPojo.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));
                        bookIssuePojo.setResource_name(cursor.getString(cursor.getColumnIndex("resource_name")));

                        cursor.moveToNext();
                        arrayList.add(bookIssuePojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return arrayList;
    }

    @SuppressLint("Range")
    public ArrayList<AddBookPojo> getBookId(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<AddBookPojo> arrayList = new ArrayList<>();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "";

                query = "select resource_id from resource where resource_name='"+name+"' and resource_status is not 1";

                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        AddBookPojo bookIssuePojo = new AddBookPojo();
                        // pregnantWomenPojo.setLocal_id(cursor.getString(cursor.getColumnIndex("local_id")));
                        bookIssuePojo.setResource_id(cursor.getString(cursor.getColumnIndex("resource_id")));

                        cursor.moveToNext();
                        arrayList.add(bookIssuePojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return arrayList;
    }


    @SuppressLint("Range")
    public ArrayList<AddBookPojo> getAllBooKs() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<AddBookPojo> arrayList = new ArrayList<>();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "";

                query = "select resource_name from resource";

                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        AddBookPojo bookPojo = new AddBookPojo();
                        bookPojo.setResource_name(cursor.getString(cursor.getColumnIndex("resource_name")));

                        cursor.moveToNext();
                        arrayList.add(bookPojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return arrayList;
    }
    @SuppressLint("Range")
    public ArrayList<AddBookPojo> getAllBooKselective(String book_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<AddBookPojo> arrayList = new ArrayList<>();
        try {
            if (db != null && db.isOpen() && !db.isReadOnly()) {
                String query = "";

                query = "select * from resource where resource_name='"+book_name +"'";

                Cursor cursor = db.rawQuery(query, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        AddBookPojo bookPojo = new AddBookPojo();
                        bookPojo.setCategory_id(cursor.getString(cursor.getColumnIndex("category_id")));
                        bookPojo.setAuthor_name(cursor.getString(cursor.getColumnIndex("author_name")));
                        bookPojo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        bookPojo.setLanguage_id(cursor.getString(cursor.getColumnIndex("language_id")));
                        bookPojo.setResource_image(cursor.getString(cursor.getColumnIndex("resource_image")));

                        cursor.moveToNext();
                        arrayList.add(bookPojo);
                    }
                    db.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            db.close();
        }
        return arrayList;
    }
}
