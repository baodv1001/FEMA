package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.Address
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.AddressApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressRepository {
    private var listAddress: MutableLiveData<List<Address>>? = MutableLiveData<List<Address>>()
    fun setAddressData(addressData: List<Address>) {
        listAddress?.value = addressData
    }

    fun getAddressData(): MutableLiveData<List<Address>>? {
        return listAddress
    }
    private  var addressApi: AddressApi? = null

    companion object {
        private var addressRepository: AddressRepository? = null
        val instance: AddressRepository?
            get() {
                if (addressRepository == null) {
                    addressRepository = AddressRepository()
                }
                return  addressRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    fun doAddressRequest(idAccount: String) {
        val callAddress: Call<List<Address>> = addressApi!!.getAddressInfoData(idAccount)
        callAddress.enqueue(object : Callback<List<Address>> {
            override fun onResponse(call: Call<List<Address>>, response: Response<List<Address>>) {
                response.body()?.let { setAddressData(it) }
            }

            override fun onFailure(call: Call<List<Address>>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    init {
        var retrofit: RetrofitClient = RetrofitClient()
        addressApi = retrofit.getRetrofitInstance()!!.create(AddressApi::class.java)
    }
}