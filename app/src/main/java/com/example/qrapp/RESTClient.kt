package com.example.qrapp

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class RESTClient {

    companion object{
        private val BASE_URI = "https://student.i-propusk.ru/api/"
        private var mToken : String? = null

        fun setToken(token: String){
            mToken = token
        }

        fun getToken() = mToken

        fun logout() {
            mToken = null
        }
    }
}