package com.helloworld.goodpoint.retrofit;

import com.helloworld.goodpoint.pojo.LostItem;
import com.helloworld.goodpoint.pojo.RegUser;
import com.helloworld.goodpoint.pojo.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    //@POST("auth/signup/")
    //Call<RegUser> storePost(@Body RegUser regUser);
    @FormUrlEncoded
    @POST("auth/signup/")
    Call<Token> storePost(@Field("username") String emailInput, @Field("password") String passwordInput
                         ,@Field("first_name") String usernameInput, @Field("phone") String pInput
                         ,@Field("city") String cityInput, @Field("birthdate") String Datee);


    @FormUrlEncoded
    @POST("api/token/")
    Call<Token> getToken(@Field("username") String emailInput, @Field("password") String passwordInput);


    @FormUrlEncoded
    @POST("api/token/refresh/")
    Call<Token> refresh(@Field("refresh") String refresh);

    @FormUrlEncoded
    @POST("losts/lostobject/")
    Call<LostItem> storeLost(/*@Query("user_id") int userid ,*/ @Field("date") String Datee, @Field("city") String cityInput
                            ,@Field("is_matched") String is_matched);

    @FormUrlEncoded
    @POST("losts/lostitem/")
    Call<LostItem> store2Lost(@Field("type") String Type, @Field("serial_number") String Serial
                             ,@Field("brand") String brand, @Field("color") String ObjectColor
                             ,@Field("description") String textArea_information);


}






















