package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.model.*
import com.example.fashionecommercemobileapp.retrofit.repository.BillInfoRepository
import com.example.fashionecommercemobileapp.retrofit.repository.CartRepository
import com.example.fashionecommercemobileapp.retrofit.repository.CouponRepository
import com.example.fashionecommercemobileapp.retrofit.repository.ProductRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers


class BillInfoViewModel : ViewModel() {

    private var billInfoRepository: BillInfoRepository? = null

    fun init() {
        billInfoRepository = BillInfoRepository()
    }

    fun createBillInfo(billInfo: BillInfo) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = billInfoRepository?.createBillInfo(billInfo)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }

    fun getBillInfo(idBill: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = billInfoRepository?.getBillInfo(idBill)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }
}