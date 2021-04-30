package com.helloworld.goodpoint.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //base url
    private static final String BASE_URL = "https://a0a1b50b54fd.ngrok.io";
    private static Retrofit retrofit = null;


    //Create Builder ...
    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
