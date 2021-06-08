package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.model.Bill
import com.example.fashionecommercemobileapp.retrofit.repository.BillRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers

class BillViewModel : ViewModel() {
    private var billData: MutableLiveData<List<Bill>>? = null
    private var billRepository: BillRepository? = null

    fun init() {
        if (billData != null) {
            return
        }
        billRepository = BillRepository()
    }

    fun createBill(bill: Bill) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = billRepository?.createBill(bill)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }

    fun getBillData(idAccount: String): LiveData<List<Bill>>? {
        billRepository!!.doBillRequest(idAccount)
        billData = billRepository?.getBillData()
        return billData
    }

    fun updateBill(idBill: String) {
        billRepository!!.updateBill(idBill)
    }

    fun updateBillRated(idBill: String) {
         billRepository!!.updateBillRated(idBill)
    }
}