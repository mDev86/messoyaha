package com.example.qrapp

import android.app.Application
import android.content.Context

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object{
        lateinit var recursiveSD: Unit
        lateinit var recursiveStore: Unit

        lateinit var context : Context
    }
}