package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Bill
import com.example.fashionecommercemobileapp.model.BillInfo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface BillInfoApi {

    @POST("Bill/post_bill_info.php")
    @FormUrlEncoded
    suspend fun createBillInfo(
        @Field("idBill") idBill: Int?,
        @Field("idProduct") idProduct: Int?,
        @Field("quantity") quantity: Int?
    ): Boolean
}