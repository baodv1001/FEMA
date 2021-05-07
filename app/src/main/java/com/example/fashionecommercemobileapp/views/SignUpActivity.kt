package com.example.fashionecommercemobileapp.views

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.repository.AccountRepository
import com.example.fashionecommercemobileapp.viewmodels.AccountViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVertificationId : String?= null
    private lateinit var firebaseAuth: FirebaseAuth
    private var phoneNumberSignUpSuccess : String? = null

    private lateinit var progressDialog: ProgressDialog

    private var accountViewModel : AccountViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        AccountRepository.Companion.setContext(this@SignUpActivity)
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        accountViewModel!!.init()

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
                Toast.makeText(this@SignUpActivity, "Check your phone number", Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, p1)
                mVertificationId = verificationId
                forceResendingToken = p1

                btnResendOTP.visibility = View.VISIBLE
                btnSendOTP.visibility = View.INVISIBLE
                btnSign.isEnabled = true
                progressDialog.dismiss()
            }
        }

        btnSendOTP.setOnClickListener(){
            var phone : String = txtPhone.text.toString()
            if (checkEnterInfo()) {
                if (phone[0] == '0') {
                    phone = phone.drop(1)
                    phone = "+84" + phone
                }
                sendVerificationCode(phone)
            }
        }

        btnResendOTP.setOnClickListener(){
            var phone : String = txtPhone.text.toString()
            if (checkEnterInfo()) {
                if (phone[0] == '0') {
                    phone = phone.drop(1)
                    phone = "+84" + phone
                }
                reSendVerificationCode(phone, forceResendingToken)
            }
        }

        btnSign.setOnClickListener(){
            if (txtOTP.text.toString().isEmpty())
            {
                Toast.makeText(this@SignUpActivity, "Please enter your verification code...", Toast.LENGTH_SHORT).show()
            }
            else
            {
                if (checkEnterInfo())
                    verifyPhoneNumberWithCode(mVertificationId , txtOTP.text.toString())
            }
        }
    }

    private fun sendVerificationCode(phone: String)
    {
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
    private fun reSendVerificationCode(phone:String, token : PhoneAuthProvider.ForceResendingToken?)
    {
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

    private fun verifyPhoneNumberWithCode(verificationId:  String?, code :String)
    {
        progressDialog.setMessage("Verifying code...")
        progressDialog.show()

        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        progressDialog.setMessage("Signing up...")
        progressDialog.show()

        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                phoneNumberSignUpSuccess = firebaseAuth.currentUser.phoneNumber
                regiter()
                progressDialog.dismiss()
                //finish()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this@SignUpActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private  fun checkEnterInfo() :Boolean
    {
        if (txtUsername.text.toString().isEmpty() || txtPhone.text.toString().isEmpty() || txtName.text.toString().isEmpty()
            || txtPassword.text.toString().isEmpty() || txtRepassword.text.toString().isEmpty())
        {
            Toast.makeText(this@SignUpActivity, "Field must be fill...!", Toast.LENGTH_SHORT).show()
            return false
        } else if (!txtPassword.text.toString().equals(txtRepassword.text.toString())) {
            Toast.makeText(this@SignUpActivity, "Password not match", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    private fun regiter()
    {
        val username = txtUsername.text.toString()
        val password = txtPassword.text.toString()
        val name = txtName.text.toString()
        val phoneNum = txtPhone.text.toString()

        accountViewModel!!.resultOfSignUp(username, password, name, phoneNum)
    }
}