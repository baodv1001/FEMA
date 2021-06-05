package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.model.Cart
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.retrofit.repository.CartRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers


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

    fun postCartInfo(idCart: Int, idProduct: Int, quantity: Int) {
        cartRepository!!.postCartInfo(idCart, idProduct, quantity)
    }

    fun deleteCartInfo(idCart: Int, idProduct: Int) {
        cartRepository!!.deleteCartInfo(idCart, idProduct)
    }

    fun updateCartInfo(idCart: Int, idProduct: Int, quantity: Int) {
        cartRepository!!.updateCartInfo(idCart, idProduct, quantity)
    }

    fun updateCart(idCart: Int, idAccount: Int, isPaid: Boolean) {
        cartRepository!!.updateCart(idCart, idAccount, isPaid)
    }

    fun getCart(idAccount: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = cartRepository?.getCartRequest(idAccount)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }

    fun getCartInfo(idCart: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = cartRepository?.getCartInfoRequest(idCart)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }

    fun getCartInfoByProduct(idCart: Int, idProduct: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = cartRepository?.getCartInfoByProduct(idCart, idProduct)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }
}