package com.example.fashionecommercemobileapp.retrofit

import com.example.fashionecommercemobileapp.Retrofit.api.UserApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    var retrofit: Retrofit? = null
    private val baseUrl =
        "http://laptop-0QNM76CK/fashionecommerceapp/"

    val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }
}