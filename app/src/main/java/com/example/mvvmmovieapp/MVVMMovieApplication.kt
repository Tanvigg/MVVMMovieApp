package com.example.mvvmmovieapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class MVVMMovieApplication : Application() {

    companion object{
        private var instance: MVVMMovieApplication ?= null

        fun getInstance(): MVVMMovieApplication? {
            return instance
        }
        fun hasNetwork(): Boolean {
            return instance!!.isNetworkConnected()
        }

    }

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting
    }


}