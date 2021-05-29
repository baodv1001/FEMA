package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.Color
import com.example.fashionecommercemobileapp.model.Size
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.ColorApi
import com.example.fashionecommercemobileapp.retrofit.api.SizeApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductInfoRepository {
    private var listSize: MutableLiveData<List<Size>>? = MutableLiveData<List<Size>>()
    private var listColor: MutableLiveData<List<Color>>? = MutableLiveData<List<Color>>()
    fun setSizeData(sizeData: List<Size>) {
        listSize?.value = sizeData
    }

    fun getSizeData(): MutableLiveData<List<Size>>? {
        return listSize
    }
    fun setColorData(colorData: List<Color>) {
        listColor?.value = colorData
    }

    fun getColorData(): MutableLiveData<List<Color>>? {
        return listColor
    }
    private  var sizeApi: SizeApi? = null
    private var colorApi: ColorApi? = null
    companion object {
        private var productInfoRepository: ProductInfoRepository? = null
        val instance: ProductInfoRepository?
            get() {
                if (productInfoRepository == null) {
                    productInfoRepository = ProductInfoRepository()
                }
                return  productInfoRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    suspend fun doSizeRequest() = sizeApi?.getSize()
    suspend fun doColorRequest() = colorApi?.getColor()

    init {
        var retrofit: RetrofitClient = RetrofitClient()
        sizeApi = retrofit.getRetrofitInstance()!!.create(SizeApi::class.java)
        colorApi = retrofit.getRetrofitInstance()!!.create(ColorApi::class.java)
    }
}