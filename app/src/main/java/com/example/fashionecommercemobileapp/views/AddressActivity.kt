package com.example.fashionecommercemobileapp.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.AddressAdapter
import com.example.fashionecommercemobileapp.model.Address
import com.example.fashionecommercemobileapp.retrofit.repository.AddressRepository
import com.example.fashionecommercemobileapp.viewmodels.AddressViewModel
import kotlinx.android.synthetic.main.activity_address.*

class AddressActivity : AppCompatActivity() {
    private var addressViewModel: AddressViewModel? = null
    private lateinit var addressAdapter: AddressAdapter
    private var isCheckOut: Boolean = false
    private lateinit var isSelected: LiveData<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        val intent: Intent = intent
        val idAccount: Int = intent.getIntExtra("idAccount", 0)
        isCheckOut = intent.getBooleanExtra("isCheckOut", false)

        setUpAddressRecyclerView()

        AddressRepository.setContext(this@AddressActivity)
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        addressViewModel!!.init()
        addressViewModel!!.getAddressData(idAccount.toString())
            ?.observe(this, Observer { retrieveList(it as ArrayList<Address>) })


        isSelected = addressAdapter.getSate()
        val observer = Observer<Boolean> { it ->
            if (it) {
                onBackPressed()
            }
        }
        isSelected.observe(this, observer)

    }

    private fun setUpAddressRecyclerView() {
        addressAdapter = AddressAdapter(this, arrayListOf(), isCheckOut)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
        address_recycler.layoutManager = layoutManager
        address_recycler.adapter = addressAdapter
    }

    fun retrieveList(listAddress: ArrayList<Address>) {
        addressAdapter.changeData(listAddress)
    }

    override fun onBackPressed() {
        val intent: Intent = Intent()
        val address: MutableLiveData<Address> = addressAdapter.getAddress()
        intent.putExtra("name", address.value?.name)
        intent.putExtra("address", address.value?.address)
        intent.putExtra("phoneNumber", address.value?.phoneNumber)
        setResult(RESULT_OK, intent)
        finish()
    }
//    override fun onBackPressed(view: View) {
//        val intent: Intent = Intent()
//        intent.putExtra("mess", "test")
//        setResult(RESULT_OK, intent)
//        finish()
//    }
}