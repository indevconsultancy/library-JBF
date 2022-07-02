package com.indev.library.Model;

public class BookCategoryPojo
{
      private String local_id;
    private String category_id;
    private String category_name;
    private String status;
    private String created_at;

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
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
    private static final String TABLE_NAME = "book_category";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_CATEGORY_ID="category_id";
    private static final String COLUMN_CATEGORY_NAME = "category_name";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_CREATED_AT="created_at";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_CATEGORY_ID + " INTEGER, "
            + COLUMN_CATEGORY_NAME + " TEXT, "
            + COLUMN_STATUS + " TEXT, "
            + COLUMN_CREATED_AT + " TEXT "
            + ")";
}
