package com.example.fashionecommercemobileapp.views

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.UploadResponse
import com.example.fashionecommercemobileapp.model.User
import com.example.fashionecommercemobileapp.retrofit.api.UploadApi
import com.example.fashionecommercemobileapp.retrofit.repository.UserRepository
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.retrofit.utils.UploadRequestBody
import com.example.fashionecommercemobileapp.retrofit.utils.getFileName
import com.example.fashionecommercemobileapp.retrofit.utils.snackbar
import com.example.fashionecommercemobileapp.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProfileActivity : AppCompatActivity(), UploadRequestBody.UploadCallBack {
    private var userViewModel: UserViewModel? = null
    private var listUser: List<User>? = null
    val REQUEST_CODE = 100
    private var id: Int? = null
    var language:String = ""
    private var selectedImageUri: Uri? = null

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        language = sharedPreferences.getString("My_Lang", "").toString()

        UserRepository.Companion.setContext(this@ProfileActivity)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel!!.init()

        var pickDate: String? = " "

        val sp1 = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val username = sp1.getString("Unm", null)
        val idAccount = sp1.getString("Id", null)
        id = idAccount?.toInt()
        userViewModel?.getUserData(idAccount.toString())?.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        listUser = it?.data
                        text_username.text = username
                        Glide.with(imageView_user).load(listUser!![0].imageURL).into(imageView_user)
                        text_name.text = listUser!![0].name
                        text_name_bottom.text = listUser!![0].name
                        text_gender.text = listUser!![0].gender
                        /*val birthday: Array<String> = listUser!![0].dateOfBirth?.toLocaleString()?.split(" ")!!.toTypedArray()
                        //text_birthday.text = listUser!![0].dateOfBirth?.toLocaleString()
                        val month: Array<String> = birthday[2].split(",").toTypedArray()
                        text_birthday.text = birthday[0] + "-" + month[0] + "-" + birthday[3]*/
                        val format = SimpleDateFormat("dd-MM-yyyy")
                        val format1 = SimpleDateFormat("yyyy-MM-dd")
                        text_birthday.text = format.format(listUser!![0].dateOfBirth)
                        pickDate = format1.format(listUser!![0].dateOfBirth)
                        //text_user_email.text = listUser!![0].email
                        text_phone_number.text = listUser!![0].phoneNumber

                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
        //change name
        name_layout.setOnClickListener {
            showDialog(text_name_bottom.text.toString())
        }
        //change gender
        gender_layout.setOnClickListener {
            //create dialog
            var items :Array<String>
                if (language == "en")
                    items = arrayOf(
                    "Male",
                    "Female",
                    "Others"
                )
            else
                    items = arrayOf(
                        "Nam",
                        "Nữ",
                        "Giới tính khác"
                    )
            val builder = AlertDialog.Builder(this, R.style.myDialogStyle)

            builder.setItems(items,
                DialogInterface.OnClickListener { dialog, which ->
                    text_gender.text = items[which]
                })
            val title = TextView(this)
            title.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            title.typeface = Typeface.DEFAULT_BOLD
            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(20, 0, 0, 0)
            title.setPadding(40, 30, 0, 0)
            title.layoutParams = params
            if (language == "en")
                title.text = "Gender"
            else
                title.text = "Giới tính"
            builder.setCustomTitle(title)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        }
        //change birthday
        birthday_layout.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val curYear = calendar.get(Calendar.YEAR)
            val curMonth = calendar.get(Calendar.MONTH)
            val curDay = calendar.get(Calendar.DAY_OF_MONTH)

            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    text_birthday.text =
                        dayOfMonth.toString() + "-" + (month + 1).toString() + "-" + year.toString()
                    pickDate =
                        year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString()
                }
            val dialog = DatePickerDialog(
                this,
                R.style.myDateDialogStyle,
                dateSetListener,
                curYear,
                curMonth,
                curDay
            )
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            if (language == "en")
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).text = "Cancel"
            else
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).text = "Hủy"
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        }
        //pick image
        imageView_user.setOnClickListener {
            openGalleryForImage()

        }
        //update User
        button_saved.setOnClickListener {
            userViewModel!!.updateUserDate(
                idAccount!!.toInt(),
                text_name_bottom.text.toString(),
                text_gender.text.toString(),
                pickDate!!
            )
            uploadImage(idAccount.toInt())
            Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT).show()
            super.onBackPressed()
        }
        //change Phone Number
        phone_number_layout.setOnClickListener {
            val intent = Intent(this, ChangePhoneNumberActivity::class.java).apply { }
            (this as Activity).startActivityForResult(intent, 0)
        }
        //change Password
        change_password_layout.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java).apply { }
            startActivity(intent)
        }
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }

    //pick image from gallery
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            imageView_user.setImageURI(data?.data) //handle chosen image
            selectedImageUri = data?.data
        }
        if (requestCode == 0) {
            userViewModel?.getUserData(id.toString())?.observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            listUser = it?.data
                            /*//text_username.text = username
                            //Glide.with(imageView_user).load(listUser!![0].imageURL).into(imageView_user)
                            text_name.text = listUser!![0].name
                            text_name_bottom.text = listUser!![0].name
                            text_gender.text = listUser!![0].gender
                            text_birthday.text = listUser!![0].dateOfBirth?.toLocaleString()
                            //pickDate = listUser!![0].dateOfBirth?.toString()
                            //text_user_email.text = listUser!![0].email*/
                            text_phone_number.text = listUser!![0].phoneNumber

                        }
                        Status.ERROR -> {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun uploadImage(idAccount: Int) {
        if (selectedImageUri == null) {
            layout_root.snackbar("Select an Image First")
            return
        }

        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        progress_bar.progress = 0
        val body = UploadRequestBody(file, "image", this)
        UploadApi().upLoadImage(
            MultipartBody.Part.createFormData(
                "image",
                file.name,
                body
            ),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "json"),
            idAccount
        ).enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                layout_root.snackbar(t.message!!)
                progress_bar.progress = 0
            }

            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                response.body()?.let {
                    layout_root.snackbar(it.message)
                    progress_bar.progress = 100
                }
            }
        })

    }

    private fun showDialog(name: String) {
        val builder: AlertDialog.Builder =
            android.app.AlertDialog.Builder(this, R.style.myDialogStyle)

        var titleText = ""

        if (language == "en")
            titleText = "New name"
        else
            titleText = "Tên mới"
        // Set up the input
        val input = EditText(this)
        input.setSingleLine()
        val container = FrameLayout(this)
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.leftMargin = resources.getDimensionPixelSize(R.dimen.dialog_margin)
        params.rightMargin = resources.getDimensionPixelSize(R.dimen.dialog_margin)
        input.layoutParams = params
        input.gravity = Gravity.TOP or Gravity.LEFT
        if (language == "en")
            input.hint = "Name"
        else
            input.hint = "Tên"
        input.setTextColor(Color.BLACK)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(name)
        container.addView(input)
        val title = TextView(this)
        title.setTextColor(ContextCompat.getColor(this, android.R.color.black))
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        title.typeface = Typeface.DEFAULT_BOLD
        val params1 = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params1.setMargins(20, 0, 0, 0)
        title.setPadding(52, 30, 0, 0)
        title.layoutParams = params1
        if (language == "en")
            title.text = "Name"
        else
            title.text = "Tên"
        builder.setCustomTitle(title)
        builder.setView(container)
        val foregroundColorSpan = ForegroundColorSpan(Color.BLACK)

        // Initialize a new spannable string builder instance

        // Initialize a new spannable string builder instance
        val ssBuilder = SpannableStringBuilder(titleText)

        // Apply the text color span

        // Apply the text color span
        ssBuilder.setSpan(
            foregroundColorSpan,
            0,
            titleText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        builder.setTitle(ssBuilder)
        // Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            text_name_bottom.text = input.text.toString()
            text_name.text = input.text.toString()
        })
        if (language == "en")
            builder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        else
            builder.setNegativeButton(
                "Hủy",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        var dialog = builder.create()

        dialog.show()

        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)

    }

    override fun onProgressUpdate(percentage: Int) {
        progress_bar.progress = percentage
    }

    fun onClickLogOut(view: View) {
        val sp = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val Ed = sp.edit()
        Ed.putString("Unm", null)
        Ed.putString("Psw", null)
        Ed.putInt("Id", 0)
        Ed.apply()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)

        if (this is Activity) {
            (this as Activity).finish()
        }

        Runtime.getRuntime().exit(0)
    }
}
