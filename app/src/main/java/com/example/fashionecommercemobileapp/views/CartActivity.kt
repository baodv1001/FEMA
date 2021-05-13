package com.example.fashionecommercemobileapp.views


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.android.synthetic.main.activity_wishlist.*


class CartActivity : AppCompatActivity() {
    private var listCart: List<CartInfo> = arrayListOf()

    private var cartViewModel: CartViewModel? = null
    private var productViewModel: ProductViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        CartRepository.Companion.setContext(this@CartActivity)
        ProductRepository.Companion.setContext(this@CartActivity)

//        listCart.forEach { info ->
//            productViewModel!!.getProductData(info.idProduct.toString())
//                ?.observe(this, Observer { it -> productList.addAll(it) })
//        }

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        cartViewModel!!.init()
        cartViewModel!!.getCartInfo(1)
            ?.observe(this, Observer { setUpRecyclerView(it, getProduct(it)) })


//        var cartAdapter: CartAdapter = CartAdapter(this, listCart)
//        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        recyclerview_cart.layoutManager = layoutManager
//        recyclerview_cart.adapter = cartAdapter
    }

    private fun getProduct(cartList: List<CartInfo>): List<Product> {
        var productList: List<Product> = arrayListOf()
        var list: MutableList<Product> = mutableListOf()

        cartList.forEach { it ->
            productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
            productViewModel!!.init()
            var product: LiveData<List<Product>>? = productViewModel!!.getProduct(it.idProduct.toString())
            product?.observe(this, Observer { list.addAll(it) })
        }
        productList = list
        return productList
    }

    private fun setUpRecyclerView(cartList: List<CartInfo>, productList: List<Product>) {
        var cartAdapter: CartAdapter = CartAdapter(this, cartList, productList)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview_cart.layoutManager = layoutManager
        recyclerview_cart.adapter = cartAdapter
    }
}