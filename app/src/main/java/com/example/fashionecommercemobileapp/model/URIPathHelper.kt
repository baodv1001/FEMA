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
        val docId = DocumentsContract.getDocumentId(uri)
        val split = docId.split(":".toRegex()).toTypedArray()
        val selection = "_id=?"
        val selectionArgs = arrayOf(split[1])

        return getDataColumn(context, uri, selection, selectionArgs)
    }

    fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = context.getContentResolver().query(uri!!, projection, selection, selectionArgs,null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }
}