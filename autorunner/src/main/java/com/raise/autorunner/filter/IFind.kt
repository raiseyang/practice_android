package com.raise.autorunner.filter

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

/**
 * 通过该接口，查找子View
 */
interface IFind {

    fun findAll(root: AccessibilityNodeInfoCompat, selector: Selector): List<AccessibilityNodeInfoCompat>

    /**
     * 找到第一个满足的节点
     */
    fun findFirst(root: AccessibilityNodeInfoCompat, selector: Selector): AccessibilityNodeInfoCompat?

}