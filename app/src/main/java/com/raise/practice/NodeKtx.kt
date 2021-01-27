package com.raise.practice

import android.view.accessibility.AccessibilityNodeInfo

const val TAG = "NodeKtx"

/**
 * 精确匹配
 */
fun findViewByText(rootNode: AccessibilityNodeInfo, text: String): AccessibilityNodeInfo? {

    for (i in 0 until rootNode.childCount) {
        val nodeInfo = rootNode.getChild(i)
        if (nodeInfo.className.endsWith("Layout")) { // ViewGroup
            // 递归查找view元素
            val findViewByText = findViewByText(rootNode, text)
            if (findViewByText != null) {
                return findViewByText
            }
        } else {
            if (nodeInfo.text == text) {
                // 找到
                return nodeInfo
            }
        }

    }
    return null
}

fun findScrollableView(rootNode: AccessibilityNodeInfo): List<AccessibilityNodeInfo> {

    val scrollableViewList = mutableListOf<AccessibilityNodeInfo>()

    for (i in 0 until rootNode.childCount) {
        val nodeInfo = rootNode.getChild(i)
        if (isViewGroupNode(nodeInfo)) { // ViewGroup
            // 递归查找元素
            findScrollableView(rootNode)
        } else {
            if (nodeInfo.isScrollable) {
                // 找到
                scrollableViewList.add(nodeInfo)
            }
        }

    }
    return scrollableViewList
}


fun isViewGroupNode(node: AccessibilityNodeInfo) = node.className.endsWith("Layout")