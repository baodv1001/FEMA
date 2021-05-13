package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.CartApi
import com.example.fashionecommercemobileapp.retrofit.api.ProductApi
import com.example.fashionecommercemobileapp.retrofit.api.WishListApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRepository {
    private var cartInfoList: MutableLiveData<List<CartInfo>>? = MutableLiveData()

    fun setCartInfo(cartInfo: List<CartInfo>) {
        cartInfoList?.postValue(cartInfo)
        //cartInfoList?.value = cartInfo
    }
    fun getCartInfo(): MutableLiveData<List<CartInfo>>? {
        return cartInfoList
    }

    private var cartApi: CartApi? = null

    companion object {
        private var cartRepository: CartRepository? = null
        val instance: CartRepository?
            get() {
                if (cartRepository == null) {
                    cartRepository = CartRepository()
                }
                return cartRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    fun getCartInfoRequest(idAccount: Int) {
//        Thread(Runnable {
//            val cartInfoCall: Call<List<CartInfo>> = cartApi!!.getCartInfo(idAccount)
//            val response: Response<List<CartInfo>> = cartInfoCall.execute()
//            response.body()?.let { setCartInfo(it) }
//            Log.d(TAG, cartInfoList.toString())
//        }).start()

        val cartInfoCall: Call<List<CartInfo>> = cartApi!!.getCartInfo(idAccount)
        cartInfoCall.enqueue(object: Callback<List<CartInfo>> {
            override fun onResponse(
                call: Call<List<CartInfo>>,
                response: Response<List<CartInfo>>
            ) {
                response.body()?.let { setCartInfo(it) }
            }

            override fun onFailure(call: Call<List<CartInfo>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    init {
        var retrofit: RetrofitClient = RetrofitClient()
        cartApi = retrofit.getRetrofitInstance()!!.create(CartApi::class.java)
    }
}