package com.example.mvvmmovieapp.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast

class RestartService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Broadcast Listened", "Service tried to stop")
        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(Intent(context, NotificationService::class.java))
        else
            context.startService(Intent(context, NotificationService::class.java))
    }
}
