package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.model.Address
import com.example.fashionecommercemobileapp.retrofit.repository.AddressRepository

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
}