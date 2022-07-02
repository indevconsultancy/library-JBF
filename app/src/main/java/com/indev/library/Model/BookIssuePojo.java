package com.indev.library.Model;

public class BookIssuePojo
{
    private String local_id;
    private String id;
    private String librarain_id;
    private String resource_id;
    private String transaction_id;
    private String recieved_date;
    private String issue_date;
    private String issue_recieve_type;
    private String subscriber_id;
    private String return_status;
    private String status;
    private String user_id;
    private String flag;


    private String created_at;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getReturn_status() {
        return return_status;
    }

    public void setReturn_status(String return_status) {
        this.return_status = return_status;
    }

    public String getLibrarain_id() {
        return librarain_id;
    }

    public void setLibrarain_id(String librarain_id) {
        this.librarain_id = librarain_id;
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

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getRecieved_date() {
        return recieved_date;
    }

    public void setRecieved_date(String recieved_date) {
        this.recieved_date = recieved_date;
    }

    public String getIssue_recieve_type() {
        return issue_recieve_type;
    }

    public void setIssue_recieve_type(String issue_recieve_type) {
        this.issue_recieve_type = issue_recieve_type;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }
    private static final String TABLE_NAME = "transaction_book";
    private static final String COLUMN_LOCAL_ID="local_id";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_LIBRARAIN_ID="librarain_id";
    private static final String COLUMN_RESOURCE_ID = "resource_id";
    private static final String COLUMN_TRANSACTION_ID = "transaction_id";
    private static final String COLUMN_RECIEVED_DATE = "recieved_date";
    private static final String COLUMN_RETURN_STATUS = "return_status";

    private static final String COLUMN_ISSUE_RECIEVE_TYPE = "issue_recieve_type";
    private static final String COLUMN_SUBSCRIBER_ID = "subscriber_id";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_FLAG = "flag";

    private static final String COLUMN_ISSUE_DATE = "issue_date";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + COLUMN_LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_ID + " INTEGER, "
            +COLUMN_LIBRARAIN_ID + " INTEGER, "
            + COLUMN_RESOURCE_ID + " INTEGER, "
            + COLUMN_TRANSACTION_ID + " TEXT, "
            + COLUMN_RECIEVED_DATE + " TEXT, "
            + COLUMN_RETURN_STATUS + " TEXT, "
            + COLUMN_FLAG + " TEXT, "
            + COLUMN_ISSUE_RECIEVE_TYPE + " TEXT, "
            + COLUMN_SUBSCRIBER_ID + " INTEGER, "
            + COLUMN_CREATED_AT + " TEXT, "
            + COLUMN_STATUS + " TEXT, "
            + COLUMN_ISSUE_DATE+ " TEXT "
            + ")";
}
