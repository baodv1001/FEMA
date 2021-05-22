package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Cart
import com.example.fashionecommercemobileapp.model.CartInfo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CartApi {

    @POST("Cart/get_cart_info.php")
    @FormUrlEncoded
    fun getCartInfo(@Field("idCart") idCart: Int): Call<List<CartInfo>>

    @POST("Cart/get_cart.php")
    @FormUrlEncoded
    fun getCart(@Field("idAccount") idAccount: Int): Call<Cart>

    @POST("Cart/update_cart.php")
    @FormUrlEncoded
    fun updateCart(
        @Field("idCart") idCart: Int,
        @Field("idAccount") idAccount: Int,
        @Field("isPaid") isPaid: Boolean
    ): Call<Boolean>

    @POST("Cart/update_cart_info.php")
    @FormUrlEncoded
    fun updateCartInfo(
        @Field("idCart") idCart: Int,
        @Field("idProduct") idProduct: Int,
        @Field("quantity") quantity: Int
    ): Call<Boolean>
}