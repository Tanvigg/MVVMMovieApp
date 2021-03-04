package com.example.mvvmmovieapp.Service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.model.User
import com.example.mvvmmovieapp.view.activity.MoviesActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class NotificationService : Service() {
    private var db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private lateinit var currentUserId: String

    private lateinit var favRef: CollectionReference
    var mldUserFavourites: MutableLiveData<List<User>> = MutableLiveData()
    private var counter = 0


    override fun onCreate() {
        super.onCreate()
        currentUserId = auth.currentUser!!.uid
        favRef = db.collection("users").document(currentUserId).collection("Favourites")

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startDefaultForeground()
        else
            startForeground(1, Notification())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startDefaultForeground() {
        val NOTIFICATION_CHANNEL_ID = "defaultNotification"
        val channelName = "Default Notification"
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_NONE
        )
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

//        val appPendingIntent = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notificationBuild = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification = notificationBuild
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(NotificationManager.IMPORTANCE_NONE)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setAutoCancel(true)
            .setTimeoutAfter(2000)
            .build()

        startForeground(3, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startCustomForeground() {
        ObserveLike().observeForever {
            if (it != null && counter >1) {
                val NOTIFICATION_CHANNEL_ID = "messageNotification"
                val channelName = "Message Notification"
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                channel.importance = NotificationManager.IMPORTANCE_HIGH

                val appPendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, MoviesActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                val notificationBuild = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                val notification = notificationBuild.setOngoing(false)
                    .setContentTitle("NOTIFICATION")
                    .setContentText("Added to Favourites")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setContentIntent(appPendingIntent)
                    .setAutoCancel(true)
                    .build()

                notificationManager.notify(2, notification)

            }
        }


    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        setTimer()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startCustomForeground()
        else
            startForeground(1, Notification())


        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        val bradcastIntent = Intent()
        bradcastIntent.setAction("restartService")
        bradcastIntent.setClass(this, RestartService::class.java)
        this.sendBroadcast(bradcastIntent)
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    fun ObserveLike(): LiveData<List<User>> {
        favRef.addSnapshotListener { value, e ->
            if (e != null) {
                Log.w("TAG", "listen:error", e)
                return@addSnapshotListener
            }
            val userIdList: MutableList<User> = mutableListOf()

            for (doc in value!!.documentChanges) {
                when (doc.type) {
                    DocumentChange.Type.MODIFIED -> {
                        val fetchdata = doc.document.toObject(User::class.java)
                        userIdList.add(fetchdata)
                        Log.d("docs", userIdList.toString())
                    }
                }
            }
            counter++
            mldUserFavourites.value = userIdList
        }
        return mldUserFavourites
    }

    private fun setTimer() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val myWork = PeriodicWorkRequest.Builder(DelayWorker::class.java, 1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueue(myWork)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getRandomNotification() {
        val NOTIFICATION_CHANNEL_ID = "messageNotification"
        val channelName = "Message Notification"
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val appPendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MoviesActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val notificationBuild = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification = notificationBuild.setOngoing(false)
            .setContentTitle("NOTIFICATION")
            .setContentText("Get Latest Movies here")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setContentIntent(appPendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(4, notification)

    }

}

