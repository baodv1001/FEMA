package com.example.fashionecommercemobileapp.adapters

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
import com.example.fashionecommercemobileapp.model.Product


class CheckOutItemAdapter(
    private val context: Context,
    private val listCartInfo: ArrayList<CartInfo>,
    private val listProduct: ArrayList<Product>
) :
    RecyclerView.Adapter<CheckOutItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageCart: ImageView = view.findViewById(R.id.imageView_product_checkOut)
        var productCart: TextView = view.findViewById(R.id.textView_product_checkOut)
        var infoCart: TextView = view.findViewById(R.id.textView_info_checkOut)
        var costCart: TextView = view.findViewById(R.id.textView_cost_checkOut)
    }

    fun changeData(cartInfoList: List<CartInfo>, productList: List<Product>) {
        this.listCartInfo.apply {
            clear()
            addAll(cartInfoList)
        }
        this.listProduct.apply {
            clear()
            addAll(productList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.check_out_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listCartInfo.isEmpty() || listProduct.isEmpty()) {
            return
        }
        Glide.with(context)
            .load(listProduct[position].imageFile)
            .fitCenter()
            .into(holder.imageCart)

        holder.productCart.text = listProduct[position].name
        holder.infoCart.text = listProduct[position].unit
        holder.costCart.text = listProduct[position].price
    }

    override fun getItemCount(): Int {
        return listCartInfo.size
    }
}