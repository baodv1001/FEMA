package com.example.fashionecommercemobileapp.views


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.CartAdapter
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.repository.CartRepository
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import com.example.fashionecommercemobileapp.viewmodels.CartViewModel
import com.example.fashionecommercemobileapp.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    private var cartList: List<CartInfo> = arrayListOf()
    private var cartViewModel: CartViewModel? = null
    private var productViewModel: ProductViewModel? = null
    private var idCart: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        CartRepository.Companion.setContext(this@CartActivity)
        ProductRepository.Companion.setContext(this@CartActivity)

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        cartViewModel!!.init()
        cartViewModel!!.getCart(1)
            ?.observe(this, Observer { idCart = it.idCart!! })
        cartViewModel!!.getCartInfo(idCart)
            ?.observe(this, Observer { cartList = it })

        var list: ArrayList<String> = arrayListOf()
        cartList.forEach { info ->
            list.add(info.idProduct.toString())
        }

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel!!.init()
        var product: LiveData<List<Product>>? = productViewModel!!.getProduct(list)
        product?.observe(
            this,
            Observer<List<Product>> { setUpRecyclerView(cartList, it); loadData(cartList, it) })
    }

    fun loadData(cartList: List<CartInfo>, productList: List<Product>) {
        var subTotal: Int = 0
        for (i in cartList.indices) {
            subTotal += cartList[i].quantity!! * (productList[i].price?.toInt() ?: 0)
        }
        textview_sub_total.text = subTotal.toString()
        textview_total.text = (subTotal - textview_discount.text.toString()
            .toInt() + textview_shipping_cost.text.toString().toInt()).toString()
    }

    private fun setUpRecyclerView(cartList: List<CartInfo>, productList: List<Product>) {
        var cartAdapter: CartAdapter = CartAdapter(this, cartList, productList)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview_cart.layoutManager = layoutManager
        recyclerview_cart.adapter = cartAdapter
    }
}