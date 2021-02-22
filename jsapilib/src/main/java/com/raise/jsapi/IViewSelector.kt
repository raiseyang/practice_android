package com.raise.jsapi

/**
 * 控件选择器，查找界面上的控件
 */
interface IViewSelector {
    /**
     * 全匹配text控件
     */
    fun text(text: String): IViewSelector

    /**
     * 以text开头的控件
     */
    fun textStartsWith(text: String): IViewSelector

    /**
     * 根据唯一ID来查找控件，将直接返回控件对象
     */
    fun id(id: String): IView?

    /**
     * 查找满足条件的第一个控件
     */
    fun findOne(): IView?

    /**
     * 查找所有满足条件的控件
     * 1. 未找到，将会返回空集合
     */
    fun findAll(): ArrayList<IView>

}