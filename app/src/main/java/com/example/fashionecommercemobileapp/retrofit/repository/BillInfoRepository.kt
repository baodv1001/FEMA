package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import com.example.fashionecommercemobileapp.model.BillInfo
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.BillInfoApi

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
        billInfoApi?.createBillInfo(
            billInfo.idBill,
            billInfo.idProduct,
            billInfo.idSize,
            billInfo.idColor,
            billInfo.quantity
        )

    suspend fun getBillInfo(idBill: String) = billInfoApi?.getBillInfo(idBill)

    init {
        val retrofit: RetrofitClient = RetrofitClient()
        billInfoApi = retrofit.getRetrofitInstance()!!.create(BillInfoApi::class.java)
    }
}
