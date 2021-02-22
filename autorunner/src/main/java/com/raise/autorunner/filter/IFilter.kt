package com.raise.autorunner.filter

import android.widget.Button
import android.widget.EditText
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

/**
 * 过滤器接口
 */
interface IFilter {

    fun filter(nodeInfo: AccessibilityNodeInfoCompat): Boolean
}

object Filter {

    fun id(id: String): IFilter = filter("id(${id})") {
        this.viewIdResourceName == id
    }

    fun textStartsWith(text: String): IFilter = filter("textStartsWith(${text})") {
        this.text.startsWith(text)
    }

    fun text(text: String): IFilter = filter("text($text)") {
        this.text == text
    }

    fun descStartsWith(text: String): IFilter = filter("descStartsWith($text)") {
        this.contentDescription.startsWith(text)
    }

    fun desc(text: String): IFilter = filter("desc($text)") {
        this.contentDescription == text
    }

    fun view(className: String): IFilter = filter("view($className)") {
        this.className == className
    }

    val EDITABLE = filter("EDITABLE") {
        isEditable
    }
    val CLICKABLE = filter("CLICKABLE") {
        isClickable
    }
    val SCROLLABLE = filter("SCROLLABLE") {
        isScrollable
    }

    val VIEW_BUTTON = filter("VIEW_BUTTON") {
        className == Button::class.java.name
    }

    val VIEW_EDITTEXT = filter("VIEW_EDITTEXT") {
        className == EditText::class.java.name
    }

    private fun filter(tag: String = "", block: AccessibilityNodeInfoCompat.() -> Boolean) =
            object : IFilter {
                override fun filter(nodeInfo: AccessibilityNodeInfoCompat): Boolean = block(nodeInfo)
                override fun toString(): String {
                    return tag
                }
            }

}

