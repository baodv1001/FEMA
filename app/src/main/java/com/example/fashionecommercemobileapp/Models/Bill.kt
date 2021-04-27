package com.example.fashionecommercemobileapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Bill {
    @SerializedName ("id")
    @Expose
    var id : Int? = null

    @SerializedName ("idAccount")
    @Expose
    var idAccount : Int? = null

    @SerializedName ("invoiceDate")
    @Expose
    var invoiceDate : Date? = null

    @SerializedName ("status")
    @Expose
    var status : String? = null

    @SerializedName ("totalMoney")
    @Expose
    var totalMoney : Int? = null
}