package com.raise.jsengine

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array

/**
 * 注入无返回值的全局方法
 *
 */
fun V8.injectGlobalMethod(jsMethodName: String, block: V8Array.() -> Unit) {
    registerJavaMethod({ _, parameters ->
        block(parameters)
    }, jsMethodName)
}

/**
 * 只有一个参数,调用block
 */
fun V8Array.only1(onError: (String) -> Unit = {}, block: (Any) -> Unit) {
    if (length() == 1) {
        block(get(0))
    } else {
        onError("V8Array Expect 1,but ${length()}")
    }
}

/**
 * 只有一个参数,调用block
 */
fun V8Array.only2(block: (Any) -> Unit) {
    if (length() == 2) {
        block(listOf(get(0), get(1)))
    }
}

/**
 * 只有一个参数,调用block
 */
fun V8Array.only3(block: (Any) -> Unit) {
    if (length() == 3) {
        block(listOf(get(0), get(1), get(2)))
    }
}