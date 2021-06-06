package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BillInfo {

    @SerializedName("idBill")
    @Expose
    var idBill: Int? = null

    @SerializedName("idProduct")
    @Expose
    var idProduct: Int? = null

    @SerializedName("idSize")
    @Expose
    var idSize: Int? = null

    @SerializedName("idColor")
    @Expose
    var idColor: Int? = null

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null

    constructor(id: Int, idProduct: Int, idSize: Int, idColor: Int, quantity: Int) {
        this.idBill = id
        this.idProduct = idProduct
        this.idSize = idSize
        this.idColor = idColor
        this.quantity = quantity
    }
}