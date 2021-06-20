package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.retrofit.repository.CartInfoRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers


class CartInfoViewModel : ViewModel() {
    private var cartInfoData: MutableLiveData<List<CartInfo>>? = null

    private var cartInfoRepository: CartInfoRepository? = null

    fun init() {
        if (cartInfoData != null) {
            return
        }
        cartInfoRepository = CartInfoRepository()
    }

    fun postCartInfo(idCart: Int, idProduct: Int, idSize: Int, idColor: Int, quantity: Int) {
        cartInfoRepository!!.postCartInfo(idCart, idProduct, idSize, idColor, quantity)
    }

    fun deleteCartInfo(idCart: Int, idProduct: Int, idSize: Int, idColor: Int) {
        cartInfoRepository!!.deleteCartInfo(idCart, idProduct, idSize, idColor)
    }

    fun updateCartInfo(idCart: Int, idProduct: Int, idSize: Int, idColor: Int, quantity: Int) {
        cartInfoRepository!!.updateCartInfo(idCart, idProduct, idSize, idColor, quantity)
    }

    fun getCartInfo(idCart: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = cartInfoRepository?.getCartInfoRequest(idCart)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }

    fun getCartInfoByProduct(idCart: Int, idProduct: Int, idSize: Int, idColor: Int) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(
                    Resource.success(
                        data = cartInfoRepository?.getCartInfoByProduct(
                            idCart,
                            idProduct,
                            idSize,
                            idColor
                        )
                    )
                )
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error"))
            }
        }
}