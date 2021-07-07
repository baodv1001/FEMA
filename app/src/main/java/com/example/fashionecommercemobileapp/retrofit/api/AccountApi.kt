package com.example.fashionecommercemobileapp.retrofit.api

import  retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.example.fashionecommercemobileapp.model.Account

interface AccountApi {
    @FormUrlEncoded
    @POST("Account/post_checkAccount.php")
    fun checkAccount(@Field("username") username: String,
                      @Field("phoneNumber") phoneNumber: String) : Call<String>

    @FormUrlEncoded
    @POST("Account/post_updatePassword.php")
    fun updatePassword(@Field("username") username: String,
                   @Field("password") password: String?,
                   @Field("phoneNumber") phoneNumber: String?) : Call<String>

    @FormUrlEncoded
    @POST("Account/post_signup.php")
    fun signUp(@Field("username") username: String,
               @Field("password") password: String,
                @Field("name") name: String,
               @Field("phoneNumber") phone: String?) : Call<String>

    @FormUrlEncoded
    @POST("Account/login.php")
    suspend fun getAccount(@Field("username") username : String,
                           @Field("password") password : String) : List<Account>

    @FormUrlEncoded
    @POST("Account/change_password.php")
    fun changePassword(@Field("idAccount") idAccount : Int,
                        @Field("newPassword")  newPassword : String): Call<String>
}