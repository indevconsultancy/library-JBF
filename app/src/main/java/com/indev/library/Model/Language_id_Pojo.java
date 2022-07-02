package com.indev.library.Model;

public class Language_id_Pojo
{
    private String local_id;
    private  String id;
    private String language_id;
    private  String language_name;
    private String status;
    private  String created_at;

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
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
    private static final String TABLE_NAME = "language";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_LANGUAGE_ID="language_id";
    private static final String COLUMN_LANGUAGE_NAME="language_name";
    private static final String COLUMN_STATUS="status";
    private static final String COLUMN_CREATED_AT="created_at";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_ID + " INTEGER, "
            +COLUMN_LANGUAGE_ID + " INTEGER, "
            +COLUMN_LANGUAGE_NAME + " TEXT, "
            +COLUMN_STATUS + " TEXT, "
            + COLUMN_CREATED_AT + " TEXT "
            + ")";

}
