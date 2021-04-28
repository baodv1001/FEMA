package com.example.fashionecommercemobileapp.Retrofit.API

import com.example.fashionecommercemobileapp.Model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {

    @GET("product.php")
    fun getProduct(): Call<List<Product>>

    @GET("recommended.php")
    fun getRecommended(): Call<List<Product>>

    @GET("flash_sale.php")
    fun getFlashSale(): Call<List<Product>>

}