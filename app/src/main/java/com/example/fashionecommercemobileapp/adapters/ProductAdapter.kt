package com.example.fashionecommercemobileapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
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
import com.example.fashionecommercemobileapp.views.ProductDetailsActivity
import com.example.fashionecommercemobileapp.R
import kotlinx.android.synthetic.main.flash_sale_recycler_item.view.*
import kotlinx.android.synthetic.main.product_recycler_item.view.*


class ProductAdapter(
    private val context: Context,
    allProductsList: List<Product>,
    wishList: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val requestCode: Int = 2
    private val wishList: List<Product> = wishList
    private val allProductsList: List<Product> = allProductsList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view: View =
                LayoutInflater.from(context).inflate(R.layout.product_recycler_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        //Truyền dữ liệu vô item
        holder.productName.text = allProductsList[position].name
        holder.productSalePrice.text =
                ((1 - allProductsList[position].discount!!.toFloat()) * allProductsList[position].price!!.toFloat()).toString()
        holder.productPrice.text = allProductsList[position].price
        Glide.with(context).load(allProductsList[position].imageFile).into(holder.productImage)
        holder.productRating.rating = allProductsList[position].rating!!.toFloat();
        holder.item.layoutParams.width =
            (getScreenWidth(context) - (64 * Resources.getSystem().displayMetrics.density).toInt()) / 2;

        var isLiked: Boolean = false
        if (wishList.isNotEmpty())
        {
            if (allProductsList[position].idProduct?.toInt()!! >= wishList[0].idProduct?.toInt()!!
                    && allProductsList[position].idProduct?.toInt()!! <= wishList[wishList.size-1].idProduct?.toInt()!!)
            {
                for (wishItem in wishList) {
                    if (wishItem.idProduct == allProductsList[position].idProduct) {
                        Glide.with(context).load(R.drawable.ic_heartbutton).into(holder.itemView.button_like)
                        isLiked = true
                        break
                    }
                }
            }
        }
        //Bắt sự kiện click vào item
        holder.itemView.setOnClickListener {
            val i = Intent(context, ProductDetailsActivity::class.java)
            i.putExtra("id", allProductsList[position].idProduct)
            i.putExtra("name", allProductsList[position].name)
            i.putExtra("price", allProductsList[position].price)
            i.putExtra("discount", allProductsList[position].discount)
            i.putExtra("rating", allProductsList[position].rating)
            i.putExtra("image", allProductsList[position].imageFile)
            i.putExtra("position", position)
            i.putExtra("isLiked", isLiked)
            //context.startActivity(i)
            (context as Activity).startActivityForResult(i, requestCode)
        }
    }

    override fun getItemCount(): Int {
        return allProductsList.size
    }

    class ProductViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        var productImage: ImageView = itemView.findViewById(R.id.product_image)
        var productName: TextView = itemView.findViewById(R.id.product_name)
        var productSalePrice: TextView = itemView.findViewById(R.id.product_sale_price)
        var productPrice: TextView = itemView.findViewById(R.id.product_price)
        var productRating: RatingBar = itemView.findViewById(R.id.product_ratingBar)
        var item: View = itemView
    }

    private fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    private fun getScreenHeight(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

}