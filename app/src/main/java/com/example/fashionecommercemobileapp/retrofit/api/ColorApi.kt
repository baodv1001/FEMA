package com.example.fashionecommercemobileapp.retrofit.api

import  retrofit2.Call
import com.example.fashionecommercemobileapp.model.Color
import retrofit2.http.GET

interface ColorApi {
    @GET("get_color.php")
    suspend fun getColor(): List<Color>
}