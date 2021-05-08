package com.example.fashionecommercemobileapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.Account
import kotlinx.android.synthetic.main.cart_item.view.*



class CartAdapter(context: Context, private val listCart: List<Account>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var nameCart : TextView = view.findViewById(R.id.nameCartItem)
        var infoCart : TextView = view.findViewById(R.id.infoWishlistItem)
        var costCart : TextView = view.findViewById(R.id.costCartItem)
        var imageCart : ImageView = view.findViewById(R.id.imageProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameCart.text = listCart[position].id.toString()
        holder.infoCart.text = listCart[position].username
        holder.costCart.text = listCart[position].password


        holder.itemView.btnIncrease.setOnClickListener {
            var count : Int = holder.itemView.countProduct.text.toString().toInt()
            count +=1
            holder.itemView.countProduct.setText(count.toString())
        }

        holder.itemView.btnDecrease.setOnClickListener {
            var count : Int = holder.itemView.countProduct.text.toString().toInt()
            if (count > 1)
            {
                count -= 1
                holder.itemView.countProduct.setText(count.toString())
            }
        }
    }

    override fun getItemCount()= listCart.size
}