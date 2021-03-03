package com.raise.jsengine

import com.eclipsesource.v8.JavaCallback
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object

/**
 * 注入对象构造器函数
 *
 * 注入后js可以这样使用
 * <code>
 * var x = new ViewSelector();
 * <code/>
 *
 * @param constructorName 对象构造名，常以大写字母开头(默认为类名)
 */
fun V8.injectConstructor(obj: Any, constructorName: String) {
    registerJavaMethod(obj,
            "jsConstructor", // 固定写法，参考[IV8MutilInstanceInject]
            constructorName, // js:对象构造器
            arrayOf<Class<*>>(V8Object::class.java),
            true)
}


/**
 * 注入对象构造器函数
 * <code>
 * runtime.injectProtoTypeMethod("ViewSelector") {
 *     injectMethod(this@ViewSelector,
 *             "text" to arrayOf(String::class.java),
 *             "id" to arrayOf(String::class.java,Integer::class.java)
 *             )
 * }
 * <code/>
 */
fun V8.injectProtoTypeMethod(constructorName: String, block: V8Object.() -> Unit) {
    val obj = getObject(constructorName)
    val prototype = executeObjectScript("${constructorName}.prototype")
    block(prototype)
    obj.setPrototype(prototype)
}


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
 * @param obj 给prototype 添加方法需要传入obj，obj是prototype的原对象
 */
fun V8Object.injectMethod(obj: Any = this, methodName: String, vararg clazzArray: Class<*>) {
    registerJavaMethod(this, methodName, methodName, clazzArray)
}

/**
 * 一次注入多个方法
 * @param obj 给prototype 添加方法需要传入obj，obj是prototype的原对象
 * <code>
 * injectMethod(this@ViewSelector,
 *          "text" to arrayOf(String::class.java),
 *          "id" to arrayOf(String::class.java,Integer::class.java)
 *          )
 *     <code/>
 */
fun V8Object.injectMethod(obj: Any = this, vararg pairs: Pair<String, Array<Class<*>>>) {
    pairs.forEach {
        registerJavaMethod(obj, it.first, it.first, it.second)
    }
}

/**
 * 注入包含接收器的方法
 * 1. 自动填充第一个V8Object类型参数
 */
fun V8Object.injectMethodIncludeReceiver(obj: Any = this, vararg pairs: Pair<String, Array<Class<*>>>) {
    pairs.forEach {
        val mutableList = it.second.toMutableList()
        mutableList.add(0, V8Object::class.java)
        registerJavaMethod(obj, it.first, it.first, mutableList.toTypedArray(), true)
    }
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