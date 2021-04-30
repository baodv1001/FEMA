package com.example.fashionecommercemobileapp.Retrofit.API

import com.example.fashionecommercemobileapp.Model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryApi {

    @GET("get_category.php")
    fun getCategory(): Call<List<Category>>

    @GET("get_swipe_image.php")
    fun getSwipeImage(): Call<List<String>>
}