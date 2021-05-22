package com.example.fashionecommercemobileapp.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class RetrofitClient {

    var retrofit: Retrofit? = null
    private val BASE_URL = "http://192.168.43.206:8080/FEMA/"

    val gson = GsonBuilder()
        .setLenient()
        .create()

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }
}