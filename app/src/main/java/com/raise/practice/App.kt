package com.raise.practice

import android.app.Application
import com.raise.practice.data.AppDatabase

/**
 * Created by raise.yang on 20/06/05.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }
}