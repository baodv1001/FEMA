package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.Product
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductApi {

    @POST("Product/get_product.php")
    @FormUrlEncoded
    fun getProduct(@Field("idProductCode") idProductCode: String): Call<List<Product>>

    @POST("Product/get_product_by_name.php")
    @FormUrlEncoded
    fun getProductByName(
        @Field("name") name: String,
        @Field("idProductCode") idProductCode: String
    ): Call<List<Product>>

    @POST("Product/get_product_by_rating_price.php")
    @FormUrlEncoded
    fun getProductByRating(
        @Field("rating") rating: String,
        @Field("idProductCode") idProductCode: String,
        @Field("minPrice") minPrice: String,
        @Field("maxPrice") maxPrice: String,
        @Field("discount") discount: String
    ): Call<List<Product>>

    @GET("Product/get_recommended.php")
    fun getRecommended(): Call<List<Product>>

    @GET("Product/get_flash_sale.php")
    fun getFlashSale(): Call<List<Product>>

    @POST("Product/get_product_by_id.php")
    @FormUrlEncoded
    suspend fun getProductById(@Field("idProductList") idProductList: String): List<Product>

    @GET("Product/get_all_flash_sale.php")
    fun getAllFlashSale(): Call<List<Product>>

    @POST("Product/update_product.php")
    @FormUrlEncoded
    fun updateProduct(
        @Field("idProduct") idProduct: String,
        @Field("quantity") quantity: Int
    ): Call<Boolean>

    @POST("Product/update_product_list.php")
    @FormUrlEncoded
    fun updateProducts(
        @Field("idProductList") idProductList: String,
        @Field("quantity") quantity: String
    ): Call<Boolean>

    @POST("Product/update_product_rating.php")
    @FormUrlEncoded
    fun updateProductRating(
        @Field("idProductList") idProductList: String,
        @Field("rating") rating: String
    ): Call<Boolean>
}