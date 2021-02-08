package com.raise.jsengine

import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.os.Build
import com.raise.autorunner.WAccessibilityService
import com.raise.weapon_base.LLog

private const val TAG = "RectUtil"

fun Rect.scrollUp() {
    LLog.d(TAG, "scrollUp() start：$this")
    val width = this.right - this.left
    val height = bottom - top
    // 第一个点
    val x1 = left + width / 2
    val y1 = bottom - height / 5
    // 第二个点
    val x2 = left + width / 2 - 2
    val y2 = bottom - height / 3
    // 第三个点
    val x3 = left + width / 2 - 10
    val y3 = top + 10

    if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || x3 < 0 || y3 < 0) {
        LLog.d(TAG, "scrollUp() error 小于0")
    }

    val swipePath = Path()
    swipePath.moveTo(x1.toFloat(), y1.toFloat())
    swipePath.lineTo(x2.toFloat(), y2.toFloat())
    swipePath.lineTo(x3.toFloat(), y3.toFloat())
    if (Build.VERSION.SDK_INT >= 24) {
        val gestureBuilder = GestureDescription.Builder()
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(swipePath, 0, 1000))
        WAccessibilityService.instance?.dispatchGesture(gestureBuilder.build(), null, null)
    }
}

fun Rect.scrollDown() {
    LLog.d(TAG, "scrollDown() start")
    val width = this.right - this.left
    val height = bottom - top
    // 第一个点
    val x3 = left + width / 2
    val y3 = bottom - height / 5
    // 第二个点
    val x2 = left + width / 2 - 2
    val y2 = bottom - height / 4
    // 第三个点
    val x1 = left + width / 2 - 10
    val y1 = bottom - height / 2 + height / 3

    val swipePath = Path()
    swipePath.moveTo(x1.toFloat(), y1.toFloat())
    swipePath.lineTo(x2.toFloat(), y2.toFloat())
    swipePath.lineTo(x3.toFloat(), y3.toFloat())
    if (Build.VERSION.SDK_INT >= 24) {
        val gestureBuilder = GestureDescription.Builder()
        gestureBuilder.addStroke(GestureDescription.StrokeDescription(swipePath, 0, 1000))
        WAccessibilityService.instance?.dispatchGesture(gestureBuilder.build(), null, null)
    }
}