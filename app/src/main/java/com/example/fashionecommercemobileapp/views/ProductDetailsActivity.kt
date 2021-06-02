package com.example.fashionecommercemobileapp.views

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.viewmodels.CartViewModel
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : AppCompatActivity() {
    var isLiked: Boolean = true
    var id: Int = 0
    var quantity: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        var intent: Intent = intent
        id = intent.getStringExtra("idProduct")?.toInt() ?: 0
        quantity = intent.getStringExtra("quantity")?.toInt() ?: 0
        var name: String? = intent.getStringExtra("name")
        var price: String? = intent.getStringExtra("price")
        var discount: String? = intent.getStringExtra("discount")
        var rating: String? = intent.getStringExtra("rating")
        var image: String? = intent.getStringExtra("image")
        detail_name.text = name
        if (price != null)
            detail_price.text = price
        if (discount != null)
            detail_sale_price.text = (discount.toFloat() * price!!.toFloat()).toString()
        if (rating != null) {
            ratingBar.rating = rating.toFloat()
        }
        detail_price.paintFlags = detail_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        Glide.with(this).load(image).into(detail_image)
    }

    fun onClickHeart(view: View) {
        if (isLiked)
            Glide.with(this).load(R.drawable.ic_un_heart_button).into(view as ImageView)
        else
            Glide.with(this).load(R.drawable.ic_heartbutton).into(view as ImageView)
        isLiked = !isLiked
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }

    fun addToCart(view: View) {
        if (quantity == 0) {
            Toast.makeText(this, "Out of stock!", Toast.LENGTH_SHORT).show()
        }
        val cartViewModel: CartViewModel =
            ViewModelProviders.of(this).get(CartViewModel::class.java)
        cartViewModel!!.init()
        cartViewModel!!.getCartInfoByProduct(1, id)?.observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data != null) {
                            if (quantity > it.data.quantity!!) {
                                cartViewModel.updateCartInfo(1, id, 1)
                                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()

                            } else {
                                Toast.makeText(this, "Out of stock!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            cartViewModel.postCartInfo(1, id, 1)
                            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}