package com.example.fashionecommercemobileapp.views

import android.app.Activity
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

    var language:String = ""
    var loading: String = ""
    var verify_code: String =""
    var re_code: String= ""
    var signup: String = ""
    var field_empty: String = ""
    var pass_error:String = ""
    var repasss_error: String = ""
    var nowhite :String = ""
    var not_match: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        language = sharedPreferences.getString("My_Lang", "").toString()
        translate()

        AccountRepository.Companion.setContext(this@ForgetPasswordActivity)
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        accountViewModel!!.init()
        accountViewModel!!.checkPW("", "")
            ?.observe(this, Observer { })

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(loading)
        progressDialog.setCanceledOnTouchOutside(false)

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                progressDialog.dismiss()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                progressDialog.dismiss()
                Toast.makeText(this@ForgetPasswordActivity, p0.message, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
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

            if (!validationPhone() || !validationUsername()) {

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
                            Toast.makeText(
                                this@ForgetPasswordActivity,
                                R.string.user_phone_not_available,
                                Toast.LENGTH_SHORT
                            ).show()
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
                if (!validationPass() || !validationRePass()) {

                } else {
                    verifyPhoneNumberWithCode(mVertificationId, txtOTPForgot.text.toString())
                }
            }
        }
    }

    fun translate()
    {
        if (language == "en")
        {
            loading = "Loading"
            verify_code = "Verifying code..."
            re_code= "Resending code..."
            signup= "Update password..."
            field_empty="Field cannot be empty"
            pass_error = "Password must be more than 6 char"
            nowhite = "White space is not allowed"
            repasss_error= "Repassword must be more than 6 char"
            not_match = "Repassword is not match"
        }
        else
        {
            loading = "Đang tải"
            verify_code = "Đang xác minh số điện thoại ..."
            re_code= "Gửi lại mã ..."
            signup= "Cập nhật mật khẩu..."
            field_empty = "Không được bỏ trống"
            pass_error = "Mật khẩu phải hơn 6 ký tự"
            nowhite = "Không được nhập khoảng trắng"
            repasss_error = "Mật khẩu phải hơn 6 ký tự"
            not_match = "Mật khẩu và nhập lại không khớp"
        }
    }

    fun validationUsername(): Boolean {
        val user: String = txtUsernameForgot.text.toString()
        if (user.isEmpty()) {
            txtUsernameForgot.error = field_empty
            false
        } else {
            txtUsernameForgot.error = null
            true
        }
        return true
    }

    fun validationPhone(): Boolean {
        val phone: String = txtPhoneForgot.text.toString()
        if (phone.isEmpty()) {
            txtPhoneForgot.error = field_empty
            false
        } else {
            txtPhoneForgot.error = null
            true
        }
        return true
    }

    fun validationPass(): Boolean {
        val pass: String = txtPasswordForgot.text.toString()
        val noWhite: Regex = Regex("(?=\\S+$)")
        if (pass.isEmpty()) {
            txtPasswordForgot.error = field_empty
        } else if (pass.length < 6) {
            txtPasswordForgot.error = pass_error
            return false
        } else if (pass.contains(" ",false)) {
            txtPasswordForgot.error = nowhite
            return false
        } else {
            txtPasswordForgot.error = null
            true
        }
        return true
    }

    fun validationRePass(): Boolean {
        val rePass: String = txtRepasswordForgot.text.toString()
        val noWhite: Regex = Regex("(?=\\S+$)")
        if (rePass.isEmpty()) {
            txtRepasswordForgot.error = field_empty
            return false
        } else if (rePass.length < 6) {
            txtRepasswordForgot.error = repasss_error
            return false
        } else if (rePass.contains(" ",false)) {
            txtRepasswordForgot.error = nowhite
            return false
        } else if (rePass != txtPasswordForgot.text.toString()) {
            txtRepasswordForgot.error = not_match
            return false
        } else {
            txtRepasswordForgot.error = null
            return true
        }
    }

    fun validationOtp(): Boolean {
        val otp: String = txtOTPForgot.text.toString()

        if (otp.isEmpty()) {
            txtOTPForgot.error = field_empty
            return false
        } else {
            txtOTPForgot.error = null
            return true
        }
    }

    private fun sendVerificationCode(phone: String) {
        progressDialog.setMessage(verify_code)
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
        progressDialog.setMessage(re_code)
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
        progressDialog.setMessage(verify_code)
        progressDialog.show()

        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        progressDialog.setMessage(signup)
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
                Toast.makeText(this@ForgetPasswordActivity, "${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun changePassword() {
        var phone: String = txtPhoneForgot.text.toString()
        var username: String = txtUsernameForgot.text.toString()
        var pass: String = txtPasswordForgot.text.toString()

        accountViewModel!!.updatePW(username, pass, phone)
    }
}