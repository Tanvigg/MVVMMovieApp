package com.example.mvvmmovieapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.view.fragment.LoginFragment
import com.example.mvvmmovieapp.view.fragment.SignUpFragment
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {
    private val manager: FragmentManager = supportFragmentManager
    private val transaction = manager.beginTransaction()
    private val loginFragment =
        LoginFragment()
    private val signupFragment =
        SignUpFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_auth)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "LOGIN"
        transaction.replace(R.id.container, loginFragment).commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val manager1: FragmentManager = supportFragmentManager
        val transaction1 = manager1.beginTransaction()
        if (item.itemId == R.id.signin) {
            supportActionBar!!.title = "LOGIN"
            transaction1.replace(R.id.container, loginFragment)
        } else if (item.itemId == R.id.signup) {
            supportActionBar!!.title = "SIGN UP"
            transaction1.replace(R.id.container, signupFragment)

        }
        transaction1.commit()
        return super.onOptionsItemSelected(item)
    }


}