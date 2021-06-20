package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Cart() {

    @SerializedName("idCart")
    @Expose
    var idCart: Int? = null

    @SerializedName("idAccount")
    @Expose
    var idAccount: Int? = null

    @SerializedName("isPaid")
    @Expose
    var isPaid: Boolean? = null

}