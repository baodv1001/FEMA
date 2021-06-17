package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Cart
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CartApi {

    @POST("Cart/get_cart.php")
    @FormUrlEncoded
    suspend fun getCart(@Field("idAccount") idAccount: Int): Cart

    @POST("Cart/update_cart.php")
    @FormUrlEncoded
    fun updateCart(
        @Field("idCart") idCart: Int,
        @Field("idAccount") idAccount: Int,
        @Field("isPaid") isPaid: Boolean
    ): Call<Boolean>
}