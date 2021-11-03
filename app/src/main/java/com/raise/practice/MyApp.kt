package com.raise.practice

import android.app.Application
import com.raise.weapon_base.LLog
import dagger.hilt.android.HiltAndroidApp
// @HiltAndroidApp 必须标记；代表应用容器，单例对象都会绑定在他身上
// 以后可以通过@ApplicationContext 注入任意Context变量
@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LLog.d("MyApp", "onCreate() this=$this")

    }
}