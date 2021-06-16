package com.example.fashionecommercemobileapp.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

    var idAccount : String = ""
    private lateinit var addressAdapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        val sp: SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        idAccount = sp.getString("Id", "").toString()


        AddressRepository.setContext(this@AddressActivity)
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        addressViewModel!!.init()

        addressViewModel!!.getAddressData(idAccount.toString())?.observe(this, Observer { setUpAddressRecyclerView(it) })
        //add address
        add_address_button.setOnClickListener {
            val intent = Intent(this, AddAddressActivity::class.java).apply { }
                intent.putExtra("idAccount",idAccount)
            (this as Activity).startActivityForResult(intent, 0)

            addressAdapter.notifyDataSetChanged()
        }
    }

    private fun setUpAddressRecyclerView(listAddress: List<Address>) {
        addressRecyclerView = findViewById(R.id.address_recycler)
        addressAdapter = AddressAdapter(this, listAddress.toMutableList(), addressViewModel, idAccount)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false)
        addressRecyclerView.layoutManager = layoutManager
        addressRecyclerView.adapter = addressAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        addressViewModel!!.getAddressData(idAccount.toString())?.observe(this, Observer { setUpAddressRecyclerView(it) })
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }
}