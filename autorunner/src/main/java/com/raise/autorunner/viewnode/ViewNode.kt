package com.raise.autorunner.viewnode

import android.os.Bundle
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.raise.autorunner.filter.NodeSelector
import com.raise.autorunner.filter.Selector

/**
 * AccessibilityNodeInfo的包装对象
 * 扩展AccessibilityNodeInfo的功能
 */
class ViewNode(val nodeInfo: AccessibilityNodeInfoCompat) {

    constructor(node: AccessibilityNodeInfo) : this(AccessibilityNodeInfoCompat.wrap(node))

    fun findAll(selector: Selector): List<ViewNode> {
        val result = ArrayList<ViewNode>()
        NodeSelector.findAll(nodeInfo, selector).forEach {
            result.add(ViewNode(it))
        }
        return result
    }

    fun findFirst(selector: Selector): ViewNode? {
        return NodeSelector.findFirst(this.nodeInfo, selector)?.let { ViewNode(it) }
    }

    /**
     * editable控件设置
     */
    fun setText(content: String): Boolean {
        if (nodeInfo.isEditable) {
            val bundle = Bundle()
            bundle.putCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, content)
            nodeInfo.performAction(AccessibilityNodeInfoCompat.ACTION_FOCUS)
            nodeInfo.performAction(AccessibilityNodeInfoCompat.ACTION_SET_TEXT, bundle)
            return true
        } else {
            return false
        }
    }

    fun click() {
        nodeInfo.performAction(AccessibilityNodeInfoCompat.ACTION_CLICK)
    }

    fun longClick() {
        nodeInfo.performAction(AccessibilityNodeInfoCompat.ACTION_LONG_CLICK)
    }

}