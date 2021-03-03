package com.example.mvvmmovieapp.view.activity

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
import com.example.mvvmmovieapp.ViewModel.LoginViewModel
import com.example.mvvmmovieapp.ViewModel.MoviesViewModel
import com.example.mvvmmovieapp.view.fragment.MoviesFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {
    private var vmMovies: MoviesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_movies)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            vmMovies?.logout()
            val intent = Intent(this,AuthActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}