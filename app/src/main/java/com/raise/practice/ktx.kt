package com.raise.practice

import android.app.Instrumentation

fun getInstrumentation(): Instrumentation {

    val activityThread: Class<*> = Class.forName("android.app.ActivityThread")
    val sCurrentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
    sCurrentActivityThread.setAccessible(true);
//    //获取ActivityThread 对象
    val activityThreadObject = sCurrentActivityThread.invoke(activityThread);
//
    //获取 Instrumentation 对象
    val mInstrumentation = activityThread.getDeclaredField("mInstrumentation");
    mInstrumentation.setAccessible(true);
    val instrumentation = mInstrumentation.get(activityThreadObject) as Instrumentation
    return instrumentation
//    CustomInstrumentation customInstrumentation = new CustomInstrumentation(instrumentation);
//    //将我们的 customInstrumentation 设置进去
//    mInstrumentation.set(activityThreadObject, customInstrumentation);
}