package com.helloworld.goodpoint.retrofit;

import com.google.gson.JsonObject;
import com.helloworld.goodpoint.pojo.LostItem;
import com.helloworld.goodpoint.pojo.ObjectLocation;
import com.helloworld.goodpoint.pojo.RegUser;
import com.helloworld.goodpoint.pojo.Token;
import com.helloworld.goodpoint.pojo.User;
import com.helloworld.goodpoint.pojo.UserMap;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    /*
    @Multipart
    @FormUrlEncoded
    @POST("auth/signup/")
    Call<RegUser> storePost(@Field("username") String emailInput
                            , @Field("password") String passwordInput, @Field("first_name") String usernameInput
                            , @Field("phone") String pInput, @Field("city") String cityInput
                            , @Field("birthdate") String Datee);/*@Part MultipartBody.Part profile_pic/*

     */


    @Multipart
    @POST("auth/signup/")
    Call<RegUser> storePost(@Part("username") String emailInput
                            , @Part("password") String passwordInput, @Part("first_name") String usernameInput
                            , @Part("phone") String pInput, @Part("city") String cityInput
                            , @Part("birthdate") String Datee, @Part MultipartBody.Part profile_pic);




    @FormUrlEncoded
    @POST("api/token/")
    Call<Token> getToken(@Field("username") String emailInput, @Field("password") String passwordInput);

    @FormUrlEncoded
    @POST("api/token/refresh/")
    Call<Token> refresh(@Field("refresh") String refresh);


    @POST("auth/signin/")
    Call<JsonObject> getData(@Header("Authorization") String token);

    //----------------------------------------------------------------------------------------------

    @FormUrlEncoded
    @POST("losts/lostobject/")
    Call<LostItem> storeLostObj(@Field("user_id") String id, @Field("date") String Datee, @Field("city") String cityInput);

    @Multipart
    @POST("losts/lostitem/")
    Call<JsonObject> storeLostItem(@Part("id") int obj_id, @Part("type") String Type, @Part("serial_number") String Serial
                             , @Part("brand") String brand, @Part("color") String ObjectColor
                             , @Part("description") String textArea_information, @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("losts/lostperson_image/")
    Call<LostItem> storeLost(@Part("id") String id, @Part MultipartBody.Part image);





    //----------------------------------------------------------------------------------------------

    @GET("losts/map/")
    Call<List<ObjectLocation>> getPoint();

    @GET("losts/founder/")
    Call<UserMap> getUserMap(@Query("id") int id);


}






















