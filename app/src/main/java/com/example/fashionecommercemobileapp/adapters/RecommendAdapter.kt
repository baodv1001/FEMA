package com.example.fashionecommercemobileapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.views.ProductDetailsActivity
import kotlinx.android.synthetic.main.flash_sale_recycler_item.view.*
import kotlinx.android.synthetic.main.recommended_recycler_item.view.*


class RecommendAdapter(
    private val context: Context,
    recommendedList: List<Product>,
    wishList: List<Product>
) : RecyclerView.Adapter<RecommendAdapter.RecommendedViewHolder>() {
    private val requestCode: Int = 3
    private val wishList: List<Product> = wishList
    private val recommendedList: List<Product> = recommendedList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view: View =
                LayoutInflater.from(context).inflate(R.layout.recommended_recycler_item, parent, false)
        return RecommendedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        holder.recommendName.text = recommendedList[position].name
        holder.recommendSalePrice.text =
                ((1 - recommendedList[position].discount!!.toFloat()) * recommendedList[position].price!!.toFloat()).toString()
        holder.recommendedRating.rating = recommendedList[position].rating?.toFloat()!!
        holder.recommendPrice.text = recommendedList[position].price
        Glide.with(context).load(recommendedList[position].imageFile).into(holder.recommendImage)
        holder.item.layoutParams.width = (getScreenWidth(context) - 128) / 2;

        var isLiked: Boolean = false
        if (wishList.isNotEmpty())
        {
            if (recommendedList[position].idProduct?.toInt()!! >= wishList[0].idProduct?.toInt()!!
                    && recommendedList[position].idProduct?.toInt()!! <= wishList[wishList.size-1].idProduct?.toInt()!!)
            {
                for (wishItem in wishList) {
                    if (wishItem.idProduct == recommendedList[position].idProduct) {
                        Glide.with(context).load(R.drawable.ic_heartbutton).into(holder.itemView.button_like)
                        isLiked = true
                        break
                    }
                }
            }
        }
        holder.itemView.setOnClickListener {
            val i = Intent(context, ProductDetailsActivity::class.java)
            i.putExtra("idProduct", recommendedList[position].idProduct)
            i.putExtra("quantity", recommendedList[position].quantity)
            i.putExtra("id", recommendedList[position].idProduct)
            i.putExtra("name", recommendedList[position].name)
            i.putExtra("price", recommendedList[position].price)
            i.putExtra("discount", recommendedList[position].discount)
            i.putExtra("rating", recommendedList[position].rating)
            i.putExtra("image", recommendedList[position].imageFile)
            i.putExtra("position", position)
            i.putExtra("isLiked", isLiked)
            //context.startActivity(i)
            (context as Activity).startActivityForResult(i, requestCode)
        }
    }

    override fun getItemCount(): Int {
        return recommendedList.size
    }

    class RecommendedViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        var recommendImage: ImageView = itemView.findViewById(R.id.recommend_image)
        var recommendName: TextView = itemView.findViewById(R.id.recommend_name)
        var recommendSalePrice: TextView = itemView.findViewById(R.id.recommend_sale_price)
        var recommendPrice: TextView = itemView.findViewById(R.id.recommend_price)
        var recommendedRating: RatingBar = itemView.findViewById(R.id.recommend_ratingBar)
        var item: View = itemView
    }

    private fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

}