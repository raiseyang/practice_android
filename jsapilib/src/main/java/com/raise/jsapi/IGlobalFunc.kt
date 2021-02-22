package com.raise.jsapi

interface IGlobalFunc {

    fun sleep(timeInMills: Long)

    fun toast(content: String)

    /**
     * 点击匹配该文本的控件
     * 1. 如果界面上有多个控件匹配，则点击第一个
     */
    fun click(text: String)

    // 找到屏幕内的第一个滑动组件，向前滑动
    fun scrollForward()

    // 找到屏幕内的第一个滑动组件，向前滑动
    fun scrollBackward()

    fun scrollUp()
    fun scrollDown()


    fun currentPackage(): String
    fun currentActivity(): String

    fun setClip(text: String)
    fun getClip(): String
}