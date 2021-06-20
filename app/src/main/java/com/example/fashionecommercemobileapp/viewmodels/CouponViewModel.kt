package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.model.Cart
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.model.ShaPW
import com.example.fashionecommercemobileapp.retrofit.repository.CartRepository
import com.example.fashionecommercemobileapp.retrofit.repository.CouponRepository
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers


class CouponViewModel : ViewModel() {

    private var couponRepository: CouponRepository? = null

    fun init() {
        couponRepository = CouponRepository()
    }

    fun getCoupon(code: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = couponRepository?.getCoupon(code)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }
}