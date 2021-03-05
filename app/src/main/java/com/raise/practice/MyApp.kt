package com.raise.practice

import android.app.Application
import android.content.Context
import com.raise.weapon_base.LLog
import kotlin.properties.Delegates

class MyApp : Application() {

    companion object {
        var ctx: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this
        LLog.d("MyApp", "onCreate() this=$this")

    }
}