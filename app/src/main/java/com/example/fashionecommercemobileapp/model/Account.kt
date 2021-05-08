package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Account {

    @SerializedName("idAccount")
    @Expose
    var id: Int? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("type")
    @Expose
    var type: Int? = null

    @SerializedName("isDeleted")
    @Expose
    var isDeleted: Int? = null

}