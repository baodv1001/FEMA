package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Address
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AddressApi {
    @FormUrlEncoded
    @POST("AddressInfo/get_addressinfo.php")
    fun getAddressInfoData(@Field("idAccount") idAccount: String) : Call<List<Address>>

    @FormUrlEncoded
    @POST("AddressInfo/add_address_info.php")
    fun addAddressInfo(@Field("idAccount") idAccount: Int,
                        @Field("name") name: String,
                        @Field("address") address: String,
                        @Field("phoneNumber") phoneNumber: String) : Call<String>

    @FormUrlEncoded
    @POST("AddressInfo/delete_address_info.php")
    fun delAddressInfo(@Field("idAccount") idAccount: Int,
                       @Field("name") name: String,
                       @Field("address") address: String,
                       @Field("phoneNumber") phoneNumber: String) : Call<String>
    @FormUrlEncoded
    @POST("AddressInfo/update_address_info.php")
    fun updateAddressInfo (@Field("idAddress") idAddress: Int,
                           @Field("name") name: String,
                           @Field("address") address: String,
                           @Field("phoneNumber") phoneNumber: String) : Call<String>
}