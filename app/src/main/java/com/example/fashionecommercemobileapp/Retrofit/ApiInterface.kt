package com.example.fashionecommercemobileapp.Retrofit

import com.example.fashionecommercemobileapp.Models.Example
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("displayAddressInfo.php")
    open fun getAllData(): Call<List<Example>>

    @FormUrlEncoded
    @POST("insert.php")
    fun getStringScalar(@Field("name") name : String, @Field("id") id:String) : Call<String>
}