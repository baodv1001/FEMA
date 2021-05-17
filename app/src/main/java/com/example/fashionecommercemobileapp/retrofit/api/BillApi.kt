package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Bill
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface BillApi {
    @FormUrlEncoded
    @POST("get_invoice.php")
    fun getBillData(@Field("idAccount") idAccount : String) : Call<List<Bill>>
}