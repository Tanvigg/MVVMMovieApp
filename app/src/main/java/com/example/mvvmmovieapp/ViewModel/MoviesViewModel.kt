package com.example.mvvmmovieapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmmovieapp.model.FirebaseModel
import com.example.mvvmmovieapp.model.MovieDetailResponse
import com.example.mvvmmovieapp.model.MovieResponse
import com.example.mvvmmovieapp.model.User
import com.example.mvvmmovieapp.network.ApiClient
import com.example.mvvmmovieapp.network.MovieService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {
    private var firebaseModel = FirebaseModel()


    //Movies List Api Live Data
    var mldMoviesListDataResponse = MutableLiveData<MovieResponse>()
    var mldMoviesListingError = MutableLiveData<String>()

    //Movie detail Api Live Data
    var mldMovieDetailResponse = MutableLiveData<MovieDetailResponse>()
    var mldMovieDetailResponseError = MutableLiveData<String>()

    //Fav Movie Live Data
    var mldFavMovieResponse = MutableLiveData<Int>()

    //saved user favourites
    var mldUserFavourites : MutableLiveData<List<User>> = MutableLiveData()



    fun getMoviesList(api: String) {
        val call = ApiClient.movieService(MovieService::class.java).getupcoming(api)
        call?.enqueue(object : Callback<MovieResponse?> {

            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                mldMoviesListDataResponse.value = response.body()
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                mldMoviesListingError.value = t.message
            }
        })
    }

    fun getMovieDetail(id: Int, api: String) {
        val call = ApiClient.movieService(MovieService::class.java).getMovieDetail(id, api)
        call?.enqueue(object : Callback<MovieDetailResponse?> {

            override fun onResponse(
                call: Call<MovieDetailResponse?>,
                response: Response<MovieDetailResponse?>
            ) {
                mldMovieDetailResponse.value = response.body()
            }

            override fun onFailure(call: Call<MovieDetailResponse?>, t: Throwable) {
                mldMovieDetailResponseError.value = t.message
            }
        })
    }

    fun addId(id:Int,title:String,isFav:Boolean){
        firebaseModel.uploadId(id,title,isFav)
    }

    fun fetchUserDetails() : LiveData<List<User>> {
          firebaseModel.fetchUserData().addSnapshotListener(EventListener<QuerySnapshot> {value,e->
              if (e != null) {
                  Log.w("TAG", "listen:error", e)
                  return@EventListener
              }

              val userIdList:MutableList<User> = mutableListOf()
              for(doc in value!!){
                  val fetchdata = doc.toObject(User::class.java)
                  userIdList.add(fetchdata)
                  Log.d("docs",userIdList.toString())
              }
              mldUserFavourites.value = userIdList
          })
        return mldUserFavourites
    }

    fun logout(){
        firebaseModel.logout()
    }
}