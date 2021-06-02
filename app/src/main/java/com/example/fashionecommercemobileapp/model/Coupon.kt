package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Coupon() {

    @SerializedName("idCoupon")
    @Expose
    var idCoupon: Int? = null

    @SerializedName("code")
    @Expose
    var code: String? = null

    @SerializedName("value")
    @Expose
    var value: Int? = null
}