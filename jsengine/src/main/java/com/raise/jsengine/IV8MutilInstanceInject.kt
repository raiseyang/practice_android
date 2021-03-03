package com.raise.jsengine

import com.eclipsesource.v8.V8Object

/**
 * 多实例注入需要对象构造器函数
 */
interface IV8MutilInstanceInject : IV8Inject {
    /**
     * 空实现即可
     */
    fun jsConstructor(obj: V8Object)

}