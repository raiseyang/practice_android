package com.raise.jsengine

import com.eclipsesource.v8.JavaCallback
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object

/**
 * 注入单例api
 * <code>
 *  V8Object(runtime).injectJSClass("viewSelector") {
 *      injectMethod("text", String::class.java)
 *      injectMethod("textStarts", String::class.java)
 *      injectMethod("id", String::class.java)
 *      injectMethod("findOne", String::class.java)
 *      injectMethod("findAll", String::class.java)
 *  }
 * </code>
 *   js中使用
 *  <code>
 *      viewSelector.text("任务").findOne()
 *  </code>
 */
fun V8Object.injectJSClass(clazzName: String, block: V8Object.() -> Unit) {
    block(this)
    runtime.add(clazzName, this)
}

/**
 * 注入实例中的方法
 */
fun V8Object.injectMethod(methodName: String, vararg clazzArray: Class<*>) {
    registerJavaMethod(this, methodName, methodName, clazzArray)
}

/**
 * 注入带返回值的全局方法
 *
 */
fun <T> V8.injectGlobalMethodWithResult(jsMethodName: String, block: V8Array.() -> T) {
    registerJavaMethod(JavaCallback { _, v8Array -> block(v8Array) }, jsMethodName)
}

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