package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.model.Cart
import com.example.fashionecommercemobileapp.retrofit.repository.CartRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers


class CartViewModel : ViewModel() {
    private var cartData: MutableLiveData<Cart>? = null

    private var cartRepository: CartRepository? = null

    fun init() {
        if (cartData != null) {
            return
        }
        cartRepository = CartRepository()
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
}