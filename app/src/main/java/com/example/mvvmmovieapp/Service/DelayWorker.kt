package com.example.mvvmmovieapp.Service

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters

class DelayWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams){

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        val notificationService = NotificationService()
        notificationService.getRandomNotification()

        return Result.success()
    }
}