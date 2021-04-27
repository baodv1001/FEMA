package com.example.fashionecommercemobileapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Billinfo {

    @SerializedName ("idBill")
    @Expose
    var idBill : Int? = null

    @SerializedName ("idProduct")
    @Expose
    var idProduct : Int? = null

    @SerializedName ("quantity")
    @Expose
    var quantity : Int? = null
}