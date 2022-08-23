package com.indev.library.Model;

public class LibraryPojo
{
    private String local_id;
    private String id;

    private String library_id;
    private String librarain_id;
    private String library_name;
    private String state_id;
    private String district_id;
    private String village_name;
    private String created_at;
    private String status;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getLibrary_id() {
        return library_id;
    }

    public void setLibrary_id(String library_id) {
        this.library_id = library_id;
    }

    public String getLibrarain_id() {
        return librarain_id;
    }

    public void setLibrarain_id(String librarain_id) {
        this.librarain_id = librarain_id;
    }

    public String getLibrary_name() {
        return library_name;
    }

    public void setLibrary_name(String library_name) {
        this.library_name = library_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    private static final String TABLE_NAME = "library";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_LIBRARY_ID="library_id";
    private static final String COLUMN_CREATED_AT="created_at";
    private static final String COLUMN_LIBRARY_NAME = "library_name";
    private static final String COLUMN_LIBRARAIN_ID = "librarain_id";
    private static final String COLUMN_STATE_ID = "state_id";
    private static final String COLUMN_DISTRICT_ID = "district_id";
    private static final String COLUMN_VILLAGE_NAME = "village_name";
    private static final String COLUMN_STATUS = "status";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_ID + " INTEGER, "
            +COLUMN_STATE_ID + " INTEGER, "
            +COLUMN_LIBRARAIN_ID + " TEXT, "
            +COLUMN_CREATED_AT + " TEXT, "
            +COLUMN_LIBRARY_ID + " INTEGER, "
            + COLUMN_LIBRARY_NAME + " TEXT, "
            + COLUMN_DISTRICT_ID + " TEXT, "
            + COLUMN_STATUS + " TEXT, "
            + COLUMN_VILLAGE_NAME + " TEXT "
            + ")";


}
