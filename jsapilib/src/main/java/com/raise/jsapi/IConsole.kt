package com.raise.jsapi

/**
 * 控制台输出日志
 */
interface IConsole {

    fun log(msg: String)
    fun debug(msg: String)
    fun info(msg: String)
    fun warn(msg: String)
    fun error(msg: String)

}