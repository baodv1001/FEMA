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
import com.example.fashionecommercemobileapp.model.Color
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.model.Size
import com.example.fashionecommercemobileapp.retrofit.repository.*
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.viewmodels.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.text.NumberFormat
import java.util.*

class CartActivity : AppCompatActivity() {
    private var cartInfoList: ArrayList<CartInfo> = arrayListOf()
    private var productList: ArrayList<Product> = arrayListOf()
    private var sizeList: ArrayList<Size> = arrayListOf()
    private var colorList: ArrayList<Color> = arrayListOf()
    private var idAccount: Int = 0

    private lateinit var idDeleteProduct: LiveData<Int>
    private lateinit var isUpdatedCart: LiveData<Boolean>
    private lateinit var isSelected: LiveData<Boolean>

    private lateinit var cartAdapter: CartAdapter
    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartInfoViewModel: CartInfoViewModel
    private lateinit var productInfoViewModel: ProductInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        CartRepository.Companion.setContext(this@CartActivity)
        CartInfoRepository.Companion.setContext(this@CartActivity)
        ProductRepository.Companion.setContext(this@CartActivity)
        ProductInfoRepository.Companion.setContext(this@CartActivity)
        CouponRepository.Companion.setContext(this@CartActivity)

        val spf = getSharedPreferences("Login", Context.MODE_PRIVATE)
        idAccount = spf.getString("Id", "0")?.toInt() ?: 0

        setUpViewModel()
        setUpRecyclerView()
        setUpCartObservers()

        idDeleteProduct = cartAdapter.getIdDeletedProduct()
        val observer = Observer<Int> { idDeletedProduct ->
            if (idDeletedProduct != 0) {
                val idSize = cartAdapter.getIdSize().value
                val idColor = cartAdapter.getIdColor().value
                if (idSize != null && idColor != null) {
                    cartInfoViewModel.deleteCartInfo(idAccount, idDeletedProduct, idSize, idColor)
                }
            }
        }
        idDeleteProduct.observe(this, observer)

        isSelected = cartAdapter.getIsSelected()
        val selectedObserver = Observer<Boolean> { it ->
            if (it) {
                val product: Product? = cartAdapter.getSelectedProduct().value
                val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                    if (product != null) {
                        putExtra("idProduct", product.idProduct)
                        putExtra("name", product.name)
                        putExtra("price", product.price)
                        putExtra("discount", product.discount)
                        putExtra("rating", product.rating)
                        putExtra("image", product.imageFile)
                        putExtra("quantity", product.quantity)
                    }
                }
                startActivity(intent)
            }
        }
        isSelected.observe(this, selectedObserver)

        isUpdatedCart = cartAdapter.getIsUpdated()
        val updatedObserver = Observer<Boolean> { it ->
            if (it) {
                val idProduct = cartAdapter.getIdProduct().value
                val idSize = cartAdapter.getIdSize().value
                val idColor = cartAdapter.getIdColor().value
                val quantity = cartAdapter.getQuantity().value
                if (quantity != null && idProduct != null && idSize != null && idColor != null) {
                    cartInfoViewModel.updateCartInfo(
                        idAccount,
                        idProduct,
                        idSize,
                        idColor,
                        quantity
                    )
                    loadData(cartInfoList, productList)
                }
            }
        }
        isUpdatedCart.observe(this, updatedObserver)

        handleNavigation()
    }

    override fun onResume() {
        super.onResume()
        setUpCartObservers()
    }

    fun onClickCheckOut(view: View) {
        if (cartInfoList.isEmpty()) {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, CheckOutActivity::class.java).apply {
            putExtra("idAccount", idAccount)
            putExtra("discount", textview_discount.text)
        }
        startActivity(intent)
    }

    fun onClickApplyCode(view: View) {
        val couponViewModel: CouponViewModel =
            ViewModelProviders.of(this).get(CouponViewModel::class.java)
        couponViewModel.init()
        val coupon: String = editText_CouponCode.text.toString()
        couponViewModel.getCoupon(coupon).observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (it.data?.idCoupon == 0)
                            Toast.makeText(this, "Invalid code!", Toast.LENGTH_SHORT).show()
                        else {
                            textview_discount.text =
                                NumberFormat.getIntegerInstance(Locale.GERMANY)
                                    .format(it.data?.value)
                            Toast.makeText(
                                this,
                                "Your code was entered successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            loadData(this.cartInfoList, this.productList)
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
        cartViewModel.init()

        cartInfoViewModel = ViewModelProviders.of(this).get(CartInfoViewModel::class.java)
        cartInfoViewModel.init()

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.init()

        productInfoViewModel = ViewModelProviders.of(this).get(ProductInfoViewModel::class.java)
        productInfoViewModel.init()
    }

    private fun setUpRecyclerView() {
        cartAdapter = CartAdapter(this, arrayListOf(), arrayListOf(), sizeList, colorList)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview_cart.layoutManager = layoutManager
        recyclerview_cart.adapter = cartAdapter
    }

    private fun setUpCartObservers() {
        cartViewModel.getCart(idAccount)
            .observe(this, Observer { it ->
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            idAccount = it.data?.idCart!!
                            setUpCartInfoObservers(idAccount)
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

    private fun setUpCartInfoObservers(idCart: Int) {
        cartInfoViewModel.getCartInfo(idCart).observe(this, Observer { it ->
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
                    }
                }
            }
        })
    }

    private fun setUpProductObservers() {
        val list: ArrayList<String> = arrayListOf()
        cartInfoList.forEach { info ->
            list.add(info.idProduct.toString())
        }
        if (list.isEmpty()) {
            return
        }
        productViewModel.getProduct(list).observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        productList = it.data as ArrayList<Product>
                        loadData(this.cartInfoList, this.productList);
                        loadSize()
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

    private fun loadSize() {
        productInfoViewModel.getSizeData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        sizeList = it.data as ArrayList<Size>
                        loadColor()
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
        productInfoViewModel.getColorData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        colorList = it.data as ArrayList<Color>
                        retrieveList(
                            this.cartInfoList,
                            this.productList,
                            this.sizeList,
                            this.colorList
                        )
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

    private fun retrieveList(
        cartList: ArrayList<CartInfo>,
        productList: ArrayList<Product>,
        sizeList: List<Size>,
        colorList: List<Color>
    ) {
        cartAdapter.apply {
            changeData(cartList, productList, sizeList, colorList)
            notifyDataSetChanged()
        }
    }

    private fun loadData(cartList: List<CartInfo>, productList: List<Product>) {
        var subTotal: Int = 0
        for (i in cartList.indices) {
            subTotal += cartList[i].quantity!! * (productList[i].price?.toInt() ?: 0)
        }
        textView_sub_total.text = NumberFormat.getIntegerInstance(Locale.GERMANY).format(subTotal)

        val total: Int = subTotal - textview_discount.text.toString().replace(".", "").toInt()
        textView_total.text = NumberFormat.getIntegerInstance(Locale.GERMANY).format(total)
    }

    private fun handleNavigation() {
        val navigationBar: BottomNavigationView = bnvMain_cart

        navigationBar.selectedItemId = R.id.cart
        navigationBar.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nvg -> startActivity(Intent(this@CartActivity, MainActivity::class.java))
                R.id.profile -> startActivity(
                    Intent(
                        this@CartActivity,
                        AccountActivity::class.java
                    )
                )
                R.id.wish_list -> startActivity(
                    Intent(
                        this@CartActivity,
                        WishListActivity::class.java
                    )
                )
            }
            this.finish()
            true
        })
    }
}