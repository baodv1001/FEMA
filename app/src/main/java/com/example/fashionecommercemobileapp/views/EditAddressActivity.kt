package com.example.fashionecommercemobileapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.repository.AddressRepository
import com.example.fashionecommercemobileapp.viewmodels.AddressViewModel
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_edit_address.*
import kotlinx.android.synthetic.main.activity_edit_address.text_input_Address
import kotlinx.android.synthetic.main.activity_edit_address.text_input_Phone_Number
import kotlinx.android.synthetic.main.activity_edit_address.text_input_Receiver_Name

class EditAddressActivity : AppCompatActivity() {
    private var addressViewModel: AddressViewModel? = null
    private var idAddress: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)

        AddressRepository.Companion.setContext(this@EditAddressActivity)
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        addressViewModel!!.init()

        val intent: Intent = intent
        val name = intent.getStringExtra("name")
        val address = intent.getStringExtra("address")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val idAddress = intent.getIntExtra("idAddress", 0)

        text_input_Receiver_Name.setText(name)
        text_input_Address.setText(address)
        text_input_Phone_Number.setText(phoneNumber)

        button_edit_address.setOnClickListener {
            val nameAfter: String = text_input_Receiver_Name.text.toString()
            val addressAfter: String = text_input_Address.text.toString()
            val phoneNumberAfter: String = text_input_Phone_Number.text.toString()

            if (checkTextField()) {
                addressViewModel!!.updateAddressInfo(idAddress, nameAfter, addressAfter, phoneNumberAfter)
                Toast.makeText(this,"Save successfully!", Toast.LENGTH_SHORT).show()
                super.onBackPressed()
            }
        }

    }

    private fun checkTextField(): Boolean {
        if (text_input_Receiver_Name.text.toString().isEmpty() ||
            text_input_Address.text.toString().isEmpty() ||
            text_input_Phone_Number.text.toString().isEmpty()) {
            Toast.makeText(this@EditAddressActivity, "Field must be fill...!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun onClickBack(view: View) {
        Toast.makeText(this, "Change wil not be saved!", Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }
}


