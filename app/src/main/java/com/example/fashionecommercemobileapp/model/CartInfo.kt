package com.example.fashionecommercemobileapp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class CartInfo() : Parcelable {

    @SerializedName("idCart")
    @Expose
    var idCart: Int? = null

    @SerializedName("idProduct")
    @Expose
    var idProduct: Int? = null

    @SerializedName("idSize")
    @Expose
    var idSize: Int? = null

    @SerializedName("idColor")
    @Expose
    var idColor: Int? = null

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null

}