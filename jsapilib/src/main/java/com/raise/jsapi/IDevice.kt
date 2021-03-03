package com.raise.jsapi

/**
 * 当前设备的基本信息
 */
interface IDevice {

    fun width(): Int

    fun height(): Int

    fun sn(): String

    fun sdkInt(): Int

    /**
     * 设备是否被唤醒
     */
    @JsApi
    fun isScreenOn(): Boolean

    /**
     * 唤醒设备
     */
    @JsApi
    fun wakeUp()

    /**
     * 设备震动
     * @param timeInSecond 震动时长，单位：秒
     */
    @JsApi
    fun vibrate(timeInSecond: Int)


}