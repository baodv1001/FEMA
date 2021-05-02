package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("idProductCode")
    @Expose
    var idProductCode: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null


}