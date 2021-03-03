package com.example.mvvmmovieapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmmovieapp.Constants
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.ViewModel.MoviesViewModel
import com.example.mvvmmovieapp.model.MovieResponse
import com.example.mvvmmovieapp.model.Results
import com.example.mvvmmovieapp.view.MoviesAdapter

class MoviesFragment : Fragment() {
    private var rvMoviesList: RecyclerView? = null
    private var vmMovies: MoviesViewModel? = null
    private lateinit var mView: View
    private lateinit var mContext: Context
    private var list : List<Results>? =null
    private var adapter : MoviesAdapter? =null
    private var favouritesList : ArrayList<Int>?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = this.context!!
        mView = inflater.inflate(R.layout.fragment_movies, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMoviesList = view.findViewById(R.id.rv_movies)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vmMovies = ViewModelProviders.of(activity!!).get(MoviesViewModel::class.java)
        fetchMovies()
        handleObserver()
    }

    private fun fetchMovies() {
        vmMovies?.getMoviesList(Constants.API_KEY)
    }

    private fun handleObserver() {
        vmMovies?.mldMoviesListDataResponse?.observe(viewLifecycleOwner,mMoviesResponseObserver)
    }

    private val mMoviesResponseObserver = Observer<MovieResponse>{
        list = it.results
        adapter = MoviesAdapter(list!!,activity!!)
        rvMoviesList?.adapter = adapter
        rvMoviesList?.itemAnimator = null
    }
}