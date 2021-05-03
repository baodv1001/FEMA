package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryApi {

    @GET("get_category.php")
    fun getCategory(): Call<List<Category>>

    @GET("get_swipe_image.php")
    fun getSwipeImage(): Call<List<String>>
}