package com.example.mvvmmovieapp.network

import com.example.mvvmmovieapp.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.base_Url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun movieService(service: Class<MovieService>): MovieService {
        return retrofit.create(service)
    }

}