package com.example.fashionecommercemobileapp.views


import android.content.Context
import android.content.Intent
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
import com.example.fashionecommercemobileapp.retrofit.repository.CouponRepository
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.viewmodels.CartViewModel
import com.example.fashionecommercemobileapp.viewmodels.CouponViewModel
import com.example.fashionecommercemobileapp.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_login.*
import java.text.NumberFormat
import java.util.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_profile.*

class CartActivity : AppCompatActivity() {
    private var cartInfoList: ArrayList<CartInfo> = arrayListOf()
    private var productList: ArrayList<Product> = arrayListOf()
    private var idCart: Int = 0

    private lateinit var idDeleteProduct: LiveData<Int>
    private lateinit var isUpdatedCart: LiveData<Boolean>

    private lateinit var cartAdapter: CartAdapter
    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        CartRepository.Companion.setContext(this@CartActivity)
        ProductRepository.Companion.setContext(this@CartActivity)
        CouponRepository.Companion.setContext(this@CartActivity)

        val spf = getSharedPreferences("Login", Context.MODE_PRIVATE)
        idCart = spf.getString("Id", "0")?.toInt() ?: 0
        Toast.makeText(this, idCart.toString(), Toast.LENGTH_SHORT).show()

        setUpViewModel()
        setUpRecyclerView()
        setUpCartObservers()

        idDeleteProduct = cartAdapter.getIdDeletedProduct()
        val observer = Observer<Int> { idDeletedProduct ->
            if (idDeletedProduct != 0) {
                cartViewModel.deleteCartInfo(idCart, idDeletedProduct)
            }
        }
        idDeleteProduct.observe(this, observer)

        isUpdatedCart = cartAdapter.getIsUpdated()
        val updatedObserver = Observer<Boolean> { it ->
            if (it) {
                val id = cartAdapter.getIdProduct().value
                val quantity = cartAdapter.getQuantity().value
                if (quantity != null && id != null) {
                    cartViewModel.updateCartInfo(idCart, id, quantity)
                    loadData(cartInfoList, productList)
                }
            }
        }
        isUpdatedCart.observe(this, updatedObserver)
    }

    fun onClickCheckOut(view: View) {
        val intent = Intent(this, CheckOutActivity::class.java).apply {
            putExtra("idCart", idCart)
            putExtra("idAccount", idCart)
        }
        startActivity(intent)
    }

    fun onClickApplyCode(view: View) {
        val couponViewModel: CouponViewModel =
            ViewModelProviders.of(this).get(CouponViewModel::class.java)
        couponViewModel!!.init()
        val coupon: String = editText_CouponCode.text.toString()
        couponViewModel.getCoupon(coupon)?.observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data?.idCoupon == 0)
                            Toast.makeText(this, "Invalid code!", Toast.LENGTH_SHORT).show()
                        else {
                            textview_discount.text =
                                NumberFormat.getIntegerInstance(Locale.GERMANY)
                                    .format(it.data?.value)
                            loadData(this.cartInfoList, this.productList)
                            Toast.makeText(
                                this,
                                "Your code was entered successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
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
                        cartInfoList = (it.data as ArrayList<CartInfo>?)!!
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
        cartInfoList.forEach { info ->
            list.add(info.idProduct.toString())
        }

        productViewModel!!.getProduct(list)?.observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        productList = it.data as ArrayList<Product>
                        loadData(this.cartInfoList, this.productList);
                        retrieveList(this.cartInfoList, this.productList)
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
        textView_sub_total.text = NumberFormat.getIntegerInstance(Locale.GERMANY).format(subTotal)

        val total: Int = subTotal - textview_discount.text.toString().replace(".", "").toInt()
        textView_total.text = NumberFormat.getIntegerInstance(Locale.GERMANY).format(total)
        handleNavigation()
    }

    private fun handleNavigation() {
        var navigationBar: BottomNavigationView = bnvMain_cart

        navigationBar.selectedItemId = R.id.cart
        navigationBar.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nvg -> startActivity(Intent(this@CartActivity, MainActivity::class.java))
                R.id.profile -> startActivity(Intent(this@CartActivity, AccountActivity::class.java))
                R.id.wish_list -> startActivity(Intent(this@CartActivity, WishListActivity::class.java))
            }
            this.finish()
            true
        })
    }
}