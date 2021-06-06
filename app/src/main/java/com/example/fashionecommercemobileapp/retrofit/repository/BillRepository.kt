package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.Bill
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.BillApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillRepository {
    private var listBill: MutableLiveData<List<Bill>>? = MutableLiveData<List<Bill>>()
    private var billApi: BillApi? = null

    fun setBillData(billData: List<Bill>) {
        listBill?.value = billData
    }

    fun getBillData(): MutableLiveData<List<Bill>>? {
        return listBill
    }

    companion object {
        private var billRepository: BillRepository? = null
        val instance: BillRepository?
            get() {
                if (billRepository == null) {
                    billRepository = BillRepository()
                }
                return billRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    suspend fun createBill(bill: Bill) = billApi?.createBill(
        bill.idAddress,
        bill.idAccount,
        bill.invoiceDate,
        bill.status,
        bill.totalMoney
    )

    fun doBillRequest(idAccount: String) {
        val callBill: Call<List<Bill>> = billApi!!.getBillData(idAccount)
        callBill.enqueue(object : Callback<List<Bill>> {
            override fun onResponse(call: Call<List<Bill>>, response: Response<List<Bill>>) {
                response.body()?.let { setBillData(it) }
            }

            override fun onFailure(call: Call<List<Bill>>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    init {
        var retrofit: RetrofitClient = RetrofitClient()
        billApi = retrofit.getRetrofitInstance()!!.create(BillApi::class.java)
    }
}
