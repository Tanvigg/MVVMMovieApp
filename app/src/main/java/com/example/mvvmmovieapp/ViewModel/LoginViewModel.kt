package com.example.mvvmmovieapp.ViewModel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmmovieapp.model.FirebaseModel

class LoginViewModel : ViewModel() {
    private var emailError = MutableLiveData<String>()
    private var passwordError = MutableLiveData<String>()
    private var loginState = MutableLiveData<LoginState>()
    private var firebaseModel = FirebaseModel()

    fun getEmailError(): LiveData<String> {
        return emailError
    }

    fun getPasswordError(): LiveData<String> {
        return passwordError
    }

    fun getLoginState(): LiveData<LoginState> {
        return loginState
    }


    fun login(email: String, passwd: String) {
        if (TextUtils.isEmpty(email)) {
            emailError.value = "Email can't be blank"
        } else if (TextUtils.isEmpty(passwd)) {
            passwordError.value = "Please enter Password"
        } else {
            loginState.value = LoginState.SHOW_PROGRESS
            firebaseModel.login(email,passwd).addOnSuccessListener {
                loginState.value = LoginState.HIDE_PROGRESS
                loginState.value = LoginState.GO_TO_HOMEPAGE
            }
                .addOnFailureListener {
                    Log.d("data", "signInWithEmail:Failed")
                    loginState.value = LoginState.HIDE_PROGRESS
            }
        }
    }

    enum class LoginState {
        SHOW_PROGRESS,
        HIDE_PROGRESS,
        GO_TO_HOMEPAGE,
    }
}