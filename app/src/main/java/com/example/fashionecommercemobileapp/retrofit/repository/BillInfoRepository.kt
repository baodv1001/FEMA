package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.Bill
import com.example.fashionecommercemobileapp.model.BillInfo
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.BillApi
import com.example.fashionecommercemobileapp.retrofit.api.BillInfoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillInfoRepository {
    private var billInfoApi: BillInfoApi? = null

    companion object {
        private var billInfoRepository: BillInfoRepository? = null
        val instance: BillInfoRepository?
            get() {
                if (billInfoRepository == null) {
                    billInfoRepository = BillInfoRepository()
                }
                return billInfoRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    suspend fun createBillInfo(billInfo: BillInfo) =
        billInfoApi?.createBillInfo(billInfo.idBill, billInfo.idProduct, billInfo.quantity)

    init {
        val retrofit: RetrofitClient = RetrofitClient()
        billInfoApi = retrofit.getRetrofitInstance()!!.create(BillInfoApi::class.java)
    }
}
