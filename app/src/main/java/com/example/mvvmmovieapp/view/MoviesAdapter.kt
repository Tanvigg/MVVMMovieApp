package com.example.mvvmmovieapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmmovieapp.Constants
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.databinding.MovieItemBinding
import com.example.mvvmmovieapp.model.Results
import com.example.mvvmmovieapp.view.fragment.MovieDetailFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(private val moviesList:List<Results>,private val activity : FragmentActivity):
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context)
            ,parent,false))
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val result : Results = moviesList[position]
        Picasso.get().load(Constants.IMAGE_BASE_URL + result.poster_path).into(holder.ivMovie)
        holder.bind(result)

        holder.ivMovie.setOnClickListener{
            val movieDetailFragment = MovieDetailFragment()
            val bundle = Bundle()
            bundle.putInt("id",moviesList[position].id)
            bundle.putString("title",moviesList[position].title)
            val fm: FragmentManager = activity.supportFragmentManager
            val ft = fm.beginTransaction()
            movieDetailFragment.arguments = bundle
            ft.replace(R.id.container,movieDetailFragment)
            ft.addToBackStack(null)
            ft.commit()
        }
    }

    class MoviesViewHolder(private var mBinding : MovieItemBinding) :RecyclerView.ViewHolder(mBinding.root){
        val ivMovie = itemView.findViewById<ImageView>(R.id.iv_movie)
        fun bind(movieItem: Results){
            mBinding.movies = movieItem
        }
    }
}