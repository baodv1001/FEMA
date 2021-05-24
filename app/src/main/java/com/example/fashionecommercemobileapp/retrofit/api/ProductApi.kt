package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Product
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductApi {

    @POST("get_product.php")
    @FormUrlEncoded
    fun getProduct(@Field("idProductCode") idProductCode: String): Call<List<Product>>

    @GET("get_recommended.php")
    fun getRecommended(): Call<List<Product>>

    @GET("get_flash_sale.php")
    fun getFlashSale(): Call<List<Product>>

    @POST("get_product_by_id.php")
    @FormUrlEncoded
    suspend fun getProductById(@Field("idProductList") idProductList: String): List<Product>
}