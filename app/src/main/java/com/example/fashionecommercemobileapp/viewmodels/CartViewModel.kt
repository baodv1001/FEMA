package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.repository.CartRepository
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository


class CartViewModel : ViewModel() {
    private var productCartData: MutableLiveData<List<Product>>? = null
    private var cartInfoData: MutableLiveData<List<CartInfo>>? = null
    private var cartRepository: CartRepository? = null

    public fun init() {
        if (cartInfoData != null) {
            return
        }
        cartRepository = CartRepository()
//        cartRepository!!.getCartInfoRequest(1) //tim cach gan idAccount
//        cartInfoData = cartRepository?.getCartInfo()
    }

    fun getCartInfo(idAccount: Int): LiveData<List<CartInfo>>? {
        cartRepository!!.getCartInfoRequest(1) //tim cach gan idAccount
        cartInfoData = cartRepository?.getCartInfo()
        return cartInfoData
    }

    fun getProductCart(idAccount: Int): LiveData<List<Product>>? {
        
        cartRepository!!.getCartInfoRequest(1) //tim cach gan idAccount
        cartInfoData = cartRepository?.getCartInfo()
        return productCartData
    }
}