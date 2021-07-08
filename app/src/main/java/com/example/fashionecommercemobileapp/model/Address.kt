package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Address {
    @SerializedName("idAddress")
    @Expose
    var idAddress: Int? = null

    @SerializedName("idAccount")
    @Expose
    var idAccount: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String? = null

    constructor(idAddress: Int?, idAcc: Int?, name: String?, add: String?, phoneNum: String?) {
        this.idAddress = idAddress
        this.idAccount = idAcc
        this.name = name
        this.address = add
        this.phoneNumber = phoneNum
    }
}