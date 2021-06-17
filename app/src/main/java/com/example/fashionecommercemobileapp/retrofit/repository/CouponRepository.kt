package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.Cart
import com.example.fashionecommercemobileapp.model.CartInfo
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import com.example.fashionecommercemobileapp.retrofit.api.CartApi
import com.example.fashionecommercemobileapp.retrofit.api.CouponApi
import com.example.fashionecommercemobileapp.retrofit.api.ProductApi
import com.example.fashionecommercemobileapp.retrofit.api.WishListApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CouponRepository {
    private var apiCoupon: CouponApi? = null

    companion object {
        private var CouponRepository: CouponRepository? = null
        val instance: CouponRepository?
            get() {
                if (CouponRepository == null) {
                    CouponRepository = CouponRepository()
                }
                return CouponRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    suspend fun getCoupon(code: String) = apiCoupon?.getCoupon(code)

    init {
        var retrofit: RetrofitClient = RetrofitClient()
        apiCoupon = retrofit.getRetrofitInstance()!!.create(CouponApi::class.java)
    }
}