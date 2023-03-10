package com.indev.library.RestAPI;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Library_API
{
    @POST("add_resource.php")
    Call<JsonObject> AddBookRegistration(@Body RequestBody body);
    @POST("add-subscriber.php")
    Call<JsonObject> AddSubscriberRegistration(@Body RequestBody body);
    @POST("receive_transaction.php")
    Call<JsonObject> UpdateReturnBook(@Body RequestBody body);

    @POST("add-issued.php")
    Call<JsonObject> AddIssueRegistration(@Body RequestBody body);

    @POST("download_general.php")
    Call<JsonArray> getSpinner(@Body RequestBody body);

    @POST("subscriber-list.php")
    Call<JsonArray> DatadownloadSubscriber(@Body RequestBody body);

    @POST("resource-list.php")
    Call<JsonArray> DatadownloadResourse(@Body RequestBody body);

    @POST("download-issuebook.php")
    Call<JsonArray> DatadownloadIssuBook(@Body RequestBody body);

    @POST("activity_reporting_download.php")
    Call<JsonArray> DatadownloadActivityReporting(@Body RequestBody body);

    @POST("user_login.php")
    Call<JsonObject> LoginApi(@Body RequestBody body);

    @POST("change_password.php")
    Call<JsonObject> ChangePasswordApi(@Body RequestBody body);

    @POST("otp_verification.php")
    Call<JsonObject>ForgetPasswordApi(@Body RequestBody body);
    @POST("forget_password.php")
    Call<JsonObject>ConfirmOtpApi(@Body RequestBody body);

    @POST("add_activity_reporting.php")
    Call<JsonObject> Reporting(@Body RequestBody body);
    @POST("delete_resource.php")
    Call<JsonObject> DeleteBook(@Body RequestBody body);

    @POST("delete_subscriber.php")
    Call<JsonObject> DeleteSubscriber(@Body RequestBody body);
}
