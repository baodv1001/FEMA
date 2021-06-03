package com.example.fashionecommercemobileapp.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.adapters.*
import com.example.fashionecommercemobileapp.model.Category
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.repository.CategoryRepository
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import com.example.fashionecommercemobileapp.retrofit.repository.WishListRepository
import com.example.fashionecommercemobileapp.viewmodels.CategoryViewModel
import com.example.fashionecommercemobileapp.viewmodels.ProductViewModel
import com.example.fashionecommercemobileapp.viewmodels.WishListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_more_products.*
import kotlinx.android.synthetic.main.flash_sale_recycler_item.view.*
import kotlinx.android.synthetic.main.product_recycler_item.view.*
import java.util.*


class MainActivity : AppCompatActivity() {
    var idAccount: Int = 1
    private var productViewModel: ProductViewModel? = null
    var categoryViewModel: CategoryViewModel? = null
    lateinit var viewPager: ViewPager
    lateinit var adapter: SwipeAdapter
    var currentPosition: Int = 0;
    private var wishListViewModel: WishListViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ProductRepository.Companion.setContext(this@MainActivity)
        CategoryRepository.Companion.setContext(this@MainActivity)
        WishListRepository.Companion.setContext(this@MainActivity)
        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel::class.java)
        wishListViewModel!!.init()
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel!!.init()
        productViewModel!!.getRecommendedData()
                ?.observe(this, Observer {
                    val listRecommend = it
                    wishListViewModel!!.getWishListProductData(idAccount)
                            ?.observe(this, Observer { it ->
                                setupRecommendedRecyclerView(listRecommend, it)
                            })
                })
        productViewModel!!.getFlashSaleData()
            ?.observe(this, Observer {
                val listFlashSale = it
                wishListViewModel!!.getWishListProductData(idAccount)
                        ?.observe(this, Observer { it ->
                            setupFlashSaleRecyclerView(listFlashSale, it)
                        })
            })
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        categoryViewModel!!.init()
        categoryViewModel!!.getCategoryData()
            ?.observe(this, Observer { setupCategoryRecyclerView(it) })
        initSlideShow()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1)
            if (resultCode == RESULT_OK && data != null) {
                val check = data.getBooleanExtra("check", true)
                val pos = data.getIntExtra("position", 0)
                if (check)
                {
                    Glide.with(this).load(R.drawable.ic_heartbutton).into(flash_sale_recycler[pos].button)
                }
                else
                {
                    Glide.with(this).load(R.drawable.ic_un_heart_button).into(flash_sale_recycler[pos].button)
                }
                productViewModel!!.getRecommendedData()
                        ?.observe(this, Observer {
                            val listRecommend = it
                            wishListViewModel!!.getWishListProductData(idAccount)
                                    ?.observe(this, Observer { it ->
                                        setupRecommendedRecyclerView(listRecommend, it)
                                    })
                        })
        }
        if (requestCode == 3)
            if (resultCode == RESULT_OK && data != null) {
                val check = data.getBooleanExtra("check", true)
                val pos = data.getIntExtra("position", 0)
                if (check)
                {
                    Glide.with(this).load(R.drawable.ic_heartbutton).into(flash_sale_recycler[pos].button)
                }
                else
                {
                    Glide.with(this).load(R.drawable.ic_un_heart_button).into(flash_sale_recycler[pos].button)
                }
                productViewModel!!.getFlashSaleData()
                        ?.observe(this, Observer {
                            val listFlashSale = it
                            wishListViewModel!!.getWishListProductData(idAccount)
                                    ?.observe(this, Observer { it ->
                                        setupFlashSaleRecyclerView(listFlashSale, it)
                                    })
                        })
            }
    }

    private fun setupRecommendedRecyclerView(productList: List<Product>, wishList: List<Product>) {
        var recommendAdapter: RecommendAdapter = RecommendAdapter(this, productList, wishList)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recommend_recycler.layoutManager = layoutManager
        recommend_recycler.adapter = recommendAdapter
    }

    private fun setupFlashSaleRecyclerView(flashSaleList: List<Product>, wishList: List<Product>) {
        var flashSaleAdapter: FlashSaleAdapter = FlashSaleAdapter(this, flashSaleList, wishList)
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
}