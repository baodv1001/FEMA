package com.example.fashionecommercemobileapp.retrofit.api

import  retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountAPI {
    @FormUrlEncoded
    @POST("post_checkAccount.php")
    fun checkAccount(@Field("username") username: String,
                      @Field("phoneNumber") phoneNumber: String) : Call<String>

    @FormUrlEncoded
    @POST("post_updatePassword.php")
    fun updatePassword(@Field("username") username: String,
                   @Field("password") password: String?,
                   @Field("phoneNumber") phoneNumber: String?) : Call<String>

    @FormUrlEncoded
    @POST("post_signup.php")
    fun signUp(@Field("username") username: String,
               @Field("password") password: String,
                @Field("name") name: String,
               @Field("phoneNumber") phone: String?) : Call<String>
}