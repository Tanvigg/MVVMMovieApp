package com.example.mvvmmovieapp.network

import com.example.mvvmmovieapp.model.MovieDetailResponse
import com.example.mvvmmovieapp.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("upcoming")
    fun getupcoming(@Query("api_key") apiKey: String?): Call<MovieResponse?>?

    @GET("{id}")
    fun getMovieDetail(@Path("id") id:Int?,@Query("api_key") apiKey: String?): Call<MovieDetailResponse?>?


}