package com.example.fashionecommercemobileapp.views

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.*
import androidx.lifecycle.ViewModelProviders
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.repository.AddressRepository
import com.example.fashionecommercemobileapp.viewmodels.AddressViewModel
import kotlinx.android.synthetic.main.activity_add_address.*

class AddAddressActivity : AppCompatActivity() {
    private var addressViewModel: AddressViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        AddressRepository.Companion.setContext(this@AddAddressActivity)
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        addressViewModel!!.init()

        val sp: SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val idAccount = sp.getString("Id", "").toString()

        button_choose.setOnClickListener {

            val name: String = text_input_Receiver_Name.text.toString()
            val address: String = text_input_Address.text.toString()
            val phoneNumber: String = text_input_Phone_Number.text.toString()

            if (checkTextField()) {
                addressViewModel!!.addAddressInfo(idAccount.toInt(), name, address, phoneNumber)
                Toast.makeText(this@AddAddressActivity, "Add address successfully!", LENGTH_SHORT)
                    .show()
                super.onBackPressed()
            }
        }


        button_back.setOnClickListener {
            Toast.makeText(this@AddAddressActivity, "Changes will not be saved!", LENGTH_SHORT)
                .show()
            super.onBackPressed()
        }
    }

    private fun checkTextField(): Boolean {
        if (text_input_Receiver_Name.text.toString().isEmpty() ||
            text_input_Address.text.toString().isEmpty() ||
            text_input_Phone_Number.text.toString().isEmpty()
        ) {
            Toast.makeText(this@AddAddressActivity, "Field must be fill...!", LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }
}