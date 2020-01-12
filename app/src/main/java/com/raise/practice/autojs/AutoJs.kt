package com.raise.practice.autojs

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.abupdate.common.Trace
import com.stardust.autojs.engine.LoopBasedJavaScriptEngine
import com.stardust.autojs.runtime.ScriptRuntime
import com.stardust.autojs.runtime.api.AppUtils
import com.stardust.autojs.script.JavaScriptSource


/**
 * Created by Stardust on 2017/4/2.
 */

class AutoJs private constructor(application: Application) : com.stardust.autojs.AutoJs(application) {
    override fun ensureAccessibilityServiceEnabled() {

    }

    override fun waitForAccessibilityServiceEnabled() {

    }

    init {
        // 注册脚本执行监听器
        Trace.d(TAG, "() 注册脚本执行监听器")
        scriptEngineService.registerGlobalScriptExecutionListener(ScriptExecutionGlobalListener())
    }

    override fun createAppUtils(context: Context): AppUtils {
        return AppUtils(context, context.packageName + ".fileprovider")
    }

    override fun initScriptEngineManager() {
        super.initScriptEngineManager()
        Trace.d(TAG, "initScriptEngineManager() ")
        scriptEngineManager.registerEngine(JavaScriptSource.ENGINE) {
            val engine = LoopBasedJavaScriptEngine(application)
            engine.runtime = createRuntime()
            engine
        }
    }

    override fun createRuntime(): ScriptRuntime {
        val runtime = super.createRuntime()
        // 注册Activity界面，可以打开app.startActivity(name)
//        runtime.putProperty("class.settings", SettingsActivity::class.java)
//        runtime.putProperty("class.console", LogActivity::class.java)
        return runtime
    }

    companion object {
        const val TAG = "AutoJs"
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: AutoJs
            private set

        fun initInstance(application: Application) {
            instance = AutoJs(application)
        }
    }
}
