package br.com.android.core.topics.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServiceSample : Service() {

    init {
        Log.d(TAG, "Service is running...")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val dataString = intent?.getStringExtra(ServiceSampleActivity.SERVICE_DATA_KEY)
        dataString?.let {
            Log.d(TAG, "DATA STRING: $dataString")
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service is stopping...")
    }

    companion object {
        private const val TAG = "ServiceSample"
    }
}