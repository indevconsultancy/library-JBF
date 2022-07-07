package com.indev.library.Model;

public class ReportingImagePojo {
    private String uuid;
    private String image;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    private static final String TABLE_NAME="activity_reporting_image";
    private static final String COLUMN_UUID="uuid";
    private static final String COLUMN_IMAGE="image";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_UUID + " TEXT , "
            +COLUMN_IMAGE + " TEXT "
            + ")";
}
