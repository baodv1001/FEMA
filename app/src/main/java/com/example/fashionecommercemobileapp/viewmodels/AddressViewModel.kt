package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.model.Address
import com.example.fashionecommercemobileapp.retrofit.repository.AddressRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AddressViewModel : ViewModel() {
    private var addressData: MutableLiveData<List<Address>>? = null
    private var addressRepository: AddressRepository? = null

    public fun init() {
        if (addressData != null) {
            return
        }
        addressRepository = AddressRepository()
    }

    fun getAddressData(idAccount: String): LiveData<List<Address>>? {
        addressRepository!!.doAddressRequest(idAccount)
        addressData = addressRepository?.getAddressData()
        return  addressData
    }
    fun addAddressInfo(idAccount: Int, name: String, address: String, phoneNumber: String)
    {
        addressRepository!!.doAddAddressInfoRequest(idAccount, name, address, phoneNumber)
    }
    fun delAddressInfo(idAccount: Int, name: String, address: String, phoneNumber: String)
    {
        addressRepository!!.doDelAddressInfoRequest(idAccount, name, address, phoneNumber)
    }
    fun updateAddressInfo(idAddress: Int, name: String, address: String, phoneNumber: String)
    {
        addressRepository!!.doUpdateAddressInfoRequest(idAddress, name, address, phoneNumber)
    }
}