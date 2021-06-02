package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class BillInfo {

    @SerializedName("idBill")
    @Expose
    var idBill: Int? = null

    @SerializedName("idProduct")
    @Expose
    var idProduct: Int? = null

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null

    constructor(id: Int, idProduct: Int, quantity: Int) {
        this.idBill = id
        this.idProduct = idProduct
        this.quantity = quantity
    }
}