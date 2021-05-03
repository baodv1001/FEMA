package com.example.fashionecommercemobileapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class RetrofitClient {

    var retrofit: Retrofit? = null
    private val BASE_URL =
        "http://LAPTOP-PHDK779R/FEMA/"

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}