package com.example.fashionecommercemobileapp.views

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.repository.AccountRepository
import com.example.fashionecommercemobileapp.viewmodels.UserViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_change_phone_number.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.concurrent.TimeUnit

class ChangePhoneNumberActivity : AppCompatActivity() {
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVertificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private var phoneNumberSignUpSuccess: String? = null

    private lateinit var progressDialog: ProgressDialog

    private var userViewModel : UserViewModel? = null
    var language: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_phone_number)

        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        language = sharedPreferences.getString("My_Lang", "").toString()

        AccountRepository.Companion.setContext(this@ChangePhoneNumberActivity)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel!!.init()

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                //progressDialog.dismiss()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                progressDialog.dismiss()
                if (language == "en")
                    Toast.makeText(this@ChangePhoneNumberActivity, "Check your phone number", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@ChangePhoneNumberActivity, "Kiểm tra số điện thoại", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, p1)
                mVertificationId = verificationId
                forceResendingToken = p1

                button_reSend.visibility = View.VISIBLE
                button_Send.visibility = View.INVISIBLE
                button_choose.isEnabled = true
                progressDialog.dismiss()
            }
        }

        button_Send.setOnClickListener() {
            var phone: String = text_input_Phone_Number.text.toString()
            if (checkTextField()) {
                userViewModel?.checkPhoneNumber(phone)?.observe(this, Observer {
                    if (it == false) {
                        if (phone[0] == '0') {
                            phone = phone.drop(1)
                            phone = "+84" + phone
                        }
                        sendVerificationCode(phone)}
                    else {
                        if (language == "en")
                            Toast.makeText(this, "phone number already in use",Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this, "Số điện thoại đã được sử dụng", Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }


        button_reSend.setOnClickListener() {
            var phone: String = text_input_Phone_Number.text.toString()
            if (checkTextField()) {
                userViewModel?.checkPhoneNumber(phone)?.observe(this, Observer {
                    if (it == false) {

                        if (phone[0] == '0') {
                            phone = phone.drop(1)
                            phone = "+84" + phone
                        }
                        reSendVerificationCode(phone, forceResendingToken)
                    }
                    else {
                        if (language == "en")
                            Toast.makeText(this, "phone number already in use",Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this, "Số điện thoại đã được sử dụng", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        button_choose.setOnClickListener {
            if (text_input_OTP.text.toString().isEmpty())
            {
                if (language == "en")
                    Toast.makeText(this@ChangePhoneNumberActivity, "Please enter your verification code...", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@ChangePhoneNumberActivity, "Vui lòng nhập mã xác nhận...", Toast.LENGTH_SHORT).show()
            }
            else
            {
                if (checkTextField()) {
                    verifyPhoneNumberWithCode(mVertificationId, text_input_OTP.text.toString())
                }
            }
        }
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }

    fun checkTextField(): Boolean {
        if(text_input_Phone_Number.text.toString().isEmpty() ) {
            if (language == "en")
                Toast.makeText(this, "Field must be fill...!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Vui lòng điền đủ thông tin...!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun sendVerificationCode(phone: String) {
        progressDialog.setMessage("Verifying Phone Number...")
        progressDialog.show()

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun reSendVerificationCode(
        phone: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        progressDialog.setMessage("Resending code...")
        progressDialog.show()

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallbacks)
            .setForceResendingToken(token)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        progressDialog.setMessage("Verifying code...")
        progressDialog.show()

        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        progressDialog.setMessage("Change Phone Number...")
        progressDialog.show()

        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                phoneNumberSignUpSuccess = firebaseAuth.currentUser.phoneNumber
                changePhoneNumber()
                /*var phone : String = text_input_Phone_Number.text.toString()
                val sp : SharedPreferences = getSharedPreferences("ChangePhone", MODE_PRIVATE)
                val ed = sp.edit()
                ed.putString("Phone", phone)*/
                progressDialog.dismiss()
                Toast.makeText(this@ChangePhoneNumberActivity, "Success", Toast.LENGTH_SHORT).show()
                //finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this@ChangePhoneNumberActivity, "${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun changePhoneNumber() {
        val sp: SharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val idAccount = sp.getString("Id", "")!!.toInt()
        val phoneNumber = text_input_Phone_Number.text.toString()
        userViewModel!!.changePhoneNumber(idAccount, phoneNumber)
    }

}