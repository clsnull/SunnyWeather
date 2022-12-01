package com.clsnull.sunnyweather

import android.app.Application
import android.content.Context
import android.util.Log

class SunnyWeatherApplication : Application() {
    companion object {
        const val TOKEN = "nenTaWFGkWIOMWgU"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}