package com.example.fashionecommercemobileapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.views.ProductDetailsActivity
import kotlinx.android.synthetic.main.flash_sale_recycler_item.view.*


class FlashSaleAdapter(
    private val context: Context,
    flashSaleList: List<Product>,
    wishList: List<Product>
) : RecyclerView.Adapter<FlashSaleAdapter.FlashSaleViewHolder>() {
    private val requestCode: Int = 1
    private val wishList: List<Product> = wishList
    private val flashSaleList: List<Product> = flashSaleList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashSaleViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.flash_sale_recycler_item, parent, false)
        // here we need to create a layout for recyclerview cell items.
        return FlashSaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashSaleViewHolder, position: Int) {
        holder.flashSaleName.text = flashSaleList[position].name
        holder.flashSaleSalePrice.text =
                ((1 - flashSaleList[position].discount!!.toFloat()) * flashSaleList[position].price!!.toFloat()).toString()
        holder.flashSalePrice.text = flashSaleList[position].price
        Glide.with(context).load(flashSaleList[position].imageFile).into(holder.flashSaleImage)
        if (position == flashSaleList.size - 1) {
            val params: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                (120 * Resources.getSystem().displayMetrics.density).toInt(),
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 0)
            holder.item.layoutParams = params
        }

        var isLiked: Boolean = false
        if (wishList.isNotEmpty())
        {
            if (flashSaleList[position].idProduct?.toInt()!! >= wishList[0].idProduct?.toInt()!!
                    && flashSaleList[position].idProduct?.toInt()!! <= wishList[wishList.size-1].idProduct?.toInt()!!)
            {
                for (wishItem in wishList) {
                    if (wishItem.idProduct == flashSaleList[position].idProduct) {
                        Glide.with(context).load(R.drawable.ic_heartbutton).into(holder.itemView.button)
                        isLiked = true
                        break
                    }
                }
            }
        }
        holder.itemView.setOnClickListener {
            val i = Intent(context, ProductDetailsActivity::class.java)
            i.putExtra("idProduct", flashSaleList[position].idProduct)
            i.putExtra("quantity", flashSaleList[position].quantity)
            i.putExtra("id", flashSaleList[position].idProduct)
            i.putExtra("name", flashSaleList[position].name)
            i.putExtra("price", flashSaleList[position].price)
            i.putExtra("discount", flashSaleList[position].discount)
            i.putExtra("rating", flashSaleList[position].rating)
            i.putExtra("image", flashSaleList[position].imageFile)
            i.putExtra("position", position)
            i.putExtra("isLiked", isLiked)
            //context.startActivity(i)
            (context as Activity).startActivityForResult(i, requestCode)
        }
    }

    override fun getItemCount(): Int {
        return flashSaleList.size
    }

    class FlashSaleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var flashSaleImage: ImageView = itemView.findViewById(R.id.flashSale_image)
        var flashSaleName: TextView = itemView.findViewById(R.id.flashSale_name)
        var flashSaleSalePrice: TextView = itemView.findViewById(R.id.flashSale_sale_price)
        var flashSalePrice: TextView = itemView.findViewById(R.id.flashSale_price)
        var item: View = itemView
    }

}