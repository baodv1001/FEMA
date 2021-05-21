package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Address
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AddressApi {
    @FormUrlEncoded
    @POST("get_addressinfo.php")
    fun getAddressInfoData(@Field("idAccount") idAccount: String) : Call<List<Address>>
}