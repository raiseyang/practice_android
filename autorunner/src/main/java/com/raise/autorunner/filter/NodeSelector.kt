package com.raise.autorunner.filter

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.raise.weapon_base.LLog

object NodeSelector : IFind {

    private const val TAG = "NodeSelector"
    fun findFirst(root: AccessibilityNodeInfoCompat, selector: Selector,
                  onFound: AccessibilityNodeInfoCompat.() -> Unit,
                  error: () -> Unit) {
        val node = findFirst(root, selector)
//        node?.also(onFound) ?: error
        if (node == null) {
            error()
        } else {
            onFound(node)
        }
    }

    override fun findFirst(root: AccessibilityNodeInfoCompat, selector: Selector): AccessibilityNodeInfoCompat? {
        LLog.d(TAG, "findFirst() ")
        val deque: ArrayDeque<AccessibilityNodeInfoCompat> = ArrayDeque()
        deque.add(root)
        while (!deque.isEmpty()) {
            // 取出最前面的一个元素
            val node = deque.removeFirst()
            val match = selector.filter(node)
            if (match) {
                return node
            }

            for (i in 0 until node.childCount) {
                try {
                    deque.addLast(node.getChild(i))
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    continue
                }
            }
        }
        return null
    }

    override fun findAll(root: AccessibilityNodeInfoCompat, selector: Selector): List<AccessibilityNodeInfoCompat> {
        LLog.d(TAG, "findAll() start.")
        val deque: ArrayDeque<AccessibilityNodeInfoCompat> = ArrayDeque()
        val result: ArrayList<AccessibilityNodeInfoCompat> = ArrayList()
        deque.add(root)
        while (!deque.isEmpty()) {
            // 取出最前面的一个元素
            val node = deque.removeFirst()
            val match = selector.filter(node)
            if (match) {
                result.add(node)
            }

            for (i in 0 until node.childCount) {
                deque.addLast(node.getChild(i))
            }
        }
        return result
    }

}