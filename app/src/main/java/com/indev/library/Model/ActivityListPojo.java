package com.indev.library.Model;

public class ActivityListPojo
{
    private String local_id;
    private String id;
    private String activity_id;
    private String activity_name;
    private String status;
    private String created_at;

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

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
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
    private static final String TABLE_NAME="activity_list";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_ACTIVITY_ID="activity_id";
    private static final String COLUMN_ACTIVITY_NAME="activity_name";
    private static final String COLUMN_STATUS="status";
    private static final String COLUMN_CREATED_AT="created_at";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_ID + " INTEGER, "
            +COLUMN_ACTIVITY_ID + " TEXT, "
            +COLUMN_ACTIVITY_NAME + " TEXT, "
            +COLUMN_STATUS + " TEXT, "
            +COLUMN_CREATED_AT + " TEXT "
            + ")";
}
