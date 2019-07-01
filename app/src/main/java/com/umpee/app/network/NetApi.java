package com.umpee.app.network;


import com.umpee.app.AppConfig;
import com.umpee.app.utils.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetApi {
    private static Retrofit mRetrofit = null;
    private static Interface mInterface = null;

    private static HashMap<String, InterfaceUmpire> interfaceUmpireHashMap = new HashMap<>();

    public static Interface getInterface() {
        if (mInterface == null) {
            mRetrofit = getClient();
            mInterface = mRetrofit.create(Interface.class);
        }
        return mInterface;
    }

    public static Retrofit getClient() {
        if (mRetrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(AppConfig.NET_LOG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).readTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS).build();

            mRetrofit = new Retrofit.Builder().baseUrl(AppConfig.NET_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return mRetrofit;
    }

    public static Retrofit getClient(String baseUrl) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(AppConfig.NET_LOG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).readTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS).build();

        return new Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static InterfaceUmpire getInterface(String url) {
        if (!interfaceUmpireHashMap.containsKey(url)) {
            InterfaceUmpire it = getClient(url).create(InterfaceUmpire.class);
            interfaceUmpireHashMap.put(url, it);
            return it;
        } else {
            return interfaceUmpireHashMap.get(url);
        }
    }
}
