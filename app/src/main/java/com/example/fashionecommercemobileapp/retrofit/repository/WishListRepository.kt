package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import android.text.BoringLayout
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.WishListApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishListRepository {
    private var wishListProduct: MutableLiveData<List<Product>>? = MutableLiveData<List<Product>>()
    private var checkWishItem: MutableLiveData<Boolean>? = MutableLiveData<Boolean>()
    fun setWishListProductData(productData: List<Product>) {
        wishListProduct?.value = productData
    }

    fun getWishListProductData(): MutableLiveData<List<Product>>? {
        return wishListProduct
    }

    fun setCheckWishItem(check: Boolean) {
        checkWishItem?.value = check
    }

    fun getCheckWishItem(): MutableLiveData<Boolean>? {
        return checkWishItem
    }

    private var wishListApi: WishListApi? = null

    companion object {
        private var wishListRepository: WishListRepository? = null
        val instance: WishListRepository?
            get() {
                if (wishListRepository == null) {
                    wishListRepository = WishListRepository()
                }
                return wishListRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    fun doWishListProductRequest(idAccount :Int) {
        val callListProduct: Call<List<Product>> = wishListApi!!.getWishListProduct(idAccount)
        callListProduct.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                response.body()?.let { setWishListProductData(it) }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun doDeleteWishProduct(idAccount: Int, idProduct: Int)
    {
        val call: Call<String> = wishListApi!!.deleteWishProduct(idAccount, idProduct)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun doAddWishToCart(idAccount: Int, idProduct: Int)
    {
        val call: Call<String> = wishListApi!!.addWishToCart(idAccount, idProduct)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun doAddNewWishItem(idAccount: Int, idProduct: Int)
    {
        val call: Call<String> = wishListApi!!.addNewWish(idAccount, idProduct)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun doCheckWishItem(idAccount: Int, idProduct: Int)
    {
        val call: Call<String> = wishListApi!!.checkWishItem(idAccount, idProduct)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body().toString() == "Success")
                {
                    setCheckWishItem(true)
                }
                else
                {
                    setCheckWishItem(false)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    init {
        var retrofit: RetrofitClient = RetrofitClient()
        wishListApi = retrofit.getRetrofitInstance()!!.create(WishListApi::class.java)
    }
}