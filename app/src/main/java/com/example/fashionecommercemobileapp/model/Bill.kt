package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Bill() {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("idAccount")
    @Expose
    var idAccount: Int? = null

    @SerializedName("invoiceDate")
    @Expose
    var invoiceDate: String? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("idAddress")
    @Expose
    var idAddress: Int? = null

    @SerializedName("totalMoney")
    @Expose
    var totalMoney: Float? = null

    @SerializedName("isRated")
    @Expose
    var isRated: Int? = null

    constructor(
        id: Int?,
        idAccount: Int?,
        invoiceDate: String?,
        status: Int?,
        idAddress: Int?,
        totalMoney: Float?,
        rated: Int?
    ) : this() {
        this.id = id
        this.idAccount = idAccount
        this.invoiceDate = invoiceDate
        this.status = status
        this.idAddress = idAddress
        this.totalMoney = totalMoney
        this.isRated = rated
    }
}