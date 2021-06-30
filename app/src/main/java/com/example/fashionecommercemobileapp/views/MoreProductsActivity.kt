package com.example.fashionecommercemobileapp.views

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.adapters.ProductAdapter
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.repository.WishListRepository
import com.example.fashionecommercemobileapp.viewmodels.ProductViewModel
import com.example.fashionecommercemobileapp.viewmodels.WishListViewModel
import kotlinx.android.synthetic.main.activity_more_products.*
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.product_recycler_item.view.*


class MoreProductsActivity : AppCompatActivity() {
    var idAccount: Int = 1
    private var wishListViewModel: WishListViewModel? = null
    var btnOne: ToggleButton? = null
    var btnTwo: ToggleButton? = null
    var btnThree: ToggleButton? = null
    var btnFour: ToggleButton? = null
    var btnFive: ToggleButton? = null
    var minPrice: String = "0"
    var maxPrice: String = "9999999999"
    var rating: String = "0"
    var checkedButton: ToggleButton? = null
    var isConfirm: Boolean = false
    var productViewModel: ProductViewModel? = null
    var idProductCode: String = ""
    var filterDialog: AlertDialog? = null
    var currentProducts: List<Product>? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_products)

        val sp: SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        idAccount = sp.getString("Id", "")?.toInt()!!
        val intent: Intent = intent
        idProductCode = intent.getStringExtra("idProductCode").toString()
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel!!.init()
        WishListRepository.Companion.setContext(this@MoreProductsActivity)
        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel::class.java)
        wishListViewModel!!.init()
        if (idProductCode != null) {
            if (idProductCode != "0") {
                productViewModel!!.getProductData(idProductCode)
                    ?.observe(this, Observer {
                        val listProduct = it
                        wishListViewModel!!.getWishListProductData(idAccount)
                            ?.observe(this, Observer { it ->
                                setupProductRecyclerView(listProduct, it)
                            })
                    })
            } else {
                productViewModel!!.getAllFlashSaleData()
                    ?.observe(this, Observer {
                        val listProduct = it
                        wishListViewModel!!.getWishListProductData(idAccount)
                            ?.observe(this, Observer { it ->
                                setupProductRecyclerView(listProduct, it)
                            })
                    })
            }
        }
        searchProduct()
        handleSort()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                val check = data?.getBooleanExtra("check", true)
                val pos = data?.getIntExtra("position", 0)
                if (check == true)
                    Glide.with(this).load(R.drawable.ic_heartbutton)
                        .into(all_product_recycler[pos!!].button_like)
                else
                    Glide.with(this).load(R.drawable.ic_un_heart_button)
                        .into(all_product_recycler[pos!!].button_like)
            }
        }
    }

    private fun setupProductRecyclerView(productList: List<Product>, wishList: List<Product>) {
        currentProducts = productList
        var productAdapter: ProductAdapter = ProductAdapter(this, productList, wishList)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        all_product_recycler.layoutManager = layoutManager
        all_product_recycler.adapter = productAdapter
    }

    private fun handleSort() {
        radio_group.setOnCheckedChangeListener { rGroup, _ ->
            val radioBtnID: Int = rGroup.checkedRadioButtonId
            rGroup[0].setBackgroundResource(R.drawable.ic_baseline_arrow_upward_24)
            rGroup[1].setBackgroundResource(R.drawable.ic_baseline_arrow_downward_24)
            if (radioBtnID != -1) {
                val radioB: View = rGroup.findViewById(radioBtnID)


                when (rGroup.indexOfChild(radioB)) {
                    0 -> {
                        radioB.setBackgroundResource(R.drawable.ic_checked_arrow_upward)
                        currentProducts = currentProducts?.sortedBy { x -> x.price }
                    }
                    1 -> {
                        radioB.setBackgroundResource(R.drawable.ic_checked_arrow_downward)
                        currentProducts = currentProducts?.sortedByDescending { x -> x.price }
                    }
                }
                currentProducts?.let {
                    val listProduct = it
                    wishListViewModel!!.getWishListProductData(idAccount)
                        ?.observe(this, Observer { it ->
                            setupProductRecyclerView(listProduct, it)
                        })
                }
            }
        }
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }

    private fun searchProduct() {
        txtSearchProduct.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                productViewModel!!.getProductDataByName(
                    txtSearchProduct.text.toString(),
                    idProductCode
                )
                    ?.observe(this@MoreProductsActivity, Observer {
                        val listProduct = it
                        wishListViewModel!!.getWishListProductData(idAccount)
                            ?.observe(this@MoreProductsActivity, Observer { it ->
                                setupProductRecyclerView(listProduct, it)
                            })
                    })
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onClickOpenFilter(view: View) {

        if (filterDialog == null) { // trường hợp reset lại dialog
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setView(R.layout.filter_layout)

            val dialog = builder.create()
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = (resources.displayMetrics.heightPixels)
            lp.gravity = Gravity.RIGHT or Gravity.TOP
            lp.windowAnimations = R.style.DialogAnimation
            dialog.window!!.attributes = lp
            val back = ColorDrawable(Color.TRANSPARENT)
            val inset = InsetDrawable(back, 0)
            dialog.window!!.setBackgroundDrawable(inset)
            dialog.show()
            filterDialog = dialog
        } else { // hiển thị lại dialog cũ
            filterDialog!!.show()
        }
        filterDialog?.setOnCancelListener(
            DialogInterface.OnCancelListener {
                if (!isConfirm) {  // nếu chọn filter mà ko confirm thì xóa thông tin chọn
                    filterDialog?.dismiss()
                    filterDialog = null
                }
            }
        )

        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        filterDialog!!.window!!.setLayout(width, height)

        handleFilter()
    }

    fun handleFilter() {
        btnOne = filterDialog?.findViewById(R.id.btnOneStar) as ToggleButton
        btnOne!!.setOnClickListener {
            pickRatingButton(it as ToggleButton)
        }
        btnTwo = filterDialog?.findViewById(R.id.btnTwoStar) as ToggleButton
        btnTwo!!.setOnClickListener {
            pickRatingButton(it as ToggleButton)
        }
        btnThree = filterDialog?.findViewById(R.id.btnThreeStar) as ToggleButton
        btnThree!!.setOnClickListener {
            pickRatingButton(it as ToggleButton)
        }
        btnFour = filterDialog?.findViewById(R.id.btnFourStar) as ToggleButton
        btnFour!!.setOnClickListener {
            pickRatingButton(it as ToggleButton)
        }
        btnFive = filterDialog?.findViewById(R.id.btnFiveStar) as ToggleButton
        btnFive!!.setOnClickListener {
            pickRatingButton(it as ToggleButton)
        };
    }

    @SuppressLint("ResourceAsColor")
    fun pickRatingButton(button: ToggleButton) {
        var isChecked = button.isChecked
        btnTwo?.let { uncheckButton(it) }
        btnOne?.let { uncheckButton(it) }
        btnFour?.let { uncheckButton(it) }
        btnThree?.let { uncheckButton(it) }
        btnFive?.let { uncheckButton(it) }
        button.setTextColor(Color.parseColor("#3E8FEF"))
        button.isChecked = true
        checkedButton = button
    }

    private fun uncheckButton(button: ToggleButton) {
        button.isChecked = false
        button.setTextColor(Color.parseColor("#FF000000"))
    }

    private fun filterByRatingName(rating: String, minPrice: String, maxPrice: String) {
        filterDialog?.hide()
        var res = rating.filter { it.isDigit() }
        productViewModel!!.getProductDataByRating(
            res,
            idProductCode,
            minPrice,
            maxPrice,
            if (idProductCode == "0") "0" else "-1"
        )
            ?.observe(this@MoreProductsActivity, Observer {
                val listProduct = it
                wishListViewModel!!.getWishListProductData(idAccount)
                    ?.observe(this@MoreProductsActivity, Observer { it ->
                        setupProductRecyclerView(listProduct, it)
                    })
            })
    }

    fun onClickConfirm(view: View) {
        isConfirm = true
        var min = this.filterDialog?.findViewById(R.id.txtMinPrice) as EditText
        var max = this.filterDialog?.findViewById(R.id.txtMaxPrice) as EditText
        if (!min?.text.isNullOrEmpty()) {
            minPrice = min?.text.toString()
        }
        if (!max?.text.isNullOrEmpty())
            maxPrice = max?.text.toString()
        rating = checkedButton?.text.toString()
        filterByRatingName(rating, minPrice, maxPrice)
        radio_group.check(-1)
    }

    fun onClickReset(view: View) {
        isConfirm = false
        rating = "0"
        minPrice = "0"
        maxPrice = "9999999999"
        filterByRatingName(rating, minPrice, maxPrice)
        filterDialog?.dismiss()
        filterDialog = null;
    }
}