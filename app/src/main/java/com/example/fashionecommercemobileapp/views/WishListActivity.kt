package com.example.fashionecommercemobileapp.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.WishlistAdapter
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.repository.WishListRepository
import com.example.fashionecommercemobileapp.viewmodels.WishListViewModel
import kotlinx.android.synthetic.main.activity_wishlist.*

class WishListActivity : AppCompatActivity() {
    private var wishListViewModel: WishListViewModel? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        WishListRepository.Companion.setContext(this@WishListActivity)
        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel::class.java)
        wishListViewModel!!.init()
        wishListViewModel!!.getWishListProductData(32)
            ?.observe(this, Observer { setupWishListProduct(it) })

        btnCartWish.setOnClickListener {
            val intent = Intent(this@WishListActivity, CartActivity::class.java)
            startActivity(intent)
        }
    }

/*    private fun getPopularData(popularList:List<Popular>) {
        var popularRecyclerView = findViewById<View>(R.id.wishlistRecycler) as RecyclerView
        var popularAdapter = WishlistAdapter(this, popularList)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        popularRecyclerView.setLayoutManager(layoutManager)
        popularRecyclerView.setAdapter(popularAdapter)
    }*/

    private fun setupWishListProduct(productList: List<Product>) {
        var wishListAdapter: WishlistAdapter = WishlistAdapter(this, productList.toMutableList(), wishListViewModel)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        wishlistRecycler.layoutManager = layoutManager
        wishlistRecycler.adapter = wishListAdapter
    }
}