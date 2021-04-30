package com.example.fashionecommercemobileapp.Retrofit.API

import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.Model.Product
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductApi {

    @POST("get_product.php")
    @FormUrlEncoded
    fun getProduct(@Field("idProductCode") idProductCode:String): Call<List<Product>>

    @GET("get_recommended.php")
    fun getRecommended(): Call<List<Product>>

    @GET("get_flash_sale.php")
    fun getFlashSale(): Call<List<Product>>
}