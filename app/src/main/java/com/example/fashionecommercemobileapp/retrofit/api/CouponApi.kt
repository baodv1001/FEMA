package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Coupon
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CouponApi {

    @POST("Coupon/get_coupon.php")
    @FormUrlEncoded
    suspend fun getCoupon(@Field("code") code: String): Coupon
}