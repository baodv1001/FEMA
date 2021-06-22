package com.example.fashionecommercemobileapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.example.fashionecommercemobileapp.views.ProductDetailsActivity
import kotlinx.android.synthetic.main.wishlist_item.view.*

class WishlistAdapter(private val context: Context, private val wishList: MutableList<Product>, private var wishListViewModel: WishListViewModel?, private var id: String) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    private var productDetailsCodeRequest: Int = 0
    private  var newWishList = wishList
    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var nameItem : TextView = view.findViewById(R.id.nameWishItem)
        var infoItem : TextView = view.findViewById(R.id.infoWishlistItem)
        var costItem : TextView = view.findViewById(R.id.costWishItem)
        var imageItem: ImageView = view.findViewById(R.id.imageProduct)
        var idItem : TextView = view.findViewById(R.id.idProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameItem.text = newWishList[position].name
        holder.infoItem.text = newWishList[position].quantity
        holder.costItem.text = newWishList[position].price
        holder.idItem.text = newWishList[position].idProduct

        Glide.with(holder.itemView).load(newWishList[position].imageFile).into(holder.imageItem)

        holder.itemView.btnDelaWishProduct.setOnClickListener(View.OnClickListener{
            wishListViewModel!!.deleteWishProduct(id.toInt(), holder.idItem!!.text.toString().toInt())
            newWishList.removeAt(position)

            notifyItemRemoved(position)
            notifyDataSetChanged()
        })

        holder.itemView.setOnClickListener(View.OnClickListener{
            val i = Intent(context, ProductDetailsActivity::class.java)
            i.putExtra("id", newWishList[position].idProduct)
            i.putExtra("name", newWishList[position].name)
            i.putExtra("price", newWishList[position].price)
            i.putExtra("discount", newWishList[position].discount)
            i.putExtra("rating", newWishList[position].rating)
            i.putExtra("image", newWishList[position].imageFile)
            i.putExtra("position", position)
            i.putExtra("isLiked", true)
            i.putExtra("details", newWishList[position].description)
            //context.startActivity(i)
            (context as Activity).startActivityForResult(i,productDetailsCodeRequest)
        })
    }

    override fun getItemCount()= newWishList.size
}