package com.example.fashionecommercemobileapp.Adapters

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
import com.example.fashionecommercemobileapp.Model.Product
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.Views.ProductDetailsActivity


class FlashSaleAdapter(
    private val context: Context,
    flashSaleList: List<Product>
) : RecyclerView.Adapter<FlashSaleAdapter.FlashSaleViewHolder>() {
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
            (flashSaleList[position].discount!!.toFloat() * flashSaleList[position].price!!.toFloat()).toString()
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
        holder.itemView.setOnClickListener {
            val i = Intent(context, ProductDetailsActivity::class.java)
            i.putExtra("name", flashSaleList[position].name)
            i.putExtra("price", flashSaleList[position].price)
            i.putExtra("discount", flashSaleList[position].discount)
            i.putExtra("rating", flashSaleList[position].rating)
            i.putExtra("image", flashSaleList[position].imageFile)
            context.startActivity(i)
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