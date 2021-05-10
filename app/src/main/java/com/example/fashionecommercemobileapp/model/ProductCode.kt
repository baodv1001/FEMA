package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductCode {
    @SerializedName ("id")
    @Expose
    var id : Int? = null

    @SerializedName ("name")
    @Expose
    var name : String? = null
}