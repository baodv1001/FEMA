package com.example.fashionecommercemobileapp.views

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
import java.util.*


@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity(), UploadRequestBody.UploadCallBack {
    private var userViewModel: UserViewModel? = null
    private var listUser: List<User>? = null
    val REQUEST_CODE = 100
    private var id: Int? = null
    private var selectedImageUri: Uri? = null
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        UserRepository.Companion.setContext(this@ProfileActivity)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel!!.init()

        var pickDate: String? = null

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
                        text_birthday.text = listUser!![0].dateOfBirth?.toLocaleString()
                        pickDate = listUser!![0].dateOfBirth?.toString()
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
        gender_layout.setOnClickListener{
            //create dialog
            val items = arrayOf(
                "Nam",
                "Nữ",
                "Khác"
            )
            val builder = AlertDialog.Builder(this, R.style.myDialogStyle)

            builder.setTitle(R.string.dialog_title)
                .setItems(items,
                    DialogInterface.OnClickListener{ dialog, which ->
                        text_gender.text = items[which]
                    })


            val dialog = builder.create()
            dialog.show()
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        }
        //change birthday
        birthday_layout.setOnClickListener{
            val calendar : Calendar = Calendar.getInstance()
            val curYear = calendar.get(Calendar.YEAR)
            val curMonth = calendar.get(Calendar.MONTH)
            val curDay = calendar.get(Calendar.DAY_OF_MONTH)

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                text_birthday.text = dayOfMonth.toString() + " thg " + (month + 1).toString() + ", " + year.toString()
                pickDate = year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString()
            }
            DatePickerDialog(this, dateSetListener, curYear,curMonth,curDay).show()
        }
        //pick image
        imageView_user.setOnClickListener {
            openGalleryForImage()
            //bitmap = (imageView_user.drawable as BitmapDrawable).bitmap

        }
        //update User
        button_saved.setOnClickListener {
            userViewModel!!.updateUserDate(idAccount!!.toInt(),
                                            text_name_bottom.text.toString(),
                                            text_gender.text.toString(),
                                            pickDate!!)
            uploadImage(idAccount.toInt())
            //Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
            super.onBackPressed()
        }
        //change Phone Number
        phone_number_layout.setOnClickListener {
            val intent = Intent(this, ChangePhoneNumberActivity::class.java).apply {  }
            (this as Activity).startActivityForResult(intent, 0)
        }
        //change Password
        change_password_layout.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java).apply {  }
            startActivity(intent)
        }
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }

    //pick image from gallery
    private fun openGalleryForImage()
    {
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
        userViewModel?.getUserData(id.toString())?.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        listUser = it?.data
                        //text_username.text = username
                        //Glide.with(imageView_user).load(listUser!![0].imageURL).into(imageView_user)
                        text_name.text = listUser!![0].name
                        text_name_bottom.text = listUser!![0].name
                        text_gender.text = listUser!![0].gender
                        text_birthday.text = listUser!![0].dateOfBirth?.toLocaleString()
                        //pickDate = listUser!![0].dateOfBirth?.toString()
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

    private fun showDialog(name: String){
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)

        val titleText = "New name"
        // Set up the input
        val input = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        var lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(20, 0,0,0)
        val marginHorizontal = 48F
        lp.leftMargin = marginHorizontal.toInt()
        input.layoutParams = lp
        input.gravity = Gravity.TOP or Gravity.LEFT
        input.hint = "name"
        input.setTextColor(Color.BLACK)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(name)
        builder.setView(input)
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
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

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
        Ed.commit()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)

        if (this is Activity) {
            (this as Activity).finish()
        }

        Runtime.getRuntime().exit(0)
    }
}
