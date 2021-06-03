package com.example.fashionecommercemobileapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    var idProductCode: String? = null
    var productViewModel: ProductViewModel? = null
    private var wishListViewModel: WishListViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_products)
        var intent: Intent = intent
        idProductCode = intent.getStringExtra("idProductCode")
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel!!.init()
        WishListRepository.Companion.setContext(this@MoreProductsActivity)
        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel::class.java)
        wishListViewModel!!.init()
        if (idProductCode != null) {
            productViewModel!!.getProductData(idProductCode!!)
                    ?.observe(this, Observer {
                        val listProduct = it
                        wishListViewModel!!.getWishListProductData(idAccount)
                                ?.observe(this, Observer { it ->
                                    setupProductRecyclerView(listProduct, it)
                                })
                    })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                val check = data?.getBooleanExtra("check", true)
                val pos = data?.getIntExtra("position", 0)
                if (check == true)
                    Glide.with(this).load(R.drawable.ic_heartbutton).into(all_product_recycler[pos!!].button_like)
                else
                    Glide.with(this).load(R.drawable.ic_un_heart_button).into(all_product_recycler[pos!!].button_like)
            }
        }
    }

    private fun setupProductRecyclerView(productList: List<Product>, wishList: List<Product>) {
        var productAdapter: ProductAdapter = ProductAdapter(this, productList, wishList)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        all_product_recycler.layoutManager = layoutManager
        all_product_recycler.adapter = productAdapter
    }
    fun onClickBack(view: View) {
        super.onBackPressed()
    }

}