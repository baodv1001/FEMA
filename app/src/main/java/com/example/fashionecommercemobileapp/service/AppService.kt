package com.example.fashionecommercemobileapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AppService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
    override fun onCreate() {
        super.onCreate()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return START_STICKY
    }
    override fun onDestroy() {

        super.onDestroy()
    }
}