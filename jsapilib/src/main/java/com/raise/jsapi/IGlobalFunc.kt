package com.raise.jsapi

interface IGlobalFunc {

    @JsApi
    fun sleep(timeInMills: Long)

    @JsApi
    fun toast(content: String)

    /**
     * 点击匹配该文本的控件
     * 1. 如果界面上有多个控件匹配，则点击第一个
     */
    @JsApi
    fun click(text: String)

    // 找到屏幕内的第一个滑动组件，向前滑动
    @JsApi
    fun scrollForward()

    // 找到屏幕内的第一个滑动组件，向前滑动
    @JsApi
    fun scrollBackward()

    @JsApi
    fun scrollUp()

    @JsApi
    fun scrollDown()


    @JsApi
    fun currentPackage(): String

    @JsApi
    fun currentActivity(): String

    @JsApi
    fun setClip(text: String)

    @JsApi
    fun getClip(): String
}