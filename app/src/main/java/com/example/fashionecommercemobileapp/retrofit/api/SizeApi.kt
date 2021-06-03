package com.example.fashionecommercemobileapp.retrofit.api

import  retrofit2.Call
import com.example.fashionecommercemobileapp.model.Size
import retrofit2.http.GET

interface SizeApi {
    @GET("get_size.php")
    suspend fun getSize(): List<Size>
}