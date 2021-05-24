package com.example.fashionecommercemobileapp.viewmodels

import android.os.Handler
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.CartApi
import com.example.fashionecommercemobileapp.retrofit.api.ProductApi
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.concurrent.schedule


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
        productRepository!!.doFlashSaleRequest()
        productRepository!!.doRecommendedRequest()
        flashSaleData = productRepository?.getFlashSaleData()
        recommendedData = productRepository?.getRecommendedData()
    }

    fun getProduct(idProduct: List<String>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = productRepository?.fetchProductById(idProduct)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
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