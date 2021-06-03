package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import kotlin.math.min


class ProductViewModel : ViewModel() {
    private var productData: MutableLiveData<List<Product>>? = null
    private var flashSaleData: MutableLiveData<List<Product>>? = null
    private var recommendedData: MutableLiveData<List<Product>>? = null
    private var productRepository: ProductRepository? = null
    public fun init() {
        if (productData != null) {
            return
        }
        productRepository = ProductRepository()

        productRepository!!.doRecommendedRequest()
        recommendedData = productRepository?.getRecommendedData()
    }

    fun getProductData(idProductCode: String): LiveData<List<Product>>? {
        productRepository!!.doProductRequest(idProductCode)
        productData = productRepository?.getProductData()
        return productData
    }

    fun getProductDataByName(name: String, idProductCode: String): LiveData<List<Product>>? {
        productRepository!!.doProductByNameRequest(name, idProductCode)
        productData = productRepository?.getProductData()
        return productData
    }

    fun getProductDataByRating(rating: String, idProductCode: String, minPrice: String, maxPrice: String, discount: String): LiveData<List<Product>>? {
        productRepository!!.getProductByRatingPrice(rating, idProductCode, minPrice, maxPrice, discount)
        productData = productRepository?.getProductData()
        return productData
    }

    fun getFlashSaleData(): LiveData<List<Product>>? {
        productRepository!!.doFlashSaleRequest()
        flashSaleData = productRepository?.getFlashSaleData()
        return flashSaleData
    }

    fun getAllFlashSaleData(): LiveData<List<Product>>? {
        productRepository!!.doAllFlashSaleRequest()
        flashSaleData = productRepository?.getFlashSaleData()
        return flashSaleData
    }

    fun getRecommendedData(): LiveData<List<Product>>? {
        return recommendedData
    }
}