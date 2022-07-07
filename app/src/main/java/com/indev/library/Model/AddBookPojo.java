package com.indev.library.Model;

public class AddBookPojo
{
    private String local_id;
    private String user_id;
    private String resource_id;
    private String resource_unique_id;

    private String total_quantity;
    private String language_id;
    private String source_id;
    private String donor_name;
    private String resource_name;
    private String author_name;
    private String available_count;
    private String description;
    private String librarain_id;
    private String library_id;
    private String category_id;
    private String status;
    private  String no_of_days_book_issued;
    private String flag;
    private String remark;

    private String resource_status;

    private  String resource_image;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNo_of_days_book_issued() {
        return no_of_days_book_issued;
    }

    public void setNo_of_days_book_issued(String no_of_days_book_issued) {
        this.no_of_days_book_issued = no_of_days_book_issued;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getResource_status() {
        return resource_status;
    }

    public void setResource_status(String resource_status) {
        this.resource_status = resource_status;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getDonor_name() {
        return donor_name;
    }

    public void setDonor_name(String donor_name) {
        this.donor_name = donor_name;
    }

    public String getResource_unique_id() {
        return resource_unique_id;
    }

    public void setResource_unique_id(String resource_unique_id) {
        this.resource_unique_id = resource_unique_id;
    }

    public String getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(String total_quantity) {
        this.total_quantity = total_quantity;
    }

    public String getLibrarain_id() {
        return librarain_id;
    }

    public void setLibrarain_id(String librarain_id) {
        this.librarain_id = librarain_id;
    }

    public String getLibrary_id() {
        return library_id;
    }

    public void setLibrary_id(String library_id) {
        this.library_id = library_id;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public String getAvailable_count() {
        return available_count;
    }

    public void setAvailable_count(String available_count) {
        this.available_count = available_count;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getResource_image() {
        return resource_image;
    }

    public void setResource_image(String resource_image) {
        this.resource_image = resource_image;
    }


    private static final String TABLE_NAME = "resource";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_RESOURCE_ID="resource_id";
    private static final String COLUMN_TOTAL_QUANTITY="total_quantity";
    private static final String COLUMN_RESOURCE_UNIQUE_ID="resource_unique_id";
    private static final String COLUMN_LANGUAGE_ID="language_id";
    private static final String COLUMN_SOURCE_ID="source_id";
    private static final String COLUMN_RESOURCE_STATUS="resource_status";

    private static final String COLUMN_DONOR_NAME="donor_name";

    private static final String COLUMN_USER_ID="user_id";
    private static final String COLUMN_RESOURCE_NAME = "resource_name";
    private static final String COLUMN_AUTHOR_NAME = "author_name";
    private static final String COLUMN_LIBRARAIN_ID = "librarain_id";
    private static final String COLUMN_LIBRARY_ID = "library_id";

    private static final String COLUMN_AVAILABLE_COUNT = "available_count";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_RESOURCE_IMAGE = "resource_image";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_REMARK = "remark";
    private static final String COLUMN_NO_OF_DAYS_BOOK_ISSUED = "no_of_days_book_issued";

    private static final String COLUMN_FLAG = "flag";


    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_RESOURCE_ID + " TEXT, "
            +COLUMN_USER_ID + " TEXT, "
            +COLUMN_LIBRARAIN_ID + " TEXT, "
            +COLUMN_LIBRARY_ID + " TEXT, "
            + COLUMN_RESOURCE_NAME + " TEXT, "
            + COLUMN_RESOURCE_UNIQUE_ID + " TEXT, "
            + COLUMN_DONOR_NAME + " TEXT, "
            + COLUMN_LANGUAGE_ID + " TEXT, "
            + COLUMN_SOURCE_ID + " TEXT, "
            + COLUMN_NO_OF_DAYS_BOOK_ISSUED + " TEXT, "
            + COLUMN_REMARK + " TEXT, "
            + COLUMN_RESOURCE_STATUS + " TEXT, "
            + COLUMN_TOTAL_QUANTITY + " TEXT, "
            + COLUMN_AUTHOR_NAME + " TEXT, "
            + COLUMN_RESOURCE_IMAGE + " TEXT, "
            + COLUMN_FLAG + " TEXT, "
            + COLUMN_STATUS + " TEXT, "
            + COLUMN_AVAILABLE_COUNT + " TEXT, "
            + COLUMN_CATEGORY_ID + " TEXT, "
            + COLUMN_DESCRIPTION + " TEXT "
            + ")";
}
