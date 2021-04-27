package com.example.fashionecommercemobileapp.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Popular {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String? = null

    @SerializedName("rating")
    @Expose
    var rating: String? = null

    @SerializedName("deliveryTime")
    @Expose
    var deliveryTime: String? = null

    @SerializedName("deliveryCharges")
    @Expose
    var deliveryCharges: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null

    @SerializedName("note")
    @Expose
    var note: String? = null
}