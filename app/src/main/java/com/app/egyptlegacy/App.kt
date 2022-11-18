package com.app.egyptlegacy

import android.app.Application

class App : Application() {
    lateinit var gf: F

    override fun onCreate() {
        super.onCreate()
        gf = F("mystery.data", this)
    }
}