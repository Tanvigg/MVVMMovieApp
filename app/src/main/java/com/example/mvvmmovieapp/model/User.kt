package com.example.mvvmmovieapp.model

data class User(var id : Int=0, var title:String="",var isLiked:Boolean = false) {
    override fun toString(): String {
        return super.toString()
    }
}