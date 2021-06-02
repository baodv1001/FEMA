package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Bill
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface BillApi {

    @POST("Bill/post_bill.php")
    @FormUrlEncoded
    suspend fun createBill(
        @Field("idAccount") idAccount: Int?,
        @Field("invoiceDate") invoiceDate: String?,
        @Field("status") status: String?,
        @Field("totalMoney") totalMoney: Int?
    ): Int

    @FormUrlEncoded
    @POST("get_invoice.php")
    fun getBillData(@Field("idAccount") idAccount : String) : Call<List<Bill>>
}