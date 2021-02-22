package com.raise.jsengine

import android.os.Build
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.raise.jsapi.IDevice
import com.raise.jsengine.utils.getPowerManager
import com.raise.weapon_base.ContextVal

/**
 * 设备基本信息
 * js:
 * <code>
 *     var width = device.width
 * </code>
 */
object Device : IDevice, IV8Inject {

    override fun injectV8(runtime: V8) {
        // 单例注入
        val v8Device = V8Object(runtime)
        v8Device.add("width", width())
        v8Device.add("height", height())
        v8Device.add("sn", sn())
        // 第一个参数是js代码中调用的域
        runtime.add("device", v8Device)
    }

    override fun width(): Int {
        return ContextVal.getContext().resources.displayMetrics.widthPixels
    }

    override fun height(): Int {
        return ContextVal.getContext().resources.displayMetrics.heightPixels
    }

    override fun sn(): String {
        return Build.SERIAL
    }

    override fun sdkInt(): Int = Build.VERSION.SDK_INT

    override fun isScreenOn(): Boolean = getPowerManager().isInteractive

    override fun wakeUp() {
    }

    override fun vibrate(timeInSecond: Int) {
    }
}