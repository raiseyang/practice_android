package com.raise.practice

import android.app.Application
import com.raise.weapon_base.LLog
import android.os.Build
import android.os.Build.VERSION

import android.os.Build.VERSION.SDK_INT
import android.util.Log
import java.lang.reflect.Method


class MyApp : Application() {

    override fun onCreate() {
//        modifyChangeHideMethod()
        // 反射替换ActivityThread的mInstrumentation
        hookInstrumentation()


        super.onCreate()
        LLog.d("MyApp", "onCreate() this=$this")

    }

    private fun modifyChangeHideMethod() {
        if (SDK_INT < Build.VERSION_CODES.P) {
            return
        }
        try {
            val forName: Method = Class::class.java.getDeclaredMethod("forName", String::class.java)
            val getDeclaredMethod: Method = Class::class.java.getDeclaredMethod("getDeclaredMethod", String::class.java, Array::class.java)
            val vmRuntimeClass = forName.invoke(null, "dalvik.system.VMRuntime")
            val getRuntime: Method = getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null) as Method
            val setHiddenApiExemptions: Method = getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", arrayOf<Class<*>>(Array<String>::class.java)) as Method
            val sVmRuntime: Any = getRuntime.invoke(null)
            setHiddenApiExemptions.invoke(sVmRuntime, arrayOf<Any>(arrayOf("L")))
        } catch (e: Throwable) {
            Log.e("[error]", "reflect bootstrap failed:", e)
        }
    }

    private fun hookInstrumentation() {
        LLog.d("MyApp", "hookInstrumentation() this=$this")
        val myIns = MyInstrumentation()
        val atClazz = Class.forName("android.app.ActivityThread")

        val method = atClazz.getDeclaredMethod("currentActivityThread")
        val curActivityThread = method.invoke(null)

        val field = atClazz.getDeclaredField("mInstrumentation")
        field.isAccessible = true
        field.set(curActivityThread,myIns)
//    final void basicInit(ActivityThread thread) {
//        mThread = thread;
//    }

        val insClazz = Class.forName("android.app.Instrumentation")
        val method2 = insClazz.getDeclaredMethod("basicInit")
        method2.invoke(myIns,curActivityThread)
        //MyInstrumentation ins = new MyInstrumentation();
        //
        //Class cls = Class.forName("android.app.ActivityThread"); // ActivityThread被隐藏了，所以通过这种方式获得class对象
        //
        //Method mthd = cls.getDeclaredMethod("currentActivityThread", (Class[]) null); // 获取当前ActivityThread对象引用
        //
        //Object currentAT = mthd.invoke(null, (Object[]) null);
        //
        //Field mInstrumentation = currentAT.getClass().getDeclaredField("mInstrumentation");
        //
        //mInstrumentation.setAccessible(true);//设置private变量为可读取
        //
        //mInstrumentation.set(currentAT, ins); // 修改ActivityThread.mInstrumentation值
    }
}