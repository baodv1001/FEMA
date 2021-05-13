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
import kotlinx.android.synthetic.main.cart_item.view.*


class CartAdapter(
    private val context: Context,
    private val listCart: List<CartInfo>,
    private val listProduct: List<Product>
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageCart: ImageView = view.findViewById(R.id.imageview_product_cart)
        var productCart: TextView = view.findViewById(R.id.textview_product_cart)
        var infoCart: TextView = view.findViewById(R.id.textview_info_cart)
        var costCart: TextView = view.findViewById(R.id.textview_cost_cart)
        var quantityCart: TextView = view.findViewById(R.id.edittext_quantity_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (listCart.isEmpty() || listProduct.isEmpty())
//            return
        Glide.with(context)
            .load("https://via.placeholder.com/80.jpeg")
            .fitCenter()
            .into(holder.imageCart)
//        holder.productCart.text = listProduct[position].name
//        holder.infoCart.text = listProduct[position].unit
//        holder.costCart.text = ((listProduct[position].price?.toInt() ?: 1) * listCart[position].quantity!!).toString()

        holder.productCart.text = listCart[position].idProduct.toString()
        holder.infoCart.text = listCart[position].idCart.toString()
        holder.costCart.text = listCart[position].idCart.toString()
        holder.quantityCart.text = listCart[position].quantity.toString()

        holder.itemView.button_increase_cart.setOnClickListener {
            var count: Int = holder.itemView.edittext_quantity_cart.text.toString().toInt()
            count += 1
            holder.itemView.edittext_quantity_cart.setText(count.toString())
        }

        holder.itemView.button_decrease_cart.setOnClickListener {
            var count: Int = holder.itemView.edittext_quantity_cart.text.toString().toInt()
            if (count > 1) {
                count -= 1
                holder.itemView.edittext_quantity_cart.setText(count.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return listCart.size
    }
}