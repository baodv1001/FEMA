package com.example.fashionecommercemobileapp.viewmodels

import android.os.Handler
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import java.util.*
import kotlin.concurrent.schedule


class ProductViewModel : ViewModel() {
    private var productData: MutableLiveData<List<Product>>? = null
    private var flashSaleData: MutableLiveData<List<Product>>? = null
    private var recommendedData: MutableLiveData<List<Product>>? = null
    private var productRepository: ProductRepository? = null

    private var product: LiveData<List<Product>>? = null
    public fun init() {
        if (productData != null) {
            return
        }
        productRepository = ProductRepository()
        productRepository!!.doFlashSaleRequest()
        productRepository!!.doRecommendedRequest()
        flashSaleData = productRepository?.getFlashSaleData()
        recommendedData = productRepository?.getRecommendedData()
    }

    fun getProduct(idProduct: List<String>): LiveData<List<Product>>? {
        productRepository!!.fetchProductById(idProduct)
        product = productRepository!!.getProduct()
        return product
    }

    fun getProductData(idProductCode: String): LiveData<List<Product>>? {
        productRepository!!.doProductRequest(idProductCode)
        productData = productRepository?.getProductData()
        return productData
    }

    fun getFlashSaleData(): LiveData<List<Product>>? {
        return flashSaleData
    }

    fun getRecommendedData(): LiveData<List<Product>>? {
        return recommendedData
    }
}