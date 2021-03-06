package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.Cart
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
    private var apiCart: CartApi? = null

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

    fun updateCart(idCart: Int, idAccount: Int, isPaid: Boolean) {
        val cartCall: Call<Boolean> = apiCart!!.updateCart(idCart, idAccount, isPaid)
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

    suspend fun getCartRequest(idAccount: Int) = apiCart?.getCart(idAccount)

    init {
        val retrofit: RetrofitClient = RetrofitClient()
        apiCart = retrofit.getRetrofitInstance()!!.create(CartApi::class.java)
    }
}