package com.raise.autorunner.filter

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.raise.weapon_base.LLog
import java.util.*

/**
 * 选择器
 * 添加多个过滤器来做选择
 */
class Selector {

    companion object {
        private const val TAG = "Selector"

        fun addAll(vararg filters: IFilter): Selector {
            val selector = Selector()
            selector.mFilters.addAll(filters)
            return selector
        }
    }

    private val mFilters = LinkedList<IFilter>()

    fun filter(node: AccessibilityNodeInfoCompat): Boolean {
        LLog.d(TAG, "filter() start. node.className=" + node.className + ",obj=${Integer.toHexString(node.hashCode())}")
        LLog.d(TAG, "filter() 比较：$this")
        for (filter in mFilters) {
            if (!filter.filter(node)) {
                return false
            }
        }
        LLog.d(TAG, "filter() 比较成功.")
        return true
    }


    fun add(filter: IFilter) {
        mFilters.add(filter)
    }

    fun add(block: AccessibilityNodeInfoCompat.() -> Boolean) {
        mFilters.add(object : IFilter {
            override fun filter(nodeInfo: AccessibilityNodeInfoCompat): Boolean = block(nodeInfo)
        })
    }

    override fun toString(): String {
        val str = StringBuilder()
        for (filter in mFilters) {
            str.append("[")
                    .append(filter.toString())
                    .append("]")
        }
        return str.toString()
    }

}