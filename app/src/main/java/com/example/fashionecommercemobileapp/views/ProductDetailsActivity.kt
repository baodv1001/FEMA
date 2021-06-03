package com.example.fashionecommercemobileapp.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.repository.WishListRepository
import com.example.fashionecommercemobileapp.viewmodels.WishListViewModel
import com.example.fashionecommercemobileapp.model.Color
import com.example.fashionecommercemobileapp.model.Size
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.viewmodels.ProductInfoViewModel
import com.example.fashionecommercemobileapp.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.activity_product_details.view.*


class ProductDetailsActivity : AppCompatActivity() {
    var idAccount: Int = 1
    var isLiked: Boolean = true
    var wishListViewModel: WishListViewModel? = null
    var id: String? = null
    var position: Int = 0
    var productInfoViewModel: ProductInfoViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        var intent: Intent = intent
        id = intent.getStringExtra("id")
        var name: String? = intent.getStringExtra("name")
        var price: String? = intent.getStringExtra("price")
        var discount: String? = intent.getStringExtra("discount")
        var rating: String? = intent.getStringExtra("rating")
        var image: String? = intent.getStringExtra("image")
        position = intent.getIntExtra("position",0)
        isLiked = intent.getBooleanExtra("isLiked", true)

        WishListRepository.Companion.setContext(this@ProductDetailsActivity)
        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel::class.java)
        wishListViewModel!!.init()

        if (isLiked)
            Glide.with(this).load(R.drawable.ic_heartbutton).into(button_like)
        else
            Glide.with(this).load(R.drawable.ic_un_heart_button).into(button_like)

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
        productInfoViewModel = ViewModelProviders.of(this).get(ProductInfoViewModel::class.java)
        productInfoViewModel!!.init()
        loadColor()
        loadSize()
    }

    private fun loadSize() {
        productInfoViewModel?.getSizeData()?.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        var listSize = it?.data
                        if (listSize?.isEmpty() == false) {
                            setupSizeRadioGroup(listSize)
                        } else
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun loadColor() {
        productInfoViewModel?.getColorData()?.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        var listColor = it?.data
                        if (listColor?.isEmpty() == false) {
                            setupColorRadioGroup(listColor)
                        } else
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun setupSizeRadioGroup(listSize: List<Size>) {
        var i = 0;
        for (size: Size in listSize) {
            var radioButton: RadioButton = RadioButton(this)
            (radio_group_size[i] as RadioButton).text = size.name
            i++
        }
        radio_group_size.removeViews(i, radio_group_size.size - i)
    }

    private fun setupColorRadioGroup(listColor: List<Color>) {
        var i = 0;
        for (color: Color in listColor) {
            var radioButton: RadioButton = RadioButton(this)
            (radio_group_color[i] as RadioButton).text = color.name
            i++
        }
        radio_group_color.removeViews(i, radio_group_color.size - i)
    }

    fun onClickHeart(view: View) {
        if (isLiked)
        {
            wishListViewModel!!.deleteWishProduct(idAccount, id.toString().toInt())
            Glide.with(this).load(R.drawable.ic_un_heart_button).into(view as ImageView)
        }
        else
        {
            wishListViewModel!!.addNewWishItem(idAccount,id.toString().toInt())
            Glide.with(this).load(R.drawable.ic_heartbutton).into(view as ImageView)
        }
        isLiked = !isLiked
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }

    override fun onBackPressed() {
        var data = Intent().apply {
            putExtra("check", isLiked)
            putExtra("position", position)
        }
        setResult(RESULT_OK, data)
        super.onBackPressed()
    }
}