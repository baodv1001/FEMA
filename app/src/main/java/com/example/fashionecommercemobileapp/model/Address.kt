
package com.example.fashionecommercemobileapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Address {
    @SerializedName ("idAccount")
    @Expose
    var idAccount : Int? = null

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