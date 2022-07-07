package com.indev.library.Model;

import java.util.ArrayList;

public class ActivityReportingPojo
{
    private String local_id;
    private String id;
    private String activity_id;
    private String librarain_id;
    private String activity_reporting_id;
    private String activity_image2;
    private String created_at;
    private String uuid;
    private String status;
    private String flag;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    private ArrayList<ReportingImagePojo>activity_image;

    public ArrayList<ReportingImagePojo> getActivity_image() {
        return activity_image;
    }

    public void setActivity_image(ArrayList<ReportingImagePojo> activity_image) {
        this.activity_image = activity_image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getActivity_reporting_id() {
        return activity_reporting_id;
    }

    public void setActivity_reporting_id(String activity_reporting_id) {
        this.activity_reporting_id = activity_reporting_id;
    }

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

    public String getActivity_image2() {
        return activity_image2;
    }

    public void setActivity_image2(String activity_image2) {
        this.activity_image2 = activity_image2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private static final String TABLE_NAME="activity_reporting";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_ACTIVITY_ID="activity_id";

    private static final String COLUMN_ACTIVITY_REPORTING_ID="activity_reporting_id";
    private static final String COLUMN_LIBRARAIN_ID="librarain_id";
    private static final String COLUMN_ACTIVITY_IMAGE="activity_image";
    private static final String COLUMN_STATUS="status";
    private static final String COLUMN_UUID="uuid";

    private static final String COLUMN_FLAG="flag";

    private static final String COLUMN_CREATED_AT="created_at";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_ID + " INTEGER, "
            +COLUMN_ACTIVITY_ID + " TEXT, "
            +COLUMN_ACTIVITY_REPORTING_ID + " TEXT, "
            +COLUMN_STATUS + " TEXT, "
            +COLUMN_UUID + " TEXT, "
            +COLUMN_FLAG + " TEXT, "
            +COLUMN_ACTIVITY_IMAGE + " TEXT, "
            +COLUMN_CREATED_AT + " TEXT, "
            +COLUMN_LIBRARAIN_ID + " TEXT "
            + ")";
}
