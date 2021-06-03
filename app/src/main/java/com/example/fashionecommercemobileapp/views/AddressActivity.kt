package com.example.fashionecommercemobileapp.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    lateinit var addressRecyclerView : RecyclerView
    private var addressViewModel : AddressViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        val sp1 = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val idAccount = sp1.getString("Id", null)

        AddressRepository.setContext(this@AddressActivity)
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        addressViewModel!!.init()

        addressViewModel!!.getAddressData(idAccount.toString())?.observe(this, Observer { setUpAddressRecyclerView(it) })
    }

    private fun setUpAddressRecyclerView(listAddress: List<Address>) {
        addressRecyclerView = findViewById(R.id.address_recycler)
        val addressAdapter: AddressAdapter = AddressAdapter(this, listAddress)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false)
        addressRecyclerView.layoutManager = layoutManager
        addressRecyclerView.adapter = addressAdapter
    }
    fun onClickBack(view: View) {
        super.onBackPressed()
    }
}