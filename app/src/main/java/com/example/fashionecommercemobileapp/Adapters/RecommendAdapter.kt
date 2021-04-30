package com.example.fashionecommercemobileapp.Adapters

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
import com.example.fashionecommercemobileapp.Model.Product
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.Views.ProductDetailsActivity


class RecommendAdapter(
    private val context: Context,
    recommendedList: List<Product>
) : RecyclerView.Adapter<RecommendAdapter.RecommendedViewHolder>() {
    private val recommendedList: List<Product> = recommendedList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.recommended_recycler_item, parent, false)
        // here we need to create a layout for recyclerview cell items.
        return RecommendedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        holder.recommendName.text = recommendedList[position].name
        holder.recommendSalePrice.text =
            (recommendedList[position].discount!!.toFloat() * recommendedList[position].price!!.toFloat()).toString()
        holder.recommendedRating.rating = recommendedList[position].rating?.toFloat()!!
        holder.recommendPrice.text = recommendedList[position].price
        Glide.with(context).load(recommendedList[position].imageFile).into(holder.recommendImage)
        holder.item.layoutParams.width = (getScreenWidth(context) - 128) / 2;
        holder.itemView.setOnClickListener {
            val i = Intent(context, ProductDetailsActivity::class.java)
            i.putExtra("name", recommendedList[position].name)
            i.putExtra("price", recommendedList[position].price)
            i.putExtra("discount", recommendedList[position].discount)
            i.putExtra("rating", recommendedList[position].rating)
            i.putExtra("image", recommendedList[position].imageFile)
            context.startActivity(i)
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