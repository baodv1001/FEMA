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
import com.example.fashionecommercemobileapp.model.BillInfo
import com.example.fashionecommercemobileapp.model.Color
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.model.Size
import java.text.NumberFormat
import java.util.*


class OrderDetailsAdapter(
    private val context: Context,
    private val listBillInfo: ArrayList<BillInfo>,
    private val listProduct: ArrayList<Product>,
    private val listSize: ArrayList<Size>,
    private val listColor: ArrayList<Color>,
    private val lang: String
) :
    RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageCart: ImageView = view.findViewById(R.id.imageView_product_checkOut)
        var productCart: TextView = view.findViewById(R.id.textView_product_checkOut)
        var infoCart: TextView = view.findViewById(R.id.textView_info_checkOut)
        var costCart: TextView = view.findViewById(R.id.textView_cost_checkOut)
        var quantity: TextView = view.findViewById(R.id.textView_quantity_checkOut)
    }

    fun changeData(
        cartInfoList: List<BillInfo>,
        productList: List<Product>,
        sizeList: List<Size>,
        colorList: List<Color>
    ) {
        this.listBillInfo.apply {
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
        if (listBillInfo.isEmpty() || listProduct.isEmpty()) {
            return
        }
        Glide.with(context)
            .load(listProduct[position].imageFile)
            .fitCenter()
            .into(holder.imageCart)
        var sizeName: String = ""
        for (size in listSize) {
            if (size.idSize == listBillInfo[position].idSize.toString()) {
                sizeName = size.name.toString()
            }
        }
        var colorName: String = ""
        for (color in listColor) {
            if (color.idColor == listBillInfo[position].idColor.toString()) {
                colorName = color.name.toString()
            }
        }
        if (lang == "en")
        {
            holder.infoCart.text = "Size: $sizeName, Color: $colorName"
            holder.quantity.text = "Quantity: " + listBillInfo[position].quantity.toString()
        }
        else
        {
            holder.infoCart.text = "Cỡ: $sizeName, Màu: $colorName"
            holder.quantity.text = "Số lượng: " + listBillInfo[position].quantity.toString()
        }
        holder.productCart.text = listProduct[position].name
        val price = listBillInfo[position].price?.toInt()
        holder.costCart.text =
            NumberFormat.getIntegerInstance(Locale.GERMANY).format(price) + " $"

    }

    override fun getItemCount(): Int {
        return listBillInfo.size
    }
}