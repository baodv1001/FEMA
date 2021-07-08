package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.BillInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BillInfoApi {

    @POST("Bill/post_bill_info.php")
    @FormUrlEncoded
    suspend fun createBillInfo(
        @Field("idBill") idBill: Int?,
        @Field("idProduct") idProduct: Int?,
        @Field("idSize") idSize: Int?,
        @Field("idColor") idColor: Int?,
        @Field("quantity") quantity: Int?,
        @Field("price") price: String?
    ): Boolean

    @POST("Bill/get_bill_info.php")
    @FormUrlEncoded
    suspend fun getBillInfo(@Field("idBill") idBill: String?): List<BillInfo>
}