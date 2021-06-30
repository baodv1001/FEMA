package com.example.fashionecommercemobileapp.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.OrderDetailsAdapter
import com.example.fashionecommercemobileapp.model.*
import com.example.fashionecommercemobileapp.retrofit.repository.*
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.viewmodels.*
import com.kofigyan.stateprogressbar.StateProgressBar
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.activity_product_details.*
import java.text.NumberFormat
import java.util.*


class OrderDetailsActivity : AppCompatActivity() {
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter

    private var billInfoList: ArrayList<BillInfo> = arrayListOf()
    private var productList: ArrayList<Product> = arrayListOf()
    private var sizeList: ArrayList<Size> = arrayListOf()
    private var colorList: ArrayList<Color> = arrayListOf()
    private var language: String = ""

    private lateinit var billViewModel: BillViewModel
    private lateinit var billInfoViewModel: BillInfoViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productInfoViewModel: ProductInfoViewModel
    private lateinit var addressViewModel: AddressViewModel

    private var idBill: String = "0"
    private var idAddress: Int = 0
    private var idAccount: String = "0"
    private var date: String = "0"
    private var total: Int = 0
    private var status: Int = 0
    private var isRated: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        language = sharedPreferences.getString("My_Lang", "").toString()

        BillRepository.Companion.setContext(this@OrderDetailsActivity)
        BillInfoRepository.Companion.setContext(this@OrderDetailsActivity)
        ProductRepository.Companion.setContext(this@OrderDetailsActivity)
        ProductInfoRepository.Companion.setContext(this@OrderDetailsActivity)
        AddressRepository.setContext(this@OrderDetailsActivity)

        val intent: Intent = intent
        idBill = intent.getIntExtra("idBill", 0).toString()
        idAddress = intent.getIntExtra("idAddress", 0)
        idAccount = intent.getIntExtra("idAccount", 0).toString()
        date = intent.getStringExtra("date").toString()
        total = intent.getIntExtra("total", 0)
        status = intent.getIntExtra("status", 0)
        isRated = intent.getIntExtra("isRated", 0)

        setUpViewModel()
        setUpRecyclerView()
        setUpBillInfoObservers(idBill)
        getAddress()

