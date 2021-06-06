package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Category
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.model.ProductCode
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryApi {

    @GET("get_category.php")
    fun getCategory(): Call<List<Category>>

    @GET("get_swipe_image.php")
    fun getSwipeImage(): Call<List<String>>

    @POST("get_productcode_by_name.php")
    @FormUrlEncoded
    fun getProductCodeByName(@Field("name") name:String): Call<List<Category>>
}