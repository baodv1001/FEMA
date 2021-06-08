package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Bill
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BillApi {

    @POST("Bill/post_bill.php")
    @FormUrlEncoded
    suspend fun createBill(
        @Field("idAddress") idAddress: Int?,
        @Field("idAccount") idAccount: Int?,
        @Field("invoiceDate") invoiceDate: String?,
        @Field("status") status: Int?,
        @Field("totalMoney") totalMoney: Int?
    ): Int

    @FormUrlEncoded
    @POST("get_invoice.php")
    fun getBillData(@Field("idAccount") idAccount: String): Call<List<Bill>>

    @POST("Bill/update_bill.php")
    @FormUrlEncoded
    fun updateBill(@Field("idBill") idBill: String): Call<Boolean>

    @POST("Bill/update_bill_rated.php")
    @FormUrlEncoded
    fun updateBillRated(@Field("idBill") idBill: String): Call<Boolean>
}