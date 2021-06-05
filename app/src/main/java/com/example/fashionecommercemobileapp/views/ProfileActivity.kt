    package com.example.fashionecommercemobileapp.views


import android.annotation.SuppressLint
import android.app.*
import android.content.DialogInterface
import android.content.Intent
import android.database.DatabaseErrorHandler
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.URIPathHelper
import com.example.fashionecommercemobileapp.retrofit.repository.UserRepository
import com.example.fashionecommercemobileapp.model.User
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity(){
    private var userViewModel: UserViewModel? = null
    private var listUser: List<User>? = null
    private val uriPathHelper = URIPathHelper()
    val REQUEST_CODE = 100
    lateinit var uri: Uri
    lateinit var bitmap: Bitmap
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        UserRepository.Companion.setContext(this@ProfileActivity)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel!!.init()

        var intent: Intent = intent
        var idAccount: Int? = intent.getIntExtra("idAccount", 0)
        var username: String? = intent.getStringExtra("username")

        userViewModel?.getUserData(idAccount.toString())?.observe(this, Observer {
            it?.let { resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        listUser = it?.data
                        text_username.text = username
                        Glide.with(imageView_user).load(listUser!![0].imageURL).into(imageView_user)
                        text_name.text = listUser!![0].name
                        text_gender.text = listUser!![0].gender
                        text_birthday.text = listUser!![0].dateOfBirth?.toLocaleString()
                        text_user_email.text = listUser!![0].email
                        text_phone_number.text = listUser!![0].phoneNumber
                    }
                    Status.ERROR -> {
                        Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
        //change gender
        gender_layout.setOnClickListener{
            //create dialog
            val items = arrayOf(
                "Nam",
                "Nữ",
                "Khác"
            )
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.dialog_title)
                .setItems(items,
                    DialogInterface.OnClickListener{ dialog, which ->
                        text_gender.text = items[which]
                    })
            builder.create()
            builder.show()
        }
        //change birthday
        birthday_layout.setOnClickListener{
            val calendar : Calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                text_birthday.text = day.toString() + " thg " + month.toString() + ", " + year.toString()
            }
            DatePickerDialog(this, dateSetListener, year,month,day).show()
        }
        //pick image
        imageView_user.setOnClickListener {
            openGalleryForImage()
            bitmap = (imageView_user.drawable as BitmapDrawable).bitmap

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
            val filePath = data?.data
            val imagePath = uriPathHelper.getImagePath(this, filePath!!)
        }
    }
}

