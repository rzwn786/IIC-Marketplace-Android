package com.example.marketpalce.API;

import com.example.marketpalce.Model.ResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequestData {
    @GET("retrieve.php")
    Call<ResponseModel> ardRetrieveData();

    @Multipart
    @POST("create.php")
    Call<ResponseModel> ardCreateData(
            @Part("userid") RequestBody userid,
            @Part("title") RequestBody title,
            @Part("price") RequestBody price,
            @Part("categories") RequestBody categories,
            @Part("condition") RequestBody condition,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("cod") RequestBody cod,
            @Part("pos") RequestBody pos,
            @Part MultipartBody.Part upload_file
    );

    @Multipart
    @POST("signup.php")
    Call<ResponseModel> ardSignUpUser(
            @Part("fname") RequestBody fname,
            @Part("lname") RequestBody lname,
            @Part("user") RequestBody user,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("pass") RequestBody pass
            );

}
