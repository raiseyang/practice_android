package com.raise.practice

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager

/**
 * Created by raise.yang on 20/05/15.
 */
class FloatWindow {

    private var mRootView: View? = null
    private lateinit var windowManager: WindowManager

    fun showView(context: Context, view: View) {
        mRootView = view

        // 获取系统服务
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

//        windowManager.addView(mRootView, wmParams)
    }

    fun hideView() {
        windowManager.removeView(mRootView)
    }
}