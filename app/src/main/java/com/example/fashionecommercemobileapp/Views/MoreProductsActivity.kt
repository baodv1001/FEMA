package com.example.fashionecommercemobileapp.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.Adapters.ProductAdapter
import com.example.fashionecommercemobileapp.Adapters.RecommendAdapter
import com.example.fashionecommercemobileapp.Model.Product
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.ViewModels.ProductViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_more_products.*

class MoreProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var productViewModel: ProductViewModel? = null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_products)
        var intent: Intent = intent
        var idProductCode: String? = intent.getStringExtra("idProductCode")
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel!!.init()
        if (idProductCode != null) {
            productViewModel!!.getProductData(idProductCode)
                ?.observe(this, Observer { setupProductRecyclerView(it) })
        }
    }

    private fun setupProductRecyclerView(productList: List<Product>) {
        var productAdapter: ProductAdapter = ProductAdapter(this, productList)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        all_product_recycler.layoutManager = layoutManager
        all_product_recycler.adapter = productAdapter
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }
}