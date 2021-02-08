package com.raise.autorunner

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.raise.autorunner.filter.Filter
import com.raise.autorunner.filter.IFilter
import com.raise.autorunner.filter.Selector
import com.raise.autorunner.viewnode.ViewNode
import com.raise.weapon_base.LLog


class WAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "WAccessibilityService"
        var instance: WAccessibilityService? = null
    }

    override fun onCreate() {
        super.onCreate()
        LLog.d(TAG, "onCreate() ")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
        LLog.d(TAG, "onServiceConnected() ")
        Toast.makeText(this, "yds::onServiceConnected() ", Toast.LENGTH_SHORT).show()

//        serviceInfo.flags = serviceInfo.flags and AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            if (it.packageName != null) {
                AccessibilityServiceDelegate.onAccessibilityEvent(it)
            }
        }
    }

    override fun onInterrupt() {
        LLog.d(TAG, "onInterrupt() ")
    }

    override fun onDestroy() {
        LLog.d(TAG, "onDestroy() ")
        instance = null
        super.onDestroy()
    }

    fun rootViewNode() = ViewNode(rootInActiveWindow)

    fun inputText(content: String) {
        LLog.d(TAG, "inputText() content = " + content)
        val selector = Selector()
        selector.add(Filter.VIEW_EDITTEXT)
        selector.add(Filter.text("今日头条"))
        selector.add(object : IFilter {
            override fun filter(nodeInfo: AccessibilityNodeInfoCompat): Boolean = nodeInfo.isEditable
        })

        selector.add {
            isEditable
        }

        rootInActiveWindow?.apply {
            val findAll = ViewNode(this).findAll(selector)
            LLog.d(TAG, "inputText() findAll.size=" + findAll.size)
            findAll.forEach {
                LLog.d(TAG, "inputText() view.className=" + it.nodeInfo.className)
                it.setText(content)
            }
        }
                ?: LLog.d(TAG, "inputText() rootInActiveWindow == null")
    }

}
