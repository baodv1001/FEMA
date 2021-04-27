package com.example.fashionecommercemobileapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {
    @SerializedName ("id")
    @Expose
    var id : Int? = null

    @SerializedName ("idProductCode")
    @Expose
    var idProductCode : String? = null

    @SerializedName ("name")
    @Expose
    var name : String? = null

    @SerializedName ("price")
    @Expose
    var price : Int? = null

    @SerializedName ("quantity")
    @Expose
    var quantity : Int? = null

    @SerializedName ("rating")
    @Expose
    var rating : Int? = null

    @SerializedName ("imageURL")
    @Expose
    var imageURL : String? = null

    @SerializedName ("discount")
    @Expose
    var discount : Int? = null
}