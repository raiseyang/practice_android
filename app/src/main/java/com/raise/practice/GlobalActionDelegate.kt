package com.raise.practice

import android.view.accessibility.AccessibilityEvent
import com.raise.weapon_base.LLog

/**
 * 接收界面改变、点击、滑动等事件
 */
object GlobalActionDelegate {

    const val TAG = "GlobalActionDelegate"

    fun onAccessibilityEvent(event: AccessibilityEvent) {
        event.apply {
            LLog.d(TAG, "onAccessibilityEvent() event=${event}")
        }

        when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                clickedEvent(event)
            }
        }


    }

    private fun clickedEvent(event: AccessibilityEvent) {
        event.action
    }

}