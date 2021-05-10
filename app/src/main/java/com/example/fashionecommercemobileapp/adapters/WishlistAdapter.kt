package com.example.fashionecommercemobileapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.viewmodels.WishListViewModel
import kotlinx.android.synthetic.main.wishlist_item.view.*


class WishlistAdapter(context: Context, private val wishList: MutableList<Product>, private var wishListViewModel: WishListViewModel?) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var nameItem : TextView = view.findViewById(R.id.nameCartItem)
        var infoItem : TextView = view.findViewById(R.id.infoWishlistItem)
        var costItem : TextView = view.findViewById(R.id.costCartItem)
        var imageItem: ImageView = view.findViewById(R.id.imageProduct)
        var idItem : TextView = view.findViewById(R.id.idProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameItem.text = wishList[position].name
        holder.infoItem.text = wishList[position].quantity
        holder.costItem.text = wishList[position].price
        holder.idItem.text = wishList[position].idProduct

        Glide.with(holder.itemView).load(wishList[position].imageFile).into(holder.imageItem)

        holder.itemView.btnDelaWishProduct.setOnClickListener(View.OnClickListener{
            wishListViewModel!!.deleteWishProduct(32, holder.idItem!!.text.toString().toInt())
            wishList.removeAt(position)

            notifyItemRemoved(position)
        })
    }

    override fun getItemCount()= wishList.size
}