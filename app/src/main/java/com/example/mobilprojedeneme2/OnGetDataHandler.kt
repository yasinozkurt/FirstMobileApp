package com.example.mobilprojedeneme2

import android.view.View
import android.view.ViewGroup


interface OnGetDataHandler {
    fun onGetDataSuccess(gameIdL:ArrayList<Int>,gameL:ArrayList<String>,scoreL:ArrayList<String>,genreL:ArrayList<String>,imagesL:ArrayList<String>)


    fun onGetDataFailure()

}

