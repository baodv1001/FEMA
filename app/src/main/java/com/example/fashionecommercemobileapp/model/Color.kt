package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Color {
    @SerializedName("idColor")
    @Expose
    var idColor: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}


