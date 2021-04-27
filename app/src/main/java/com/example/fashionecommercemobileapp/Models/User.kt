package com.example.fashionecommercemobileapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class User {
    @SerializedName("id")
    @Expose
    var id : Int? = null

    @SerializedName ("name")
    @Expose
    var name : String? = null

    @SerializedName ("gender")
    @Expose
    var gender : String? = null

    @SerializedName ("phoneNumber")
    @Expose
    var phoneNumber : String? = null

    @SerializedName ("address")
    @Expose
    var address : String? = null

    @SerializedName ("dateOfBirth")
    @Expose
    var dateOfBirth : Date? = null

    @SerializedName ("idAccount")
    @Expose
    var idAccount : Int? = null

    @SerializedName("imageURL")
    @Expose
    var imageURL : String? = null

    @SerializedName("idNumber")
    @Expose
    var idNumber : String? = null
}