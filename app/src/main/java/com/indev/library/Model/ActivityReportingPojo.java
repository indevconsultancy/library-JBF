package com.indev.library.Model;

public class ActivityReportingPojo
{
    private String local_id;
    private String id;
    private String reporting;
    private String photo;
    private String status;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReporting() {
        return reporting;
    }

    public void setReporting(String reporting) {
        this.reporting = reporting;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private static final String TABLE_NAME="reporting";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_REPORTING="reporting";
    private static final String COLUMN_PHOTO="photo";
    private static final String COLUMN_STATUS="status";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_ID + " INTEGER, "
            +COLUMN_REPORTING + " TEXT, "
            +COLUMN_STATUS + " TEXT, "
            +COLUMN_PHOTO + " TEXT "
            + ")";
}
