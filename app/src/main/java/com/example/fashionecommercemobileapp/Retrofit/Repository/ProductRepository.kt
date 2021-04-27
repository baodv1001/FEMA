package com.example.fashionecommercemobileapp.Retrofit.Repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.Model.Product
import com.example.fashionecommercemobileapp.Retrofit.API.ProductApi
import com.example.fashionecommercemobileapp.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository {
    private var productApi: ProductApi? = null
    fun getNews(source: String?, key: String?): List<Product> {
        val callProduct: Call<List<Product>> = productApi!!.getProduct()
        var result: List<Product>? = null
        callProduct.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
            ) {
                val categoryList: List<Product> = response.body()!!
                result = categoryList
            }

            override fun onFailure(
                    call: Call<List<Product>>,
                    t: Throwable
            ) {

            }
        })
        return result!!
    }

    companion object {
        private var productRepository: ProductRepository? = null
        val instance: ProductRepository?
            get() {
                if (productRepository == null) {
                    productRepository = ProductRepository()
                }
                return productRepository
            }
    }

    init {
        var retrofit: RetrofitClient = RetrofitClient()
        productApi = retrofit.getRetrofitInstance()!!.create(ProductApi::class.java)
    }
}