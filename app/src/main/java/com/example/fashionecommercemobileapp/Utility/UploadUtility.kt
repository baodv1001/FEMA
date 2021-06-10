package com.example.fashionecommercemobileapp.Utility

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.fashionecommercemobileapp.model.URIPathHelper
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.chrono.MinguoChronology

class UploadUtility(activity: Activity) {
    var activity: Activity = activity
    var dialog: ProgressDialog? = null
    var serverURL: String = "http://laptop-0qnm76ck/fashionecommerceapp/Image/upload_image.php"
    var serverUploadDirectoryPath: String = "http://laptop-0qnm76ck/fashionecommerceapp/Image/"
    val client = OkHttpClient()

    fun uploadFile(sourceFilePath: String, uploadFileName: String? = null) {
        uploadFile(File(sourceFilePath), uploadFileName)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun uploadFile(sourceFileUri: Uri, uploadFileName: String? = null) {
        val pathFromUri = URIPathHelper().getImagePath(activity, sourceFileUri)
        uploadFile(File(pathFromUri), uploadFileName)
    }

    fun uploadFile(sourceFile: File, uploadFileName: String? = null) {
        Thread {
            val mimeType = getMineType(sourceFile)
            if (mimeType == null) {
                Log.e("file error", "Not able to get mime type")
                return@Thread
            }
            val fileName: String = uploadFileName ?: sourceFile.name
            toggleProgressDialog(true)
            /*try {*/
                val requestBody: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("upload_files", fileName,sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                    .build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody).build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    Log.d("File upload", "success, path: $serverUploadDirectoryPath$fileName")
                    showToast("File uploaded successfully at $serverUploadDirectoryPath$fileName")
                } else {
                    Log.e("File upload", "failed")
                    showToast("File uploading failed")
                }/*
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("File upload","Failed")
                showToast("File uploading failed")
            }*/
            toggleProgressDialog(false)
        }.start()
    }
    //url = file path
    private fun getMineType(file: File): String? {
        var type:String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    fun showToast(message: String) {
        activity.runOnUiThread {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun toggleProgressDialog(show: Boolean) {
        activity.runOnUiThread {
            if(show) {
                dialog = ProgressDialog.show(activity,"","Upload File ...", true)
            } else {
                dialog?.dismiss()
            }
        }
    }
}