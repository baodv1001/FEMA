package com.example.fashionecommercemobileapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Example {
    @SerializedName("addressinfo")
    @Expose
    var addressinfo: List<Address>? = null

    @SerializedName("popular")
    @Expose
    var popular: List<Popular>? = null

    @SerializedName("recommended")
    @Expose
    var recommended: List<Recommended>? = null

    @SerializedName("allmenu")
    @Expose
    var allmenu: List<Allmenu>? = null
}