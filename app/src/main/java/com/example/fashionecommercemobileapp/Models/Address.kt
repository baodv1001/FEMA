
package com.example.fashionecommercemobileapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Address {
    @SerializedName ("idUser")
    @Expose
    var idUser : Int? = null

    @SerializedName ("name")
    @Expose
    var name : String? = null

    @SerializedName ("address")
    @Expose
    var address : String? = null

    @SerializedName ("phoneNumber")
    @Expose
    var phoneNumber : String? = null
}