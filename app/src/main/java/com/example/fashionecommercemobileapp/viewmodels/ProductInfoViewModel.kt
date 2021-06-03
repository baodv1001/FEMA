package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.model.Color
import com.example.fashionecommercemobileapp.model.ShaPW
import com.example.fashionecommercemobileapp.model.Size
import com.example.fashionecommercemobileapp.retrofit.repository.ProductInfoRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers

class ProductInfoViewModel : ViewModel() {
    private var sizeData: MutableLiveData<List<Size>>? = null
    private var colorData: MutableLiveData<List<Color>>? = null
    private var productInfoRepository: ProductInfoRepository? = null

    public fun init() {
        if (sizeData != null) {
            return
        }
        productInfoRepository = ProductInfoRepository()
    }

    fun getSizeData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = productInfoRepository!!.doSizeRequest()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getColorData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = productInfoRepository!!.doColorRequest()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}