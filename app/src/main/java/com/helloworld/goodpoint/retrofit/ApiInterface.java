package com.helloworld.goodpoint.retrofit;

import com.google.gson.JsonObject;
import com.helloworld.goodpoint.pojo.FoundItem;
import com.helloworld.goodpoint.pojo.FoundPerson;
import com.helloworld.goodpoint.pojo.LostItem;
import com.helloworld.goodpoint.pojo.LostPerson;
import com.helloworld.goodpoint.pojo.ObjectLocation;
import com.helloworld.goodpoint.pojo.RegUser;
import com.helloworld.goodpoint.pojo.Token;
import com.helloworld.goodpoint.pojo.UserMap;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/signup/")
    Call<RegUser> storePost(@Field("username") String emailInput
                            , @Field("password") String passwordInput, @Field("first_name") String usernameInput
                            , @Field("phone") String pInput, @Field("city") String cityInput
                            , @Field("birthdate") String Datee);


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
    Call<JsonObject> storeLostObj(@Field("user_id") String id, @Field("date") String Datee, @Field("city") String cityInput);

    @FormUrlEncoded
    @POST("losts/lostitem/")
    Call<LostItem> storeLostItem(@Field("id") String obj_id, @Field("type") String Type, @Field("serial_number") String Serial
            , @Field("brand") String brand, @Field("color") String ObjectColor
            , @Field("description") String textArea_information);

    @Multipart
    @POST("losts/lostitem/")
    Call<LostItem> storeLostItem(@Part("id") String obj_id, @Part("type") String Type, @Part("serial_number") String Serial
                             , @Part("brand") String brand, @Part("color") String ObjectColor
                             , @Part("description") String textArea_information, @Part MultipartBody.Part image);

    @Multipart
    @POST("losts/lostperson/")
    Call<JsonObject> storeLostPerson(@Part("date") String Date, @Part("city") String city, @Part("user_id") String user_id
                                   , @Part("name") String name, @Part MultipartBody.Part images);

    //----------------------------------------------------------------------------------------------

    @FormUrlEncoded
    @POST("losts/foundobject/")
    Call<JsonObject> storeFoundObj(@Field("user_id") String id, @Field("date") String Datee, @Field("city") String cityInput
                                 , @Field("longitude") double longitude, @Field("latitude") double latitude);

    @FormUrlEncoded
    @POST("losts/founditem/")
    Call<FoundItem> storeFoundItem(@Field("id") String obj_id, @Field("type") String Type, @Field("serial_number") String Serial
            , @Field("brand") String brand, @Field("color") String ObjectColor
            , @Field("description") String textArea_information);

    @FormUrlEncoded
    @POST("losts/foundperson/")
    Call<JsonObject> storeFoundPerson(String id, String datee, @Field("id") String obj_id, double longitude, double latitude, @Field("name") String name, MultipartBody.Part pimage);

    //----------------------------------------------------------------------------------------------

    @GET("losts/map/")
    Call<List<ObjectLocation>> getPoint();

    @GET("losts/founder/{id}")
    Call<UserMap> getUserMap(@Path("id") int id);

    @GET("losts/founditem")
    Call<List<FoundItem>> getFItem(@Query("type") String type);

    @GET("losts/lostitem/")
    Call<List<LostItem>> getLItem();

    @GET("losts/lostobject/")
    Call<List<LostItem>> getHomeLosts_obj(@Query("user_id") String id);

    @GET("losts/lostobject/{user_id}")
    Call<List<LostItem>> getHomeLosts_i(@Path("user_id") String id);

    @GET("losts/foundobject/{user_id}")
    Call<List<FoundItem>> getHomeFounds_i(@Path("user_id") String id);


}






















