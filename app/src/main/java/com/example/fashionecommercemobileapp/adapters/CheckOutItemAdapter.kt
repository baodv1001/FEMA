package com.example.fashionecommercemobileapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.model.Color
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.model.Size
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class CheckOutItemAdapter(
    private val context: Context,
    private val listCartInfo: ArrayList<CartInfo>,
    private val listProduct: ArrayList<Product>,
    private val listSize: ArrayList<Size>,
    private val listColor: ArrayList<Color>,
    private val lang: String
) :
    RecyclerView.Adapter<CheckOutItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageCart: ImageView = view.findViewById(R.id.imageView_product_checkOut)
        var productCart: TextView = view.findViewById(R.id.textView_product_checkOut)
        var infoCart: TextView = view.findViewById(R.id.textView_info_checkOut)
        var costCart: TextView = view.findViewById(R.id.textView_cost_checkOut)
        var quantity: TextView = view.findViewById(R.id.textView_quantity_checkOut)
    }

    fun changeData(cartInfoList: List<CartInfo>, productList: List<Product>, sizeList: List<Size>, colorList: List<Color>) {
        this.listCartInfo.apply {
            clear()
            addAll(cartInfoList)
        }
        this.listProduct.apply {
            clear()
            addAll(productList)
        }
        this.listSize.apply {
            clear()
            addAll(sizeList)
        }
        this.listColor.apply {
            clear()
            addAll(colorList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.check_out_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listCartInfo.isEmpty() || listProduct.isEmpty()) {
            return
        }
        Glide.with(context)
            .load(listProduct[position].imageFile)
            .fitCenter()
            .into(holder.imageCart)
        var sizeName: String = ""
        for (size in listSize) {
            if (size.idSize == listCartInfo[position].idSize.toString()) {
                sizeName = size.name.toString()
            }
        }
        var colorName: String = ""
        for (color in listColor) {
            if (color.idColor == listCartInfo[position].idColor.toString()) {
                colorName = color.name.toString()
            }
        }
        if (lang == "en")
        {
            holder.infoCart.text = "Size: $sizeName, Color: $colorName"
            holder.quantity.text = "Quantity: " + listCartInfo[position].quantity.toString()
        }
        else
        {
            holder.infoCart.text = "Cỡ: $sizeName, Màu: $colorName"
            holder.quantity.text = "Số lượng: " + listCartInfo[position].quantity.toString()
        }
        holder.productCart.text = listProduct[position].name
        val discount: Float = 1 - (listProduct[position].discount?.toFloat() ?: 0F)
        val price: Float = (listProduct[position].price?.toFloat() ?: 0F)
        holder.costCart.text =
            (Math.round((discount * price!!.toFloat())*100)/100.0f).toString() + " $"

    }

    override fun getItemCount(): Int {
        return listCartInfo.size
    }
}