package com.indev.library.Model;

public class SubscriberPojo
{
    private String local_id;
    private String subscriber_id;

    private String user_id;
   private  String subscriber_unique_id;
    private String subscriber_name;
    private String subscriber_image;
    private String date_of_birth;
    private String address;
    private String mobile_number;
    private String email;
    private String gender;
    private String category_id;
    private String librarain_id;
    private String latitu;
    private String longitu;
    private String library_id;
    private String flag;


    public String getLibrary_id() {
        return library_id;
    }

    public void setLibrary_id(String library_id) {
        this.library_id = library_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSubscriber_unique_id() {
        return subscriber_unique_id;
    }

    public void setSubscriber_unique_id(String subscriber_unique_id) {
        this.subscriber_unique_id = subscriber_unique_id;
    }

    public String getLibrarain_id() {
        return librarain_id;
    }

    public void setLibrarain_id(String librarain_id) {
        this.librarain_id = librarain_id;
    }

    public String getLatitu() {
        return latitu;
    }

    public void setLatitu(String latitu) {
        this.latitu = latitu;
    }

    public String getLongitu() {
        return longitu;
    }

    public void setLongitu(String longitu) {
        this.longitu = longitu;
    }

    private String status;
    private String created_at;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getSubscriber_name() {
        return subscriber_name;
    }

    public void setSubscriber_name(String subscriber_name) {
        this.subscriber_name = subscriber_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSubscriber_image() {
        return subscriber_image;
    }

    public void setSubscriber_image(String subscriber_image) {
        this.subscriber_image = subscriber_image;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }



    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }


    private static final String TABLE_NAME = "subscriber";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_SUBSCRIBER_ID="subscriber_id";
    private static final String COLUMN_USER_ID="user_id";
    private static final String COLUMN_SUBSCRIBER_NAME="subscriber_name";
    private static final String COLUMN_SUBSCRIBER_IMAGE = "subscriber_image";
    private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    private static final String COLUMN_ADDRESS= "address";
    private static final String COLUMN_MOBILE_NUMBER= "mobile_number";
    private static final String COLUMN_EMAIL= "email";
    private static final String COLUMN_GENDER= "gender";
    private static final String COLUMN_CATEGORY_ID= "category_id";
    private static final String COLUMN_LIBRARAIN_ID= "librarain_id";
    private static final String COLUMN_LIBRARY_ID= "library_id";
    private static final String COLUMN_LATITU= "latitu";
    private static final String COLUMN_LONGITU= "longitu";

    private static final String COLUMN_SUBSCRIBER_UNIQUE_ID= "subscriber_unique_id";

    private static final String COLUMN_STATUS= "status";
    private static final String COLUMN_FLAG= "flag";

    private static final String COLUMN_CREATED_AT= "created_at";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_SUBSCRIBER_ID + " INTEGER, "
            +COLUMN_USER_ID + " INTEGER, "
            + COLUMN_SUBSCRIBER_NAME + " TEXT, "
            + COLUMN_EMAIL + " TEXT, "
            + COLUMN_GENDER + " TEXT, "
            + COLUMN_LIBRARAIN_ID + " TEXT, "
            + COLUMN_LIBRARY_ID + " TEXT, "
            + COLUMN_LATITU + " TEXT, "
            + COLUMN_LONGITU + " TEXT, "
            + COLUMN_FLAG + " TEXT, "
            + COLUMN_CATEGORY_ID + " TEXT, "
            + COLUMN_SUBSCRIBER_UNIQUE_ID + " TEXT, "
            + COLUMN_SUBSCRIBER_IMAGE + " TEXT, "
            + COLUMN_DATE_OF_BIRTH + " TEXT, "
            + COLUMN_ADDRESS + " TEXT, "
            + COLUMN_MOBILE_NUMBER + " TEXT, "
            + COLUMN_STATUS + " TEXT, "
            + COLUMN_CREATED_AT + " TEXT "
            + ")";
}
