package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Size {
    @SerializedName("idSize")
    @Expose
    var idSize: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}


