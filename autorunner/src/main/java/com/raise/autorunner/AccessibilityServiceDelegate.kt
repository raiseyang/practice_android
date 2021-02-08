package com.raise.autorunner

import android.view.accessibility.AccessibilityEvent
import com.raise.weapon_base.LLog

/**
 * 接管通知事件
 */
object AccessibilityServiceDelegate {
    private const val TAG = "AccessibilityServiceDelegate"

    /**
     * 监听界面变化事件
     */
    fun onAccessibilityEvent(event: AccessibilityEvent) {
        LLog.d(TAG, "onAccessibilityEvent() start. event=${event}")

    }

}