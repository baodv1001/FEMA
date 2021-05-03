package com.example.fashionecommercemobileapp.model

import android.content.Context
import java.math.BigInteger
import java.security.MessageDigest

class ShaPW {
    @Throws(Exception::class)
    fun encryptSHA(data: ByteArray?, shaN: String?): ByteArray? {
        val sha: MessageDigest = MessageDigest.getInstance(shaN)
        sha.update(data)
        return sha.digest()
    }

    fun doEncrypt(pass : String) : String
    {

        var inputData : ByteArray? = pass.toByteArray()
        var outputData : ByteArray? = null

        try {
            outputData = encryptSHA(inputData, "SHA-1")

        } catch (e : java.lang.Exception)
        {
            e.printStackTrace()
        }

        var shaData =  BigInteger(1,outputData)
        return shaData.toString()
    }

    companion object {
        private var shaPW: ShaPW? = null
        val instance: ShaPW?
            get() {
                if (shaPW == null) {
                    shaPW = ShaPW()
                }
                return shaPW
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }
}