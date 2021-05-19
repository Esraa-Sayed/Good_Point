package com.helloworld.goodpoint.retrofit;

import com.google.gson.JsonObject;
import com.helloworld.goodpoint.pojo.FoundItem;
import com.helloworld.goodpoint.pojo.FoundPerson;
import com.helloworld.goodpoint.pojo.LostItem;
import com.helloworld.goodpoint.pojo.LostPerson;
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

    @FormUrlEncoded
    @POST("losts/lostperson/")
    Call<JsonObject> storeLostPerson(@Field("id") String obj_id, @Field("name") String name);


    @Multipart
    @POST("losts/lostperson_image/")
    Call<LostPerson> storeLostPersonImage(@Part("id") String person_id/*, @Part MultipartBody.Part image*/);
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
    Call<JsonObject> storeFoundPerson(@Field("id") String obj_id, @Field("name") String name);

    @Multipart
    @POST("losts/foundperson_image/")
    Call<FoundPerson> storeFoundPersonImage(@Part("id") String person_id/*, @Part MultipartBody.Part image*/);

    //----------------------------------------------------------------------------------------------

    @GET("losts/map/")
    Call<List<ObjectLocation>> getPoint();

    @GET("losts/founder/")
    Call<UserMap> getUserMap(@Query("id") int id);



}






















