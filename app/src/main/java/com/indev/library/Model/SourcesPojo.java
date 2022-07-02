package com.indev.library.Model;

public class SourcesPojo
{
    private String local_id;
    private  String id;
    private String source_id;
    private  String source_name;
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

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
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

    private static final String TABLE_NAME = "sources_of_resource";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_SOURCE_ID="source_id";
    private static final String COLUMN_SOURCE_NAME="source_name";
    private static final String COLUMN_STATUS="status";
    private static final String COLUMN_CREATED_AT="created_at";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_ID + " INTEGER, "
            +COLUMN_SOURCE_ID + " INTEGER, "
            +COLUMN_SOURCE_NAME + " TEXT, "
            +COLUMN_STATUS + " TEXT, "
            + COLUMN_CREATED_AT + " TEXT "
            + ")";
}
