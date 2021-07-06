package com.example.fashionecommercemobileapp.views

import android.app.Activity
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
    var username_long : String = ""
    var name_long: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        language = sharedPreferences.getString("My_Lang", "").toString()
        translate()

        AccountRepository.Companion.setContext(this@SignUpActivity)
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        accountViewModel!!.init()

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(loading)
        progressDialog.setCanceledOnTouchOutside(false)

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                //progressDialog.dismiss()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                progressDialog.dismiss()
                Toast.makeText(this@SignUpActivity, R.string.check_phone, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onCodeSent(
                verificationId: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, p1)
                mVertificationId = verificationId
                forceResendingToken = p1

                btnResendOTP.visibility = View.VISIBLE
                btnSendOTP.visibility = View.INVISIBLE
                btnSign.isEnabled = true
                progressDialog.dismiss()
            }
        }

        btnSendOTP.setOnClickListener() {
            var phone: String = txtPhone.text.toString()
            if (checkEnterInfo()) {
                if (phone[0] == '0') {
                    phone = phone.drop(1)
                    phone = "+84" + phone
                }
                sendVerificationCode(phone)
            }
        }

        btnResendOTP.setOnClickListener() {
            var phone: String = txtPhone.text.toString()
            if (checkEnterInfo()) {
                if (phone[0] == '0') {
                    phone = phone.drop(1)
                    phone = "+84" + phone
                }
                reSendVerificationCode(phone, forceResendingToken)
            }
        }

        btnSign.setOnClickListener() {
            if (!validationOtp()) {

            } else {
                if (checkEnterInfo())
                    verifyPhoneNumberWithCode(mVertificationId, txtOTP.text.toString())
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
            signup= "Signing up..."
            field_empty="Field cannot be empty"
            pass_error = "Password must be more than 6 char"
            nowhite = "White space is not allowed"
            repasss_error= "Repassword must be more than 6 char"
            not_match = "Repassword is not match"
            username_long = "Username too long"
            name_long = "Name too long"
        }
        else
        {
            loading = "Đang tải"
            verify_code = "Đang xác minh số điện thoại ..."
            re_code= "Gửi lại mã ..."
            signup= "Đăng ký..."
            field_empty = "Không được bỏ trống"
            pass_error = "Mật khẩu phải hơn 6 ký tự"
            nowhite = "Không được nhập khoảng trắng"
            repasss_error = "Mật khẩu phải hơn 6 ký tự"
            not_match = "Mật khẩu và nhập lại không khớp"
            username_long ="Tên đăng nhập quá dài"
            name_long = "Tên quá dài"
        }
    }

    fun validationUsername(): Boolean {
        val user: String = txtUsername.text.toString()
        val noWhite: Regex = Regex("(?=\\S+$)")
        if (user.isEmpty()) {
            txtUsername.error = field_empty
            false
        } else if (user.length > 50) {
            txtUsername.error = username_long
            return false
        } else if (user.contains(" ", false)) {
            txtUsername.error = nowhite
            return false
        } else {
            txtUsername.error = null
            true
        }
        return true
    }

    fun validationName(): Boolean {
        val name: String = txtName.text.toString()

        if (name.isEmpty()) {
            txtName.error = field_empty
            false
        } else if (name.length > 50) {
            txtName.error = name_long
            return false
        } else {
            txtName.error = null
            true
        }
        return true
    }

    fun validationPhone(): Boolean {
        val phone: String = txtPhone.text.toString()
        if (phone.isEmpty()) {
            txtPhone.error = field_empty
            false
        } else {
            txtPhone.error = null
            true
        }
        return true
    }

    fun validationPass(): Boolean {
        val pass: String = txtPassword.text.toString()
        val noWhite: Regex = Regex("(?=\\S+$)")
        if (pass.isEmpty()) {
            txtPassword.error = field_empty
            false
        } else if (pass.length < 6) {
            txtPassword.error = pass_error
            return false
        } else if (pass.contains(" ",false)) {
            txtPassword.error = nowhite
            return false
        } else {
            txtPassword.error = null
            true
        }
        return true
    }

    fun validationRePass(): Boolean {
        val rePass: String = txtRepassword.text.toString()
        val noWhite: Regex = Regex("(?=\\S+$)")
        if (rePass.isEmpty()) {
            txtRepassword.error = field_empty
            return false
        } else if (rePass.length < 6) {
            txtRepassword.error = repasss_error
            return false
        } else if (rePass.contains(" ",false)) {
            txtRepassword.error = nowhite
            return false
        } else if (rePass != txtPassword.text.toString()) {
            txtRepassword.error = not_match
            return false
        } else {
            txtRepassword.error = null
            return true
        }
    }

    fun validationOtp(): Boolean {
        val otp: String = txtOTP.text.toString()

        if (otp.isEmpty()) {
            txtOTP.error = field_empty
            return false
        } else {
            txtOTP.error = null
            return true
        }
    }

    fun checkEnterInfo(): Boolean {
        if (!validationName() || !validationUsername() || !validationPhone()
            || !validationPass() || !validationRePass()
        )
            return false
        return true
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
                regiter()
                progressDialog.dismiss()
                //finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this@SignUpActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun regiter() {
        val username = txtUsername.text.toString()
        val password = txtPassword.text.toString()
        val name = txtName.text.toString()
        val phoneNum = txtPhone.text.toString()

        accountViewModel!!.resultOfSignUp(username, password, name, phoneNum)
    }
}