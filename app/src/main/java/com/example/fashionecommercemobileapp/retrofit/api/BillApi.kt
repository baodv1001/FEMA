package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Cart
import com.example.fashionecommercemobileapp.model.CartInfo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface BillApi {

    @POST("Bill/post_bill.php")
    @FormUrlEncoded
    fun createBill(
        @Field("idBill") idBill: Int,
        @Field("idAccount") idAccount: Int,
        @Field("invoiceDate") invoiceDate: Date,
        @Field("status") status: String,
        @Field("totalMoney") totalMoney: String
    ): Call<Boolean>

    @POST("Bill/post_bill_info.php")
    @FormUrlEncoded
    fun createBillInfo(
        @Field("idBill") idBill: Int,
        @Field("idProduct") idProduct: Int,
        @Field("quantity") quantity: Int
    ): Call<Boolean>
}