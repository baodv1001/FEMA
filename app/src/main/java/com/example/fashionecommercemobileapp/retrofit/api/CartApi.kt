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
    suspend fun getCartInfo(@Field("idCart") idCart: Int): List<CartInfo>

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

    @POST("Cart/update_cart_info.php")
    @FormUrlEncoded
    fun updateCartInfo(
        @Field("idCart") idCart: Int,
        @Field("idProduct") idProduct: Int,
        @Field("quantity") quantity: Int
    ): Call<Boolean>

    @POST("Cart/post_cart_info.php")
    @FormUrlEncoded
    fun postCartInfo(
        @Field("idCart") idCart: Int,
        @Field("idProduct") idProduct: Int,
        @Field("quantity") quantity: Int
    ): Call<Boolean>

    @POST("Cart/delete_cart_info.php")
    @FormUrlEncoded
    fun deleteCartInfo(
        @Field("idCart") idCart: Int,
        @Field("idProduct") idProduct: Int
    ): Call<Boolean>

    @POST("Cart/get_cart_info_product.php")
    @FormUrlEncoded
    suspend fun getCartInfo(
        @Field("idCart") idCart: Int,
        @Field("idProduct") idProduct: Int
    ): CartInfo
}