package com.example.pizzeria.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
//    TODO: zmiana IP
    private static final String BASE_URL = "http://192.168.0.129:5000";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Dodaj logger
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Dodaj logger do klienta HTTP
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor) // Interceptor dla logowania
                    .build();

            // Twórz instancję Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // Dodaj klienta z loggerem
                    .addConverterFactory(GsonConverterFactory.create()) // Parsowanie JSON
                    .build();
        }
        return retrofit;
    }
}
