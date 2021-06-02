package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Bill {
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
    var status: String? = null

    @SerializedName("totalMoney")
    @Expose
    var totalMoney: Int? = null

    constructor(id: Int, idAccount: Int, invoiceDate: String, status: String, totalMoney: Int) {
        this.id = id
        this.idAccount = idAccount
        this.invoiceDate = invoiceDate
        this.status = status
        this.totalMoney = totalMoney
    }
}