        var descriptionData: Array<String>
        if (language == "en")
            descriptionData = arrayOf("Unconfirmed", "Shipping", "Done")
        else
            descriptionData = arrayOf("Chưa xác nhận", "Đang giao", "Hoàn thành")
        val stateProgressBar =
            findViewById<View>(R.id.stateProgressBar) as StateProgressBar
        stateProgressBar.setStateDescriptionData(descriptionData)
        when (status) {
            0 -> {
                button_cancel.visibility = View.VISIBLE
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
            }
            1 -> stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO)
            2 -> {
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE)
                if (isRated == 0) {
                    ratingBar_order.visibility = View.VISIBLE
                    textView_review.visibility = View.VISIBLE
                    button_add_review.visibility = View.VISIBLE
                }
            }
            3 -> stateProgressBar.visibility = View.GONE
        }
    }

    private fun setUpViewModel() {
        billViewModel = ViewModelProviders.of(this).get(BillViewModel::class.java)
        billViewModel.init()

        billInfoViewModel = ViewModelProviders.of(this).get(BillInfoViewModel::class.java)
        billInfoViewModel.init()

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.init()

        productInfoViewModel = ViewModelProviders.of(this).get(ProductInfoViewModel::class.java)
        productInfoViewModel.init()

        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        addressViewModel.init()
    }

    private fun setUpRecyclerView() {
        orderDetailsAdapter =
            OrderDetailsAdapter(this, arrayListOf(), arrayListOf(), sizeList, colorList, language)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_orderDetails.layoutManager = layoutManager
        recyclerView_orderDetails.adapter = orderDetailsAdapter
    }

    private fun setUpBillInfoObservers(idBill: String) {
        billInfoViewModel.getBillInfo(idBill).observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        billInfoList = (it.data as ArrayList<BillInfo>?)!!
                        setUpProductObservers()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun setUpProductObservers() {
        val list: ArrayList<String> = arrayListOf()
        billInfoList.forEach { info ->
            list.add(info.idProduct.toString())
        }

        productViewModel.getProduct(list).observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        productList = it.data as ArrayList<Product>
                        loadData(this.billInfoList, this.productList)
                        loadSize()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun loadSize() {
        productInfoViewModel.getSizeData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        sizeList = it.data as ArrayList<Size>
                        loadColor()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun loadColor() {
        productInfoViewModel.getColorData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        colorList = it.data as ArrayList<Color>
                        retrieveList(
                            this.billInfoList,
                            this.productList,
                            this.sizeList,
                            this.colorList
                        )
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun retrieveList(
        cartList: ArrayList<BillInfo>,
        productList: ArrayList<Product>,
        sizeList: List<Size>,
        colorList: List<Color>
    ) {
        orderDetailsAdapter.apply {
            changeData(cartList, productList, sizeList, colorList)
            notifyDataSetChanged()
        }
    }

    private fun loadData(billInfoList: List<BillInfo>, productList: List<Product>) {
        var subTotal: Float = 0F
        for (i in billInfoList.indices) {
            subTotal += (billInfoList[i].quantity?.toFloat() ?: 0F) * (billInfoList[i].price?.toFloat() ?: 0F)
        }
        textView_sub_orderDetails.text =
            NumberFormat.getIntegerInstance(Locale.GERMANY).format(subTotal)
        if (subTotal.toInt() == total) {
            textView_discount_orderDetails.text = "0"
        } else {
            textView_discount_orderDetails.text =
                NumberFormat.getIntegerInstance(Locale.GERMANY).format(subTotal - total)
        }
        textView_total_orderDetails.text =
            NumberFormat.getIntegerInstance(Locale.GERMANY).format(total)
    }

    private fun getAddress() {
        addressViewModel.getAddressData(idAccount)?.observe(this, Observer {
            if (it.isNotEmpty()) {
                it.forEach { address ->
                    if (address.idAddress == idAddress) {
                        loadAddress(
                            address.idAddress,
                            address.name,
                            address.address,
                            address.phoneNumber
                        )
                        return@forEach
                    }
                }
            } else {
                if (language == "en")
                    Toast.makeText(this, "Please add your address!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Vui lòng chọn địa chỉ!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadAddress(
        idAddress: Int?,
        name: String?,
        address: String?,
        phoneNumber: String?
    ) {
        textView_idAddress_orderDetails.text = idAddress.toString()
        textView_name_orderDetails.text = name
        textView_address_orderDetails.text = address
        textView_phone_orderDetails.text = phoneNumber
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }

    fun onClickCancel(view: View) {
        billViewModel.updateBill(idBill)
        val idProductList: ArrayList<String> = arrayListOf()
        val quantityList: ArrayList<String> = arrayListOf()
        billInfoList.forEach { info ->
            idProductList.add(info.idProduct.toString())
            quantityList.add(info.quantity.toString())
        }
        productViewModel.updateProducts(idProductList, quantityList)
        if (language == "en")
            Toast.makeText(this, "Canceled successfully!", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Hủy thành công!", Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }

    fun onClickAddReview(view: View) {
        val idProductList: ArrayList<String> = arrayListOf()
        billInfoList.forEach { info ->
            idProductList.add(info.idProduct.toString())
        }
        billViewModel.updateBillRated(idBill)
        productViewModel.updateProductRating(idProductList, ratingBar_order.rating.toString())
        isRated = 1
        button_add_review.visibility = View.GONE
        if (language == "en")
            Toast.makeText(this, "Added review successfully!", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Thêm đánh giá thành công!", Toast.LENGTH_SHORT).show()
    }
}