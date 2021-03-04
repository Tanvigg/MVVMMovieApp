package com.example.mvvmmovieapp.view.activity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.Service.NotificationService
import com.example.mvvmmovieapp.Service.RestartService
import com.example.mvvmmovieapp.ViewModel.LoginViewModel
import com.example.mvvmmovieapp.ViewModel.MoviesViewModel
import com.example.mvvmmovieapp.view.fragment.MoviesFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {
    private var vmMovies: MoviesViewModel? = null
    lateinit var serviceIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movies)

        val notificationService = NotificationService()
        serviceIntent = Intent(this, notificationService::class.java)
        if (!isNotificationServiceRunning(notificationService::class.java)){
            startService(serviceIntent)
        }
        vmMovies = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Movies"
        val moviesFragment = MoviesFragment()
        val fm: FragmentManager = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.container,moviesFragment)
        ft.addToBackStack(null)
        ft.commit()


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return true

    }

    private fun isNotificationServiceRunning(serviceClass: Class<out NotificationService>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for(service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Int.MAX_VALUE)){
            if (serviceClass.name == service.service.className) {
                Log.d("this", "ServiceStatus: Running")
                return true
            }
        }
        Log.d("this", "ServiceStatus: Not Running")
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            vmMovies?.logout()
            val intent = Intent(this,AuthActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
//        stopService(serviceIntent)
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartService"
        broadcastIntent.setClass(this, RestartService::class.java)
        this.sendBroadcast(broadcastIntent)
        super.onDestroy()
    }
}