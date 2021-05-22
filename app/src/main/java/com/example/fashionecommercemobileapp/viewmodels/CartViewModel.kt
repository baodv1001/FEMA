package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.model.Cart
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.repository.CartRepository
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository


class CartViewModel : ViewModel() {
    private var cartInfoData: MutableLiveData<List<CartInfo>>? = null
    private var cartData: MutableLiveData<Cart>? = null

    private var cartRepository: CartRepository? = null

    fun init() {
        if (cartInfoData != null && cartData != null) {
            return
        }
        cartRepository = CartRepository()
    }

    fun updateCartInfo(idCart: Int, idProduct: Int, quantity: Int) {
        cartRepository!!.updateCartInfo(idCart, idProduct, quantity)
    }

    fun updateCart(idCart: Int, idAccount: Int, isPaid: Boolean) {
        cartRepository!!.updateCart(idCart, idAccount, isPaid)
    }

    fun getCart(idAccount: Int): LiveData<Cart>? {
        cartRepository!!.getCartRequest(idAccount)
        cartData = cartRepository?.getCart()
        return cartData
    }

    fun getCartInfo(idCart: Int): LiveData<List<CartInfo>>? {
        cartRepository!!.getCartInfoRequest(idCart)
        cartInfoData = cartRepository?.getCartInfo()
        return cartInfoData
    }
}