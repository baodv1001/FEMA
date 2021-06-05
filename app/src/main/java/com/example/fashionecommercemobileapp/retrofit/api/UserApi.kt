package com.example.fashionecommercemobileapp.retrofit.api

import androidx.browser.customtabs.CustomTabsService
import com.example.fashionecommercemobileapp.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface UserApi {
    @FormUrlEncoded
    @POST("Profile/get_user.php")
    suspend fun getUser(@Field("idAccount") idAccount: String): List<User>

    @FormUrlEncoded
    @POST("Profile/update_User.php")
    fun updateUser(@Field("idAccount") idAccount: Int,
                    @Field("name") name: String,
                    @Field("gender") gender: String,
                    @Field("dateOfBirth") dateOfBirth: String): Call<String>
}