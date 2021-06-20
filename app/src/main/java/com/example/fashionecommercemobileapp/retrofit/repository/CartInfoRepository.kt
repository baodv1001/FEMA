package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import android.widget.Toast
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.CartInfoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartInfoRepository {
    private var apiCartInfo: CartInfoApi? = null

    companion object {
        private var cartInfoRepository: CartInfoRepository? = null
        val instance: CartInfoRepository?
            get() {
                if (cartInfoRepository == null) {
                    cartInfoRepository = CartInfoRepository()
                }
                return cartInfoRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    fun postCartInfo(idCart: Int, idProduct: Int, idSize: Int, idColor: Int, quantity: Int) {
        val cartCall: Call<Boolean> =
            apiCartInfo!!.postCartInfo(idCart, idProduct, idSize, idColor, quantity)
        cartCall.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {

            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun deleteCartInfo(idCart: Int, idProduct: Int, idSize: Int, idColor: Int) {
        val cartCall: Call<Boolean> =
            apiCartInfo!!.deleteCartInfo(idCart, idProduct, idSize, idColor)
        cartCall.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {

            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun updateCartInfo(idCart: Int, idProduct: Int, idSize: Int, idColor: Int, quantity: Int) {
        val cartCall: Call<Boolean> =
            apiCartInfo!!.updateCartInfo(idCart, idProduct, idSize, idColor, quantity)
        cartCall.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {

            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    suspend fun getCartInfoRequest(idCart: Int) = apiCartInfo?.getCartInfo(idCart)
    suspend fun getCartInfoByProduct(idCart: Int, idProduct: Int, idSize: Int, idColor: Int) =
        apiCartInfo?.getCartInfo(idCart, idProduct, idSize, idColor)

    init {
        val retrofit: RetrofitClient = RetrofitClient()
        apiCartInfo = retrofit.getRetrofitInstance()!!.create(CartInfoApi::class.java)
    }
}