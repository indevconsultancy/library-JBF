package com.indev.library.Model;

public class ActivityReportingPojo
{
    private String local_id;
    private String id;
    private String activity_id;
    private String librarain_id;
    private String activity_image;
    private String status;

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

    public String getLibrarain_id() {
        return librarain_id;
    }

    public void setLibrarain_id(String librarain_id) {
        this.librarain_id = librarain_id;
    }

    public String getActivity_image() {
        return activity_image;
    }

    public void setActivity_image(String activity_image) {
        this.activity_image = activity_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private static final String TABLE_NAME="reporting";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_ACTIVITY_ID="activity_id";
    private static final String COLUMN_LIBRARAIN_ID="librarain_id";
    private static final String COLUMN_ACTIVITY_IMAGE="activity_image";
    private static final String COLUMN_STATUS="status";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_ID + " INTEGER, "
            +COLUMN_ACTIVITY_ID + " TEXT, "
            +COLUMN_STATUS + " TEXT, "
            +COLUMN_ACTIVITY_IMAGE + " TEXT, "
            +COLUMN_LIBRARAIN_ID + " TEXT "
            + ")";
}
