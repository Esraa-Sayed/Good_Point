package com.helloworld.goodpoint.retrofit;

import com.google.gson.JsonObject;
import com.helloworld.goodpoint.pojo.LostItem;
import com.helloworld.goodpoint.pojo.ObjectLocation;
import com.helloworld.goodpoint.pojo.RegUser;
import com.helloworld.goodpoint.pojo.Token;
import com.helloworld.goodpoint.pojo.User;
import com.helloworld.goodpoint.pojo.UserMap;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @Multipart
    @FormUrlEncoded
    @POST("auth/signup/")
    Call<RegUser> storePost(@Field("username") String emailInput
                            , @Field("password") String passwordInput, @Field("first_name") String usernameInput
                            , @Field("phone") String pInput, @Field("city") String cityInput
                            , @Field("birthdate") String Datee/*@Part MultipartBody.Part profile_pic/* @Field("profile_pic") String images,*/);

  /*  @FormUrlEncoded
    @POST("media/profile/")
    Call<RegUser> storeImage(@Field("profile_pic") String images);*/




    @FormUrlEncoded
    @POST("api/token/")
    Call<Token> getToken(@Field("username") String emailInput, @Field("password") String passwordInput);

    @FormUrlEncoded
    @POST("api/token/refresh/")
    Call<Token> refresh(@Field("refresh") String refresh);


    @POST("auth/signin/")
    Call<JsonObject> getData(@Header("Authorization") String token);

    //----------------------------------------------------------------------------------------------

    //@GET("losts/lostobject/")
    //Call<List<LostItem>> getuser(@Query("user_id") int idd);


    @FormUrlEncoded
    @POST("losts/lostobject/")
    Call<LostItem> storeLost(@Field("user_id") String id, @Field("date") String Datee, @Field("city") String cityInput);

    @FormUrlEncoded
    @POST("losts/lostitem/")
    Call<LostItem> store2Lost(@Field("type") String Type, @Field("serial_number") String Serial
                             ,@Field("brand") String brand, @Field("color") String ObjectColor
                             ,@Field("description") String textArea_information);

    //----------------------------------------------------------------------------------------------

    @GET("losts/map/")
    Call<List<ObjectLocation>> getPoint();

    @GET("losts/founder/")
    Call<UserMap> getUserMap(@Query("id") int id);


}






















