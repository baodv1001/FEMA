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
    private var cart: MutableLiveData<Cart> = MutableLiveData()
    private var cartInfoList: MutableLiveData<List<CartInfo>>? = MutableLiveData()

    private var cartApi: CartApi? = null

    fun setCart(cart: Cart) {
        this.cart.value = cart
    }

    fun getCart(): MutableLiveData<Cart>? {
        return cart
    }

    fun setCartInfo(cartInfo: List<CartInfo>) {
        cartInfoList?.postValue(cartInfo)
    }

    fun getCartInfo(): MutableLiveData<List<CartInfo>>? {
        return cartInfoList
    }

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

    fun updateCartInfo(idCart: Int, idProduct: Int, quantity: Int) {
        val cartCall: Call<Boolean> = cartApi!!.updateCartInfo(idCart, idProduct, quantity)
        cartCall.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {
                response.body()?.let { }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun updateCart(idCart: Int, idAccount: Int, isPaid: Boolean) {
        val cartCall: Call<Boolean> = cartApi!!.updateCart(idCart, idAccount, isPaid)
        cartCall.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {
                response.body()?.let {  }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getCartRequest(idAccount: Int) {
        val cartCall: Call<Cart> = cartApi!!.getCart(idAccount)
        cartCall.enqueue(object : Callback<Cart> {
            override fun onResponse(
                call: Call<Cart>,
                response: Response<Cart>
            ) {
                response.body()?.let { setCart(it) }
            }

            override fun onFailure(call: Call<Cart>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getCartInfoRequest(idCart: Int) {
        val cartInfoCall: Call<List<CartInfo>> = cartApi!!.getCartInfo(idCart)
        cartInfoCall.enqueue(object : Callback<List<CartInfo>> {
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