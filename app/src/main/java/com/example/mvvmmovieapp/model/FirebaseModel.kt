package com.example.mvvmmovieapp.model

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirebaseModel {
    private val auth = FirebaseAuth.getInstance()
    private val TAG = "Firebase Model"
    private var db = FirebaseFirestore.getInstance()
    private lateinit var currentUserId: String
    private var userHashMap: HashMap<String, String> = HashMap()
    private lateinit var user: User


    fun login(email: String, password: String): Task<AuthResult> {
        val fAuth: Task<AuthResult> = auth.signInWithEmailAndPassword(email,password)
        return fAuth
    }

    fun signUp(name: String, email: String, password: String):Boolean {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                currentUserId = auth.currentUser!!.uid
                uploadUser(name, email)
            }
            else{
                Log.e("Error", "" + task.exception)
            }
        }
        return true
    }

    fun uploadUser(name:String,email:String){
        userHashMap.put("Name", name)
        userHashMap.put("Email", email)
        db.collection("users").document(currentUserId).set(userHashMap).addOnSuccessListener {
            Log.e("FIREBASE MODEL", "successful")
        }
            .addOnFailureListener {
                Log.e("Error", "$it")
            }
    }

    fun uploadId(id:Int,title:String,isFav:Boolean){
        currentUserId = auth.currentUser!!.uid
        user = User(id,title,isFav)
        db.collection("users").document(currentUserId).collection("Favourites").document(id.toString()).set(user).addOnSuccessListener {
            Log.e("FIREBASE MODEL", "successful")
        }
            .addOnFailureListener {
                Log.e("Error", "$it")
            }
    }

    fun fetchUserData() : CollectionReference {
        currentUserId = auth.uid.toString()
        return db.collection("users").document(currentUserId).collection("Favourites")
    }

    fun logout(){
        auth.signOut()
    }
}