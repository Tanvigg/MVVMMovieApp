package com.example.mvvmmovieapp.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.ViewModel.LoginViewModel
import com.example.mvvmmovieapp.view.activity.MoviesActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(),View.OnClickListener {
    private var mAuth: FirebaseAuth? = null
    private lateinit var mLoginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoginViewModel = ViewModelProviders.of(this.requireActivity()).get(LoginViewModel::class.java)
        handleObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val output : View =  inflater.inflate(R.layout.fragment_login, container, false)
        mAuth = FirebaseAuth.getInstance()
        output.findViewById<Button>(R.id.button_signIn).setOnClickListener(this)
        return output
    }

    override fun onClick(v: View?) {
        when(v){
            button_signIn ->{
                val email = login_email.text.toString()
                val password = login_password.text.toString()
                mLoginViewModel.login(email, password)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth != null && mAuth!!.currentUser != null) {
            startActivity(Intent(context, MoviesActivity::class.java))
            activity!!.finish()
        }
    }

    private fun handleObservers() {
        mLoginViewModel.getEmailError().observe(this@LoginFragment,mEmailErrorObserver)
        mLoginViewModel.getPasswordError().observe(this@LoginFragment,mPasswordErrorObserver)
        mLoginViewModel.getLoginState().observe(this@LoginFragment,mLoginStateObserver)
    }

    private val mEmailErrorObserver : Observer<String> = Observer {
        login_email.error = it
    }
    private val mPasswordErrorObserver : Observer<String> = Observer {
        login_password.error = it

    }
    private val mLoginStateObserver : Observer<LoginViewModel.LoginState> = Observer{
        when(it){
            LoginViewModel.LoginState.SHOW_PROGRESS -> progressbar.visibility = View.VISIBLE
            LoginViewModel.LoginState.HIDE_PROGRESS -> progressbar.visibility = View.GONE
            LoginViewModel.LoginState.GO_TO_HOMEPAGE -> {
                startActivity(Intent(context, MoviesActivity::class.java))
                activity!!.finish()
            }
        }

    }

}