package com.example.mvvmmovieapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmmovieapp.Constants
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.ViewModel.MoviesViewModel
import com.example.mvvmmovieapp.model.MovieDetailResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.*


class MovieDetailFragment : Fragment() {
    private var vmMovies: MoviesViewModel? = null
    private lateinit var mView: View
    private lateinit var mContext: Context
    private var id: Int? = null
    private var title:String?=null
    private var isFav = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = this.context!!
        id = arguments?.getInt("id")
        title = arguments?.getString("title")
        mView = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vmMovies = ViewModelProviders.of(activity!!).get(MoviesViewModel::class.java)
        fetchMovieDetail()
        handleObserver()
        fetchUserDetails()
        handleClickListener()
    }

    private fun fetchUserDetails() {
        vmMovies?.fetchUserDetails(id!!)?.observe(viewLifecycleOwner, Observer {
            if(it[0].isLiked or it.isEmpty())
                iv_fav.setBackgroundResource(R.drawable.ic_favorite_red_500_18dp)
            else
                iv_fav.setBackgroundResource(R.drawable.ic_favorite_border_grey_500_18dp)
        })
    }

    private fun handleClickListener() {
        iv_fav.setOnClickListener(mOnClickListener)
    }

    private val mOnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.iv_fav -> {
                isFav = true
                iv_fav.setBackgroundResource(R.drawable.ic_favorite_red_500_18dp)
                vmMovies?.addId(id!!,title!!,isFav)
            }
        }
    }

    private fun fetchMovieDetail() {
        vmMovies?.getMovieDetail(id!!, Constants.API_KEY)
    }

    private fun handleObserver() {
        vmMovies?.mldMovieDetailResponse?.observe(viewLifecycleOwner, mMovieDetailResponseObserver)
    }

    private val mMovieDetailResponseObserver = Observer<MovieDetailResponse> {
        Picasso.get().load(Constants.IMAGE_BASE_URL + it.backdrop_path).into(iv_backdrop)
        Picasso.get().load(Constants.IMAGE_BASE_URL + it.poster_path).into(iv_titleImage)
        tv_title.setText(it.title)
        tv_overview.setText(it.overview)
    }
}