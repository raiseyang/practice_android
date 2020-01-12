package com.raise.practice

import android.app.Application
import com.raise.practice.autojs.AutoJs
import com.raise.practice.launch.FileScriptLauncher
import com.stardust.app.GlobalAppContext

/**
 * Created by raise.yang on 20/01/10.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // 初始化autojs
        GlobalAppContext.set(this)
        AutoJs.initInstance(this)
        FileScriptLauncher.init()
    }
}
