package com.example.fashionecommercemobileapp.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.WishlistAdapter
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.repository.WishListRepository
import com.example.fashionecommercemobileapp.viewmodels.WishListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_wishlist.*
import kotlinx.android.synthetic.main.activity_wishlist.bnvMain

class WishListActivity : AppCompatActivity() {
    private var wishListViewModel: WishListViewModel? = null
    private var idAccount: Int = 1
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        val sp: SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        idAccount = sp.getString("Id", "")?.toInt()!!
        WishListRepository.Companion.setContext(this@WishListActivity)
        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel::class.java)
        wishListViewModel!!.init()
        wishListViewModel!!.getWishListProductData(idAccount)
            ?.observe(this, Observer { setupWishListProduct(it) })
        handleNavigation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0)
        {
            if (resultCode == RESULT_OK)
            {
                    wishListViewModel!!.getWishListProductData(idAccount)
                            ?.observe(this, Observer { setupWishListProduct(it) })
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun setupWishListProduct(productList: List<Product>) {
        var wishListAdapter: WishlistAdapter = WishlistAdapter(this, productList.toMutableList(), wishListViewModel, idAccount.toString())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        wishlistRecycler.layoutManager = layoutManager
        wishlistRecycler.adapter = wishListAdapter
    }
    private fun handleNavigation() {
        var navigationBar: BottomNavigationView = bnvMain

        navigationBar.selectedItemId = R.id.wish_list
        navigationBar.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nvg -> startActivity(Intent(this@WishListActivity, MainActivity::class.java))
                R.id.cart -> startActivity(Intent(this@WishListActivity, CartActivity::class.java))
                R.id.profile -> startActivity(Intent(this@WishListActivity, AccountActivity::class.java))
            }
            this.finish()
            true
        })
    }
}