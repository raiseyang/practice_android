package com.raise.practice

import android.app.Application
import com.raise.weapon_base.LLog

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LLog.d("MyApp", "onCreate() this=$this")

    }
}