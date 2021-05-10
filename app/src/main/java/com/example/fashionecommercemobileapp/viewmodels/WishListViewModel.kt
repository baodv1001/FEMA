package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.model.Product
import com.example.fashionecommercemobileapp.retrofit.repository.WishListRepository

class WishListViewModel : ViewModel() {
    private  var wishListRepository : WishListRepository? = null
    private var wishListProduct: MutableLiveData<List<Product>>? = null

    fun init()
    {
        if (wishListProduct != null)
        {
            return
        }
        wishListRepository = WishListRepository()
    }

    fun getWishListProductData(idAccount: Int) : LiveData<List<Product>>?
    {
        wishListRepository!!.doWishListProductRequest(idAccount)
        wishListProduct = wishListRepository?.getWishListProductData()
        return wishListProduct
    }

    fun deleteWishProduct(idAccount: Int, idProduct : Int)
    {
        wishListRepository!!.doDeleteWishProduct(idAccount, idProduct)
    }
}