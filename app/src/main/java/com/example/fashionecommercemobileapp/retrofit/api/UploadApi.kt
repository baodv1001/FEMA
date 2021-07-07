package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.UploadResponse
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadApi {
    @Multipart
    @POST ("Api.php?apicall=upload")
    fun upLoadImage(
        @Part image: MultipartBody.Part,
        @Part ("desc") desc: RequestBody,
        @Part("idAccount") idAccount: Int
    ): Call<UploadResponse>

    companion object {
        operator fun invoke(): UploadApi {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .baseUrl("http://ghostlove/FEMA/Image/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(UploadApi::class.java)
        }
    }
}