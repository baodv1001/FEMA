package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.Cart
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.BillApi
import com.example.fashionecommercemobileapp.retrofit.api.CartApi

class BillRepository {
    private var cart: MutableLiveData<Cart> = MutableLiveData()
    private var cartInfoList: MutableLiveData<List<CartInfo>>? = MutableLiveData()

    private var billApi: BillApi? = null

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
        private var billRepository: BillRepository? = null
        val instance: BillRepository?
            get() {
                if (billRepository == null) {
                    billRepository = BillRepository()
                }
                return billRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    fun updateCartInfo(idCart: Int, idProduct: Int, quantity: Int) {
//        val cartCall: Call<Boolean> = cartApi!!.updateCartInfo(idCart, idProduct, quantity)
//        cartCall.enqueue(object : Callback<Boolean> {
//            override fun onResponse(
//                call: Call<Boolean>,
//                response: Response<Boolean>
//            ) {
//                response.body()?.let { }
//            }
//
//            override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    init {
        var retrofit: RetrofitClient = RetrofitClient()
        billApi = retrofit.getRetrofitInstance()!!.create(BillApi::class.java)
    }
}