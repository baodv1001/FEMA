package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.model.Category
import com.example.fashionecommercemobileapp.retrofit.repository.CategoryRepository

class CategoryViewModel : ViewModel() {
    private var categoryData: MutableLiveData<List<Category>>? = null
    private var categoryRepository: CategoryRepository? = null
    public fun init() {
        if (categoryData != null) {
            return
        }
        categoryRepository = CategoryRepository()
        categoryRepository!!.doCategoryRequest()
        categoryData = categoryRepository?.getCategoryData()
    }

    fun getCategoryData(): LiveData<List<Category>>? {
        return categoryData
    }
}