package com.example.fashionecommercemobileapp.Retrofit.api

import com.example.fashionecommercemobileapp.model.Account
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountApi {
    @FormUrlEncoded
    @POST("login.php")
    fun getAccount(@Field("username") username : String, @Field("password") password : String) : Call<List<Account>>
}