package com.raise.jsengine

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.raise.jsapi.IConsole
import com.raise.weapon_base.LLog

/**
 * 单例类
 */
object Console : IConsole, IV8Inject {
    // js全局tag
    private const val TAG = "js:"

    /**
     * 将该类注入到V8中
     */
    override fun injectV8(runtime: V8) {
        // 单例注入
        val v8Console = V8Object(runtime)
        v8Console.registerJavaMethod(this, "log", "log", arrayOf<Class<*>>(String::class.java))
        v8Console.registerJavaMethod(this, "debug", "debug", arrayOf<Class<*>>(String::class.java))
        v8Console.registerJavaMethod(this, "info", "info", arrayOf<Class<*>>(String::class.java))
        v8Console.registerJavaMethod(this, "warn", "warn", arrayOf<Class<*>>(String::class.java))
        v8Console.registerJavaMethod(this, "error", "error", arrayOf<Class<*>>(String::class.java))
        // 第一个参数是js代码中调用的域
        runtime.add("console", v8Console)
    }

    override fun log(msg: String) {
        info(msg)
    }

    override fun debug(msg: String) {
        LLog.d(TAG, msg)
    }

    override fun info(msg: String) {
        LLog.i(TAG, msg)
    }

    override fun warn(msg: String) {
        LLog.w(TAG, msg)
    }

    override fun error(msg: String) {
        LLog.e(TAG, msg)
    }

}