package com.example.fashionecommercemobileapp.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.CategoryAdapter
import com.example.fashionecommercemobileapp.adapters.SearchAdapter
import com.example.fashionecommercemobileapp.model.Category
import com.example.fashionecommercemobileapp.retrofit.repository.CategoryRepository
import com.example.fashionecommercemobileapp.viewmodels.CategoryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {
    var categoryViewModel: CategoryViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        CategoryRepository.Companion.setContext(this@SearchActivity)
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        categoryViewModel!!.init()
        searchCategory()
    }

    private fun searchCategory()
    {
        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                categoryViewModel!!.getCategoryByNameData(txtSearch.text.toString())
                        ?.observe(this@SearchActivity, Observer { setupCategoryRecyclerView(it) })
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
    private fun setupCategoryRecyclerView(categoryList: List<Category>) {
        var searchAdapter: SearchAdapter = SearchAdapter(this, categoryList)
        val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        search_recycler.layoutManager = layoutManager
        search_recycler.adapter = searchAdapter
    }
}