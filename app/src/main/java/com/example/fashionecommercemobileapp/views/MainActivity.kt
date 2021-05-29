package com.example.fashionecommercemobileapp.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.CategoryAdapter
import com.example.fashionecommercemobileapp.adapters.FlashSaleAdapter
import com.example.fashionecommercemobileapp.adapters.RecommendAdapter
import com.example.fashionecommercemobileapp.adapters.SwipeAdapter
import com.example.fashionecommercemobileapp.model.Category
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.repository.CategoryRepository
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import com.example.fashionecommercemobileapp.viewmodels.CategoryViewModel
import com.example.fashionecommercemobileapp.viewmodels.ProductViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private var productViewModel: ProductViewModel? = null
    var categoryViewModel: CategoryViewModel? = null
    lateinit var viewPager: ViewPager
    lateinit var adapter: SwipeAdapter
    var currentPosition: Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ProductRepository.Companion.setContext(this@MainActivity)
        CategoryRepository.Companion.setContext(this@MainActivity)
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel!!.init()
        productViewModel!!.getRecommendedData()
                ?.observe(this, Observer { setupRecommendedRecyclerView(it) })
        productViewModel!!.getFlashSaleData()
                ?.observe(this, Observer { setupFlashSaleRecyclerView(it) })
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        categoryViewModel!!.init()
        categoryViewModel!!.getCategoryData()
                ?.observe(this, Observer { setupCategoryRecyclerView(it) })
        initSlideShow()
        handleNavigation()
    }

    private fun setupRecommendedRecyclerView(productList: List<Product>) {
        var recommendAdapter: RecommendAdapter = RecommendAdapter(this, productList)
        val layoutManager: RecyclerView.LayoutManager =
                GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recommend_recycler.layoutManager = layoutManager
        recommend_recycler.adapter = recommendAdapter
    }

    private fun setupFlashSaleRecyclerView(flashSaleList: List<Product>) {
        var flashSaleAdapter: FlashSaleAdapter = FlashSaleAdapter(this, flashSaleList)
        val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        flash_sale_recycler.layoutManager = layoutManager
        flash_sale_recycler.adapter = flashSaleAdapter
    }

    private fun setupCategoryRecyclerView(categoryList: List<Category>) {
        var categoryAdapter: CategoryAdapter = CategoryAdapter(this, categoryList)
        val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        category_recycler.layoutManager = layoutManager
        category_recycler.adapter = categoryAdapter
    }

    private fun initSlideShow() {
        viewPager = view_pager
        adapter = SwipeAdapter(this)
        viewPager.adapter = adapter
        prepareDots(0)
        createSlideShow()
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            private var mScrollState = 0
            override fun onPageScrollStateChanged(state: Int) {
                handleScrollState(state);
                mScrollState = state;
            }

            private fun handleScrollState(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE && mScrollState === ViewPager.SCROLL_STATE_DRAGGING) {
                    setNextItemIfNeeded()
                }
            }

            private fun setNextItemIfNeeded() {
                if (!isScrollStateSettling()) {
                    handleSetNextItem()
                }
            }

            private fun isScrollStateSettling(): Boolean {
                return mScrollState === ViewPager.SCROLL_STATE_SETTLING
            }

            private fun handleSetNextItem() {
                val lastPosition: Int = adapter.count
                if (currentPosition === 0) {
                    view_pager.setCurrentItem(lastPosition, true)
                } else if (currentPosition === lastPosition) {
                    view_pager.setCurrentItem(0, true)
                }
            }

            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
                prepareDots(position)
            }
        })
    }

    private fun createSlideShow() {
        val handler = Handler()
        val update = Runnable {
            if (currentPosition === adapter.count) {
                currentPosition = 0
            }
            view_pager.setCurrentItem(currentPosition++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 300, 3000)
    }

    fun prepareDots(currentSlidePosition: Int) {
        if (dotsContainer.childCount > 0) {
            dotsContainer.removeAllViews()
        }
        var listdots: Array<ImageView> = Array<ImageView>(adapter.count) { ImageView(this) }
        for (i: Int in 0 until adapter.count) {
            listdots?.set(i, ImageView(this))
            if (i == currentSlidePosition)
                listdots[i].setImageDrawable(
                        ContextCompat.getDrawable(
                                this,
                                R.drawable.active_dots
                        )
                )
            else
                listdots[i].setImageDrawable(
                        ContextCompat.getDrawable(
                                this,
                                R.drawable.inactive_dots
                        )
                )
            var layoutParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParam.setMargins(4, 0, 4, 0)
            dotsContainer.addView(listdots[i], layoutParam)

        }
    }

    private fun handleNavigation() {
        var navigationBar: BottomNavigationView = bnvMain

        navigationBar.selectedItemId = R.id.home_nvg
        navigationBar.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.wish_list -> startActivity(Intent(this@MainActivity, WishListActivity::class.java))
                R.id.cart -> startActivity(Intent(this@MainActivity, CartActivity::class.java))
                R.id.profile -> startActivity(Intent(this@MainActivity, AccountActivity::class.java))
            }
            this.finish()
            true
        })
    }

    fun onClickOpenSearch(view: View) {
        startActivity(Intent(this@MainActivity, SearchActivity::class.java))
    }

    fun onClickViewFlashSale(view: View) {
        val intent = Intent(this, MoreProductsActivity::class.java).apply { }
        intent.putExtra("idProductCode", "0")
        startActivity(intent)
    }
}