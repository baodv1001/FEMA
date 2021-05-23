package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.model.Bill
import com.example.fashionecommercemobileapp.retrofit.repository.BillRepository

class OrderViewModel : ViewModel(){
    private var billData: MutableLiveData<List<Bill>>? = null
    private var billRepository: BillRepository? = null

    fun init() {
        if(billData != null) {
            return
        }
        billRepository = BillRepository()
    }

    fun getBillData(idAccount: String):LiveData<List<Bill>>? {
        billRepository!!.doBillRequest(idAccount)
        billData = billRepository?.getBillData()
        return billData
    }
}