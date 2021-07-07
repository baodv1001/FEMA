package com.example.fashionecommercemobileapp.retrofit

import com.example.fashionecommercemobileapp.retrofit.api.AccountApi
import com.example.fashionecommercemobileapp.retrofit.api.AddressApi
import com.example.fashionecommercemobileapp.retrofit.api.UploadApi
import com.example.fashionecommercemobileapp.retrofit.api.CartApi
import com.example.fashionecommercemobileapp.retrofit.api.ProductApi
import com.example.fashionecommercemobileapp.retrofit.api.UserApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitClient {

    var retrofit: Retrofit? = null
    private val baseUrl =
        "http://192.168.224.188/FEMA/"

    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit
    }

    val accountApi: AccountApi = getRetrofitInstance()!!.create(AccountApi::class.java)
    val userApi: UserApi = getRetrofitInstance()!!.create(UserApi::class.java)
    val addressApi: AddressApi = getRetrofitInstance()!!.create(AddressApi::class.java)
    val uploadApi: UploadApi = getRetrofitInstance()!!.create(UploadApi::class.java)
}

