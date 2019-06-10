package com.example.nikeurbandictionary.api_client;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://data.cityofnewyork.us/resource/";
    private static Retrofit retrofit = null;

    /**
     * This method returns retrofit client instance
     *
     * @return Retrofit object
     */


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}