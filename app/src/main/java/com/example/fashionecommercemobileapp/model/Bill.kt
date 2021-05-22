package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Bill() {

    @SerializedName("idBill")
    @Expose
    private val idBill: Int? = null

    @SerializedName("idAccount")
    @Expose
    private val idAccount: Int? = null

    @SerializedName("invoiceDate")
    @Expose
    private val invoiceDate: Date? = null

    @SerializedName("status")
    @Expose
    private val status: String? = null

    @SerializedName("totalMoney")
    @Expose
    private val totalMoney: Int? = null

}