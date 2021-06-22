package com.example.fashionecommercemobileapp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product() : Parcelable {
    @SerializedName("idProduct")
    @Expose
    var idProduct: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("idProductCode")
    @Expose
    var idProductCode: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null

    @SerializedName("quantity")
    @Expose
    var quantity: String? = null

    @SerializedName("unit")
    @Expose
    var unit: String? = null

    @SerializedName("imageFile")
    @Expose
    var imageFile: String? = null

    @SerializedName("discount")
    @Expose
    var discount: String? = null

    @SerializedName("rating")
    @Expose
    var rating: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("isDeleted")
    @Expose
    var isDeleted: String? = null

    constructor(
        id: String?,
        name: String?,
        code: String?,
        price: String?,
        quantity: String?,
        unit: String?,
        img: String?,
        discount: String?,
        rate: String?,
        description: String?,
        isDeleted: String?
    ) : this() {
        this.idProduct = id
        this.name = name
        this.idProductCode = code
        this.price = price
        this.quantity = quantity
        this.unit = unit
        this.imageFile = img
        this.discount = discount
        this.rating = rate
        this.description = description
        this.isDeleted = isDeleted
    }
}