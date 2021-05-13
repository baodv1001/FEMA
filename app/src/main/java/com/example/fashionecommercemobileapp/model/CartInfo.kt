package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CartInfo() {

    @SerializedName("idCart")
    @Expose
    var idCart: Int? = null

    @SerializedName("idProduct")
    @Expose
    var idProduct: Int? = null

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null

}