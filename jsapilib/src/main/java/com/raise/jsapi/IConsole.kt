package com.raise.jsapi

/**
 * 控制台输出日志
 */
interface IConsole {

    @JsApi
    fun log(msg: String)

    @JsApi
    fun debug(msg: String)

    @JsApi
    fun info(msg: String)

    @JsApi
    fun warn(msg: String)

    @JsApi
    fun error(msg: String)

}