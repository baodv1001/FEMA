package com.example.fashionecommercemobileapp.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.CheckOutItemAdapter
import com.example.fashionecommercemobileapp.model.*
import com.example.fashionecommercemobileapp.retrofit.repository.*
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.viewmodels.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class CheckOutActivity : AppCompatActivity() {
    private var cartInfoList: ArrayList<CartInfo> = arrayListOf()
    private var productList: ArrayList<Product> = arrayListOf()
    private var sizeList: ArrayList<Size> = arrayListOf()
    private var colorList: ArrayList<Color> = arrayListOf()
    private var idAccount: Int = 0
    private var isViewing: Boolean = false

    private lateinit var checkOutItemAdapter: CheckOutItemAdapter
    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartInfoViewModel: CartInfoViewModel
    private lateinit var addressViewModel: AddressViewModel
    private lateinit var productInfoViewModel: ProductInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        CartRepository.Companion.setContext(this@CheckOutActivity)
        CartInfoRepository.Companion.setContext(this@CheckOutActivity)
        ProductRepository.Companion.setContext(this@CheckOutActivity)
        ProductInfoRepository.Companion.setContext(this@CheckOutActivity)
        AddressRepository.setContext(this@CheckOutActivity)

        val intent: Intent = intent
        idAccount = intent.getIntExtra("idAccount", 0)
        isViewing = intent.getBooleanExtra("isViewing", false)
        textView_discount_checkOut.text = intent.getStringExtra("discount")

        setUpViewModel()
        setUpRecyclerView()
        setUpCartInfoObservers(idAccount)
        getAddress()

        if (isViewing) {
            button_confirm.visibility = View.GONE
            button_select_address.visibility = View.GONE
        }
    }

    private fun setUpViewModel() {
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        cartViewModel.init()

        cartInfoViewModel = ViewModelProviders.of(this).get(CartInfoViewModel::class.java)
        cartInfoViewModel.init()

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.init()

        productInfoViewModel = ViewModelProviders.of(this).get(ProductInfoViewModel::class.java)
        productInfoViewModel.init()

        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        addressViewModel.init()
    }

    private fun setUpRecyclerView() {
        checkOutItemAdapter = CheckOutItemAdapter(
            this, arrayListOf(), arrayListOf(),
            sizeList, colorList
        )
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_checkOut.layoutManager = layoutManager
        recyclerView_checkOut.adapter = checkOutItemAdapter
    }

    private fun setUpCartInfoObservers(idCart: Int) {
        cartInfoViewModel.getCartInfo(idCart).observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        cartInfoList = (it.data as ArrayList<CartInfo>?)!!
                        setUpProductObservers()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        //                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun setUpProductObservers() {
        val list: ArrayList<String> = arrayListOf()
        cartInfoList.forEach { info ->
            list.add(info.idProduct.toString())
        }

        productViewModel.getProduct(list).observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        productList = it.data as ArrayList<Product>
                        loadData(this.cartInfoList, this.productList)
                        loadSize()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun retrieveList(
        cartList: ArrayList<CartInfo>,
        productList: ArrayList<Product>,
        sizeList: List<Size>,
        colorList: List<Color>
    ) {
        checkOutItemAdapter.apply {
            changeData(cartList, productList, sizeList, colorList)
            notifyDataSetChanged()
        }
        progressBar_checkOut.visibility = View.GONE
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
                            this.cartInfoList,
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

    private fun loadData(cartInfoList: List<CartInfo>, productList: List<Product>) {
        var subTotal: Int = 0
        for (i in cartInfoList.indices) {
            subTotal += cartInfoList[i].quantity!! * (productList[i].price?.toInt() ?: 0)
        }
        textView_sub_checkOut.text =
            NumberFormat.getIntegerInstance(Locale.GERMANY).format(subTotal)
        val total: Int =
            subTotal - textView_discount_checkOut.text.toString().replace(".", "").toInt()
        textView_total_checkOut.text = NumberFormat.getIntegerInstance(Locale.GERMANY).format(total)
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }

    fun onClickConfirm(view: View) {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = simpleDateFormat.format(Date())
        val idAddress: Int = textView_idAddress_checkOut.text.toString().toInt()
        val bill: Bill = Bill(
            0,
            idAccount,
            date,
            0,
            idAddress,
            textView_total_checkOut.text.toString().replace(".", "").toInt()
        )
        val billViewModel: BillViewModel =
            ViewModelProviders.of(this).get(BillViewModel::class.java)
        billViewModel.init()
        billViewModel.createBill(bill).observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        it.data?.let { idBill -> createBillInfo(idBill) }
                        Toast.makeText(this, "Check out successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java).apply { }
                        startActivity(intent)
                        this.finish()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun createBillInfo(idBill: Int) {
        var isSuccess: Boolean = true
        cartInfoList.forEach {cartInfo ->
            val billInfo: BillInfo =
                BillInfo(idBill, cartInfo.idProduct!!, cartInfo.idSize!!, cartInfo.idColor!!, cartInfo.quantity!!)
            val billInfoViewModel: BillInfoViewModel =
                ViewModelProviders.of(this).get(BillInfoViewModel::class.java)
            billInfoViewModel.init()
            billInfoViewModel.createBillInfo(billInfo).observe(this, Observer { it ->
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (it.data == false) {
                                isSuccess = false
                            }
                            else {
                                cartInfoViewModel.deleteCartInfo(
                                    idAccount,
                                    billInfo.idProduct!!,
                                    cartInfo.idSize!!,
                                    cartInfo.idColor!!
                                )
                                productViewModel.updateProduct(billInfo.idProduct.toString(), billInfo.quantity!!)
                            }
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
        if (isSuccess) {
            Toast.makeText(this, "Check out successfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickSelectAddress(view: View) {
        if (!isViewing) {
            val intent = Intent(this, AddressActivity::class.java).apply {
                putExtra("idAccount", idAccount)
                putExtra("isCheckOut", true)
            }
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val idAddress = data?.getStringExtra("idAddress")
            val name = data?.getStringExtra("name")
            val address = data?.getStringExtra("address")
            val phoneNumber = data?.getStringExtra("phoneNumber")
            loadAddress(idAddress, name, address, phoneNumber)
        }
    }

    private fun getAddress() {
        addressViewModel.getAddressData(idAccount.toString())?.observe(this, Observer {
            if (it.isNotEmpty()) {
                loadAddress(
                    it[0].idAddress.toString(),
                    it[0].name,
                    it[0].address,
                    it[0].phoneNumber
                )
            } else {
                Toast.makeText(this, "Please add your address!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadAddress(
        idAddress: String?,
        name: String?,
        address: String?,
        phoneNumber: String?
    ) {
        textView_idAddress_checkOut.text = idAddress
        textView_name_checkOut.text = name
        textView_address_checkOut.text = address
        textView_phone_checkOut.text = phoneNumber
    }
}