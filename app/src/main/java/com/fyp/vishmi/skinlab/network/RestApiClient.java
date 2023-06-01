package com.fyp.vishmi.skinlab.network;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiClient {

    private Retrofit mClient;
    OkHttpClient client;
    Context context;

    public RestApiClient(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        RequestInterceptor interceptor = new RequestInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        mClient = new Retrofit.Builder()
                .baseUrl("http://34.124.204.173/api/v0.1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    public UserAPI buildApi (){
        return mClient.create(UserAPI.class);
    }

    public Retrofit retrofit() { return mClient; }

    public OkHttpClient okHttpClient(){ return  client;}
}
