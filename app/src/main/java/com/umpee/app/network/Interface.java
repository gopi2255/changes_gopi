package com.umpee.app.network;


import com.umpee.app.model.ModelMessage;
import com.umpee.app.model.ModelUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Interface {

    @FormUrlEncoded
    @POST("User/login")
    @Headers({"Accept: application/json"})
    Call<ModelUser> login(@Field("email") String email, @Field("password") String password, @Field("ip") String ip, @Field("umpire") int umpire);

    @FormUrlEncoded
    @POST("User/updateInfo")
    @Headers({"Accept: application/json"})
    Call<ModelUser> updateInfo(@Field("token") String token, @Field("ip") String ip);

    @FormUrlEncoded
    @POST("User/getUmpires")
    @Headers({"Accept: application/json"})
    Call<ArrayList<ModelUser>> getUmpires(@Field("token") String token);

    @FormUrlEncoded
    @POST("User/sendOut")
    @Headers({"Accept: application/json"})
    Call<ModelMessage> sendOut(@Field("is_out") String  isOut);
}