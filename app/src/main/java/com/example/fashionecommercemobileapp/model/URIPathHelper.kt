package com.example.fashionecommercemobileapp.model

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.annotation.RequiresApi

class URIPathHelper {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun getImagePath(context: Context, uri: Uri): String? {
        var cursor: Cursor? = null

        val projection = arrayOf(MediaStore.Images.Media.DATA)
        try {
            cursor = context.contentResolver.query(uri, projection, null, null,null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }
}