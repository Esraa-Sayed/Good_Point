package com.helloworld.goodpoint.retrofit;

import com.helloworld.goodpoint.pojo.RegUser;
import com.helloworld.goodpoint.pojo.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("auth/signup/")
    Call<RegUser> storePost(@Body RegUser regUser);


    @FormUrlEncoded
    @POST("api/token/")
    Call<Token> getToken(@Field("username") String emailInput, @Field("password") String passwordInput);


    @FormUrlEncoded
    @POST("api/token/refresh/")
    Call<Token> refresh(@Field("refresh") String refresh);

}






















