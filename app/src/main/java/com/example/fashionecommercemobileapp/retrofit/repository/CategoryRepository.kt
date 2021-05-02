package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.Category
import com.example.fashionecommercemobileapp.retrofit.api.CategoryApi
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRepository {
    private var listCategory: MutableLiveData<List<Category>>? = MutableLiveData<List<Category>>()
    fun setCategoryData(categoryData: List<Category>) {
        listCategory?.value = categoryData
    }

    fun getCategoryData(): MutableLiveData<List<Category>>? {
        return listCategory
    }

    private var categoryApi: CategoryApi? = null

    companion object {
        private var categoryRepository: CategoryRepository? = null
        val instance: CategoryRepository?
            get() {
                if (categoryRepository == null) {
                    categoryRepository = CategoryRepository()
                }
                return categoryRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    fun doCategoryRequest() {
        val callCategory: Call<List<Category>> = categoryApi!!.getCategory()
        callCategory.enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                response.body()?.let { setCategoryData(it) }
            }

            override fun onFailure(
                call: Call<List<Category>>,
                t: Throwable
            ) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    init {
        var retrofit: RetrofitClient = RetrofitClient()
        categoryApi = retrofit.getRetrofitInstance()!!.create(CategoryApi::class.java)

    }
}