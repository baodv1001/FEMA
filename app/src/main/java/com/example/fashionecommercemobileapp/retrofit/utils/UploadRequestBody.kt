package com.example.fashionecommercemobileapp.retrofit.utils

import android.os.Looper
import android.os.Handler
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

class UploadRequestBody(
    private val file: File,
    private val contentType: String,
    private val callBack: UploadCallBack
) : RequestBody() {

    override fun contentType() = "$contentType/*".toMediaTypeOrNull()

    override fun contentLength() = file.length()

    override fun writeTo(sink: BufferedSink) {
        val length = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = FileInputStream(file)
        var uploaded = 0L
        fileInputStream.use {inputStream->
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            while (inputStream.read(buffer).also { read = it } != -1) {
                handler.post(ProgressUpdater(uploaded, length))
                uploaded += read
                sink.write(buffer, 0, read)
            }
        }
    }

    interface UploadCallBack {
        fun onProgressUpdate(percentage: Int)
    }

    inner class ProgressUpdater(private val uploaded: Long, private val total: Long) : Runnable {
        override fun run() {
            callBack.onProgressUpdate((100 * uploaded / total).toInt())
        }
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }
}