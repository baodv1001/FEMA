package com.example.fashionecommercemobileapp.views

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.repository.AccountRepository
import com.example.fashionecommercemobileapp.viewmodels.AccountViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_forget_password.*
import java.util.concurrent.TimeUnit

class ForgetPasswordActivity : AppCompatActivity() {
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var mVertificationId: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private var phoneNumberSignUpSuccess: String? = null

    private lateinit var progressDialog: ProgressDialog

    private var accountViewModel: AccountViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        AccountRepository.Companion.setContext(this@ForgetPasswordActivity)
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        accountViewModel!!.init()
        accountViewModel!!.checkPW("", "")
                ?.observe(this, Observer { })

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                progressDialog.dismiss()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                progressDialog.dismiss()
                Toast.makeText(this@ForgetPasswordActivity, p0.message, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, p1)
                mVertificationId = verificationId
                forceResendingToken = p1

                linearLayoutGetOtp.visibility = View.INVISIBLE
                linearLayoutChangePW.visibility = View.VISIBLE
                progressDialog.dismiss()
            }
        }

        btnSendOTPForgot.setOnClickListener() {
            var phone: String = txtPhoneForgot.text.toString()
            var username: String = txtUsernameForgot.text.toString()
            var result: Boolean? = null

            if (!validationPhone() || !validationUsername())
            {

            } else {
                accountViewModel!!.checkPW(username, phone)
                        ?.observe(this, Observer {
                            result = it

                            if (result == true) {
                                if (phone[0] == '0') {
                                    phone = phone.drop(1)
                                    phone = "+84" + phone
                                }
                                sendVerificationCode(phone)
                            } else {
                                Toast.makeText(this@ForgetPasswordActivity, "Username or number phone is not available", Toast.LENGTH_SHORT).show()
                            }
                        })
            }
        }

        btnReSendOTPForgot.setOnClickListener() {
            var phone: String = txtPhoneForgot.text.toString()

            if (phone[0] == '0') {
                phone = phone.drop(1)
                phone = "+84" + phone
            }
            reSendVerificationCode(phone, forceResendingToken)
        }

        btnChanePW.setOnClickListener() {
            var pass: String = txtPasswordForgot.text.toString()
            var rePass: String = txtRepasswordForgot.text.toString()

            if (!validationOtp()) {

            } else {
                if (!validationPass() || !validationRePass())
                {

                } else {
                    verifyPhoneNumberWithCode(mVertificationId, txtOTPForgot.text.toString())
                }
            }
        }
    }
    fun validationUsername(): Boolean{
        val user: String = txtUsernameForgot.text.toString()
        if (user.isEmpty()){
            txtUsernameForgot.error = "Field cannot be empty"
            false
        } else {
            txtUsernameForgot.error = null
            true
        }
        return true
    }
    fun validationPhone(): Boolean{
        val phone: String = txtPhoneForgot.text.toString()
        if (phone.isEmpty()){
            txtPhoneForgot.error = "Field cannot be empty"
            false
        } else {
            txtPhoneForgot.error = null
            true
        }
        return true
    }
    fun validationPass(): Boolean{
        val pass: String = txtPasswordForgot.text.toString()
        val noWhite: Regex = Regex("(?=\\S+$)")
        if (pass.isEmpty()){
            txtPasswordForgot.error = "Field cannot be empty"
            false
        } else if (pass.length < 6)
        {
            txtPasswordForgot.error  = "Password must be more than 6 char"
            return false
        } else if (pass.matches(noWhite))
        {
            txtPasswordForgot.error = "White space is not allowed"
            return false
        } else {
            txtPasswordForgot.error = null
            true
        }
        return true
    }

    fun validationRePass(): Boolean{
        val rePass: String = txtRepasswordForgot.text.toString()
        val noWhite: Regex = Regex("(?=\\S+$)")
        if (rePass.isEmpty()){
            txtRepasswordForgot.error = "Field cannot be empty"
            return false
        } else if (rePass.length < 6)
        {
            txtRepasswordForgot.error  = "Repassword must be more than 6 char"
            return false
        } else if (rePass.matches(noWhite))
        {
            txtRepasswordForgot.error = "White space is not allowed"
            return false
        }else if (rePass != txtPasswordForgot.text.toString())
        {
            txtRepasswordForgot.error = "Repassword is not match"
            return false
        } else {
            txtRepasswordForgot.error = null
            return true
        }
    }

    fun validationOtp(): Boolean{
        val otp: String = txtOTPForgot.text.toString()

        if (otp.isEmpty()){
            txtOTPForgot.error = "Field cannot be empty"
            return false
        } else {
            txtOTPForgot.error = null
            return true
        }
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

    private fun reSendVerificationCode(phone: String, token: PhoneAuthProvider.ForceResendingToken?) {
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
        progressDialog.setMessage("Signing up...")
        progressDialog.show()

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    phoneNumberSignUpSuccess = firebaseAuth.currentUser.phoneNumber
                    changePassword()
                    progressDialog.dismiss()
                    //finish()
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this@ForgetPasswordActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                }
    }

    private fun changePassword() {
        var phone: String = txtPhoneForgot.text.toString()
        var username: String = txtUsernameForgot.text.toString()
        var pass: String = txtPasswordForgot.text.toString()

        accountViewModel!!.updatePW(username, pass, phone)
    }
}