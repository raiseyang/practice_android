package com.raise.practice

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.util.Log

class MyInstrumentation : Instrumentation() {

    /**
     * hook打印创建的activity对象
     */
    override fun newActivity(cl: ClassLoader?, className: String?, intent: Intent?): Activity {
        val activity = super.newActivity(cl, className, intent)
        //newActivity() activity=com.raise.practice.MainActivity@58f3841
        Log.d("MyInstrumentation","newActivity() activity=$activity")
        return activity
    }

}