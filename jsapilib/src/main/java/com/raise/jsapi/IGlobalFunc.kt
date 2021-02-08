package com.raise.jsapi

interface IGlobalFunc {

    fun sleep(timeInMills: Long)

    fun toast(content: String)

    fun click(text: String)

    // 找到屏幕内的第一个滑动组件，向前滑动
    fun scrollForward()

    // 找到屏幕内的第一个滑动组件，向前滑动
    fun scrollBackward()

    fun scrollUp()
    fun scrollDown()
}