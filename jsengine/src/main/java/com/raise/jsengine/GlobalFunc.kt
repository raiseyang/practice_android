package com.raise.jsengine

import android.os.SystemClock
import com.eclipsesource.v8.V8
import com.raise.jsapi.IGlobalFunc
import com.raise.weapon_base.LLog

object GlobalFunc : IGlobalFunc, IV8Inject {
    const val TAG = "GlobalFunc"
    override fun injectV8(runtime: V8) {
        // 全局方法注入
        runtime.apply {
            // js代码中可以直接使用sleep(2000)
            injectGlobalMethod("sleep") {
                only1(onError = {
                    errorParams(it)
                }) {
                    sleep((it as Int).toLong())
                }
            }

            injectGlobalMethod("toast") {
                only1(onError = {
                    errorParams(it)
                }) {
                    toast(it as String)
                }
            }
        }
    }

    override fun sleep(timeInMills: Long) {
        SystemClock.sleep(timeInMills)
    }

    override fun toast(content: String) {
        LLog.d(TAG, "toast() content=$content")
//        Toast.makeText(null, content, Toast.LENGTH_SHORT).show()
    }

    private fun errorParams(error: String) {
        LLog.e("GlobalFunc", "errorParams() $error")
    }
}