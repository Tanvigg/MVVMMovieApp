package com.example.mvvmmovieapp.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.ViewModel.SignUpViewModel
import com.example.mvvmmovieapp.view.activity.MoviesActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*


class SignUpFragment : Fragment(),View.OnClickListener {
    private lateinit var mSignUpViewModel: SignUpViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSignUpViewModel = ViewModelProviders.of(this.requireActivity()).get(SignUpViewModel::class.java)
        handleObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val output =  inflater.inflate(R.layout.fragment_sign_up, container, false)
        output.sign_up.setOnClickListener(this)
        return output
    }

    override fun onClick(v: View?) {
        when(v){
            sign_up -> saveUserDetails()
        }
    }

    private fun saveUserDetails() {
        val name = signup_name.text.toString()
        val email = signup_email.text.toString()
        val password = signup_password.text.toString()
        if(mSignUpViewModel.signUp(name,email,password)){
            startActivity(Intent(context,MoviesActivity::class.java))
            activity!!.finish()
            Toast.makeText(context,"Sign Up successful",Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleObservers() {
        mSignUpViewModel.getNameError().observe(this@SignUpFragment,mNameErrorObserver)
        mSignUpViewModel.getEmailError().observe(this@SignUpFragment,mEmailErrorObserver)
        mSignUpViewModel.getPasswordError().observe(this@SignUpFragment,mPasswordErrorObserver)
    }

    private val mNameErrorObserver : Observer<String> = Observer {
        signup_name.error = it
    }

    private val mEmailErrorObserver : Observer<String> = Observer {
        signup_email.error = it
    }
    private val mPasswordErrorObserver :Observer<String> = Observer {
        signup_password.error = it
    }
}