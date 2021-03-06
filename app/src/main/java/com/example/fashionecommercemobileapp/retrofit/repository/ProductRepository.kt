package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.ProductApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository {
    private var listProducts: MutableLiveData<List<Product>>? = MutableLiveData<List<Product>>()
    var listFlashSale: MutableLiveData<List<Product>>? = MutableLiveData<List<Product>>()
    var listRecommended: MutableLiveData<List<Product>>? = MutableLiveData<List<Product>>()

    fun setProductData(productData: List<Product>) {
        listProducts?.value = productData
    }

    fun getProductData(): MutableLiveData<List<Product>>? {
        return listProducts
    }

    fun setFlashSaleData(flashSaleData: List<Product>) {
        listFlashSale?.value = flashSaleData
    }

    fun getFlashSaleData(): MutableLiveData<List<Product>>? {
        return listFlashSale
    }

    fun setRecommendedData(recommendedData: List<Product>) {
        listRecommended?.value = recommendedData
    }

    fun getRecommendedData(): MutableLiveData<List<Product>>? {
        return listRecommended
    }

    private var productApi: ProductApi? = null

    companion object {
        private var productRepository: ProductRepository? = null
        val instance: ProductRepository?
            get() {
                if (productRepository == null) {
                    productRepository = ProductRepository()
                }
                return productRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    suspend fun fetchProductById(idProduct: List<String>) =
        productApi?.getProductById(idProduct.joinToString())

    fun doProductRequest(idProductCode: String) {
        val callProduct: Call<List<Product>> = productApi!!.getProduct(idProductCode)
        callProduct.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                response.body()?.let { setProductData(it) }
            }

            override fun onFailure(
                call: Call<List<Product>>,
                t: Throwable
            ) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun doProductByNameRequest(name: String, idProductCode: String) {
        val callProduct: Call<List<Product>> = productApi!!.getProductByName(name, idProductCode)
        callProduct.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                response.body()?.let { setProductData(it) }
            }

            override fun onFailure(
                call: Call<List<Product>>,
                t: Throwable
            ) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getProductByRatingPrice(
        rating: String,
        idProductCode: String,
        minPrice: String,
        maxPrice: String,
        discount: String
    ) {
        val callProduct: Call<List<Product>> =
            productApi!!.getProductByRating(rating, idProductCode, minPrice, maxPrice, discount)
        callProduct.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                response.body()?.let { setProductData(it) }
            }

            override fun onFailure(
                call: Call<List<Product>>,
                t: Throwable
            ) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun doFlashSaleRequest() {
        val callProduct: Call<List<Product>> = productApi!!.getFlashSale()
        callProduct.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                response.body()?.let { setFlashSaleData(it) }
            }

            override fun onFailure(
                call: Call<List<Product>>,
                t: Throwable
            ) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun doAllFlashSaleRequest() {
        val callProduct: Call<List<Product>> = productApi!!.getAllFlashSale()
        callProduct.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                response.body()?.let { setFlashSaleData(it) }
            }

            override fun onFailure(
                call: Call<List<Product>>,
                t: Throwable
            ) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun doRecommendedRequest() {
        val callProduct: Call<List<Product>> = productApi!!.getRecommended()
        callProduct.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                response.body()?.let { setRecommendedData(it) }
            }

            override fun onFailure(
                call: Call<List<Product>>,
                t: Throwable
            ) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun updateProduct(idProduct: String, quantity: Int) {
        val callProduct: Call<Boolean> = productApi!!.updateProduct(idProduct, quantity)
        callProduct.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {

            }

            override fun onFailure(
                call: Call<Boolean>,
                t: Throwable
            ) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun updateProducts(idProductList: List<String>, quantity: List<String>) {
        val callProduct: Call<Boolean> = productApi!!.updateProducts(idProductList.joinToString(), quantity.joinToString())
        callProduct.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {

            }

            override fun onFailure(
                call: Call<Boolean>,
                t: Throwable
            ) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun updateProductRating(idProductList: List<String>, rating: String) {
        val callProduct: Call<Boolean> = productApi!!.updateProductRating(idProductList.joinToString(), rating)
        callProduct.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {

            }

            override fun onFailure(
                call: Call<Boolean>,
                t: Throwable
            ) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    init {
        val retrofit: RetrofitClient = RetrofitClient()
        productApi = retrofit.getRetrofitInstance()!!.create(ProductApi::class.java)
    }
}