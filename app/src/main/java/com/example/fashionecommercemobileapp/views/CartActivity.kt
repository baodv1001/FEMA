package com.example.fashionecommercemobileapp.views


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.CartAdapter
import com.example.fashionecommercemobileapp.model.Bill
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.repository.CartRepository
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.viewmodels.CartViewModel
import com.example.fashionecommercemobileapp.viewmodels.OrderViewModel
import com.example.fashionecommercemobileapp.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_login.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartActivity : AppCompatActivity() {
    private var cartList: ArrayList<CartInfo> = arrayListOf()
    private var idCart: Int = 0
    private var productList: ArrayList<Product> = arrayListOf()

    private lateinit var cartAdapter: CartAdapter

    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        CartRepository.Companion.setContext(this@CartActivity)
        ProductRepository.Companion.setContext(this@CartActivity)

        setUpViewModel()
        setUpRecyclerView()
        setUpCartObservers()

//        btnCheckout.setOnClickListener {
//            val bill: Bill = Bill()
//            val orderViewModel: OrderViewModel =
//                ViewModelProviders.of(this).get(OrderViewModel::class.java)
//            orderViewModel!!.init()
//            orderViewModel.createBill(bill)
//        }
    }

    private fun setUpViewModel() {
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        cartViewModel!!.init()

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel!!.init()
    }

    private fun setUpRecyclerView() {
        cartAdapter = CartAdapter(this, arrayListOf(), arrayListOf())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview_cart.layoutManager = layoutManager
        recyclerview_cart.adapter = cartAdapter
    }

    private fun setUpCartObservers() {
        cartViewModel!!.getCart(1)
            ?.observe(this, Observer { it ->
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            idCart = it.data?.idCart!!
                            setUpCartInfoObservers(idCart)
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

    private fun setUpCartInfoObservers(idCart: Int) {
        cartViewModel!!.getCartInfo(idCart)?.observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        cartList = (it.data as ArrayList<CartInfo>?)!!
                        setUpProductObservers()
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

    private fun setUpProductObservers() {
        var list: ArrayList<String> = arrayListOf()
        cartList.forEach { info ->
            list.add(info.idProduct.toString())
        }

        productViewModel!!.getProduct(list)?.observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        productList = it.data as ArrayList<Product>
                        loadData(this.cartList, this.productList);
                        retrieveList(this.cartList, this.productList)
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

    private fun retrieveList(cartList: ArrayList<CartInfo>, productList: ArrayList<Product>) {
        cartAdapter.apply {
            changeData(cartList, productList)
            notifyDataSetChanged()
        }
        progressBar.visibility = View.GONE
    }

    private fun loadData(cartList: List<CartInfo>, productList: List<Product>) {
        var subTotal: Int = 0
        for (i in cartList.indices) {
            subTotal += cartList[i].quantity!! * (productList[i].price?.toInt() ?: 0)
        }
        textview_sub_total.text = NumberFormat.getIntegerInstance(Locale.GERMANY).format(subTotal)

        var total: Int = subTotal - textview_discount.text.toString().replace(".", "")
            .toInt() + textview_shipping_cost.text.toString().replace(".", "").toInt()
        textview_total.text = NumberFormat.getIntegerInstance(Locale.GERMANY).format(total)
    }
}