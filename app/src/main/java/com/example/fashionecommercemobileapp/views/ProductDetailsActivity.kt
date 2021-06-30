package com.example.fashionecommercemobileapp.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.viewmodels.CartInfoViewModel
import com.example.fashionecommercemobileapp.viewmodels.ProductInfoViewModel
import com.example.fashionecommercemobileapp.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.activity_product_details.view.*


class ProductDetailsActivity : AppCompatActivity() {
    var idAccount: Int = 1
    var isLiked: Boolean = true
    var idProduct: Int = 0
    var quantity: Int = 0
    var wishListViewModel: WishListViewModel? = null
    var id: String? = null
    var position: Int = 0
    var productInfoViewModel: ProductInfoViewModel? = null
    private lateinit var productViewModel: ProductViewModel
    var sizeMap: MutableMap<String, String> = mutableMapOf<String, String>()
    var colorMap: MutableMap<String, String> = mutableMapOf<String, String>()
    var language: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        language = sharedPreferences.getString("My_Lang", "").toString()

        val intent: Intent = intent
        idProduct = intent.getStringExtra("idProduct")?.toInt() ?: 0
        val sp: SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        idAccount = sp.getString("Id", "")?.toInt()!!
        id = intent.getStringExtra("id")
        var name: String? = intent.getStringExtra("name")
        var price: String? = intent.getStringExtra("price")
        var discount: String? = intent.getStringExtra("discount")
        var rating: String? = intent.getStringExtra("rating")
        var image: String? = intent.getStringExtra("image")
        quantity = intent.getStringExtra("quantity")?.toInt() ?: 0
        position = intent.getIntExtra("position",0)
        isLiked = intent.getBooleanExtra("isLiked", true)

        ProductRepository.Companion.setContext(this@ProductDetailsActivity)
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.init()

        WishListRepository.Companion.setContext(this@ProductDetailsActivity)
        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel::class.java)
        wishListViewModel!!.init()

        desc_txt.text = intent.getStringExtra("details")
        if (isLiked)
            Glide.with(this).load(R.drawable.ic_heartbutton).into(button_like)
        else
            Glide.with(this).load(R.drawable.ic_un_heart_button).into(button_like)

        detail_name.text = name
        if (price != null)
            detail_price.text = price
        if (discount != null)
            detail_sale_price.text = ((1 - discount.toFloat()) * price!!.toFloat()).toString()
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
            sizeMap[size.idSize!!] = size.name!!
        }
        radio_group_size.removeViews(i, radio_group_size.size - i)
    }

    private fun setupColorRadioGroup(listColor: List<Color>) {
        var i = 0;
        for (color: Color in listColor) {
            var radioButton: RadioButton = RadioButton(this)
            (radio_group_color[i] as RadioButton).text = color.name
            i++
            colorMap[color.idColor!!] = color.name!!
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
        var data = Intent().apply {
            putExtra("check", isLiked)
            putExtra("position", position)
        }
        setResult(RESULT_OK, data)
        super.onBackPressed()
    }

    override fun onBackPressed() {
        var data = Intent().apply {
            putExtra("check", isLiked)
            putExtra("position", position)
        }
        setResult(RESULT_OK, data)
        super.onBackPressed()
        finish()
    }

    fun addToCart(view: View) {
        val selectedSizeButton: RadioButton = findViewById(radio_group_size.checkedRadioButtonId)
        val size: String = selectedSizeButton.text.toString()
        val idSize: Int = sizeMap.filterValues { it == size }.keys.first().toString().toInt()

        val selectedColorButton: RadioButton = findViewById(radio_group_color.checkedRadioButtonId)
        val color: String = selectedColorButton.text.toString()
        val idColor: Int = colorMap.filterValues { it == color }.keys.first().toString().toInt()

        val spf = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val idAccount = spf.getString("Id", "0")?.toInt() ?: 0
        if (quantity == 0) {
            if (language == "en")
                Toast.makeText(this, "Out of stock!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Hết hàng!", Toast.LENGTH_SHORT).show()
        }

        val cartInfoViewModel: CartInfoViewModel =
            ViewModelProviders.of(this).get(CartInfoViewModel::class.java)
        cartInfoViewModel.init()
        cartInfoViewModel.getCartInfoByProduct(idAccount, idProduct, idSize, idColor)
            .observe(this, Observer { it ->
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (it.data?.idCart != 0) {
                                if (quantity > it.data?.quantity!!) {
                                    val updatedQuantity: Int = it.data.quantity!! + 1
                                    quantity -= 1
                                    cartInfoViewModel.updateCartInfo(
                                        idAccount,
                                        idProduct,
                                        idSize,
                                        idColor,
                                        updatedQuantity
                                    )
                                    productViewModel.updateProduct(
                                        idProduct.toString(),
                                        1
                                    )
                                    Toast.makeText(
                                        this,
                                        "Add to cart successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    if (language == "en")
                                        Toast.makeText(this, "This product is out of stock!", Toast.LENGTH_SHORT).show()
                                    else
                                        Toast.makeText(this, "Sản phẩm hết hàng!", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                cartInfoViewModel.postCartInfo(
                                    idAccount,
                                    idProduct,
                                    idSize,
                                    idColor,
                                    1
                                )
                                if (language == "en")
                                    Toast.makeText(this, "Add to cart successfully", Toast.LENGTH_SHORT)
                                        .show()
                                else
                                    Toast.makeText(this, "Thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show()
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