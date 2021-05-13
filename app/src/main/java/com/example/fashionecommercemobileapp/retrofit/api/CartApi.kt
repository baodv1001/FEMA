package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.CartInfo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CartApi {

    @POST("get_cart_info.php")
    @FormUrlEncoded
    fun getCartInfo(@Field("idCart") idCart: Int): Call<List<CartInfo>>

}